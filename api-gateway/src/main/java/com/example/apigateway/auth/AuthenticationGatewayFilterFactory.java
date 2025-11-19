package com.example.apigateway.auth;

import com.example.apigateway.config.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JwtTokenProvider jwtTokenProvider;
    private final ReactiveRedisTemplate<String, String> redisTemplate;
    private final AuthServiceClient authServiceClient;

    public AuthenticationGatewayFilterFactory(
            JwtTokenProvider jwtTokenProvider,
            ReactiveRedisTemplate<String, String> redisTemplate,
            AuthServiceClient authServiceClient
    ) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
        this.authServiceClient = authServiceClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String path = exchange.getRequest().getPath().value();
            log.info("path : {}", path);

            // 1. Internal API 차단
            if (path.startsWith("/internal/")) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            // 2. WebSocket 요청 통과 (Upgrade 헤더 확인)
            String upgrade = exchange.getRequest().getHeaders().getFirst("Upgrade");
            if ("websocket".equalsIgnoreCase(upgrade)) {
                return chain.filter(exchange);
            }

            // 3. 공개 경로
            if (isPublicPath(path)) {
                return chain.filter(exchange);
            }

            // 4. JWT 토큰 추출
            String token = extractToken(exchange.getRequest());
            if (token == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // === 여기부터 전부 "비동기 + 이벤트루프 offload" ===
            return parseJwtAsync(token)               // [boundedElastic]에서 JWT 검증 + 이메일/롤 추출
                    .flatMap(payload ->               // JWT 정상
                            isTokenBlacklisted(token) // Redis로 블랙리스트 확인 (비동기)
                                    .flatMap(isBlacklisted -> {
                                        if (isBlacklisted) {
                                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                            return exchange.getResponse().setComplete();
                                        }

                                        // email → memberId 조회 (Redis 캐시 or Auth 서비스)
                                        return getMemberIdFromEmail(payload.getEmail())
                                                .flatMap(memberId -> {
                                                    // 헤더 추가
                                                    ServerHttpRequest request = exchange.getRequest().mutate()
                                                            .header("Member-Id", memberId.toString())
                                                            .header("Member-Email", payload.getEmail())
                                                            .header("Member-Role", payload.getRole())
                                                            .build();

                                                    return chain.filter(exchange.mutate().request(request).build());
                                                });
                                    })
                    )
                    .onErrorResume(e -> {
                        log.error("Authentication filter error", e);
                        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        return exchange.getResponse().setComplete();
                    });
        };
    }

    /**
     * JWT 검증 + email/role 추출을 boundedElastic 스레드풀로 오프로드
     * (Netty event-loop 블로킹 방지)
     */
    private Mono<JwtPayload> parseJwtAsync(String token) {
        return Mono.fromCallable(() -> {
                    // 여기 안은 순수 동기 코드지만 boundedElastic에서 돌아감
                    if (!jwtTokenProvider.validateToken(token)) {
                        throw new InvalidTokenException("Invalid JWT token");
                    }

                    String email = jwtTokenProvider.getEmailFromToken(token);
                    String role = jwtTokenProvider.getRoleFromToken(token);
                    return new JwtPayload(email, role);
                })
                .subscribeOn(Schedulers.boundedElastic()); // CPU-heavy 작업 offload
    }

    private Mono<Boolean> isTokenBlacklisted(String token) {
        String redisKey = "blacklist:" + token;
        return redisTemplate.opsForValue().get(redisKey)
                .map(value -> "logout".equals(value))
                .defaultIfEmpty(false);
    }

    private Mono<Integer> getMemberIdFromEmail(String email) {
        String redisKey = "email:" + email;

        return redisTemplate.opsForValue().get(redisKey)
                .flatMap(cachedMemberId -> Mono.fromCallable(() -> Integer.parseInt(cachedMemberId)))
                .switchIfEmpty(
                        authServiceClient.getMemberIdByEmail(email)
                                .flatMap(memberId ->
                                        redisTemplate.opsForValue()
                                                .set(redisKey, memberId.toString(), Duration.ofMinutes(10))
                                                .thenReturn(memberId)
                                )
                );
    }

    private String extractToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isPublicPath(String path) {
        return path.startsWith("/api/swagger/") ||   // Swagger 문서 프록시
                path.startsWith("/api/swagger-ui") || // Swagger UI
                path.startsWith("/swagger-ui") ||     // 내부 리소스 접근
                path.startsWith("/v3/api-docs") ||    // springdoc 내부 호출
                path.startsWith("/api/v3/api-docs") ||// monolith 등
                path.startsWith("/api/auth/login") ||
                path.startsWith("/api/auth/register") ||
                path.startsWith("/api/auth/refresh") ||
                path.startsWith("/api/health");
    }

    public static class Config {
    }

    @Getter
    @AllArgsConstructor
    private static class JwtPayload {
        private final String email;
        private final String role;
    }

    private static class InvalidTokenException extends RuntimeException {
        public InvalidTokenException(String message) {
            super(message);
        }
    }
}
