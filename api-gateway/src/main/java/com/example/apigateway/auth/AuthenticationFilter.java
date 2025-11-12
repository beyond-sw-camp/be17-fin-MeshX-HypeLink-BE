package com.example.apigateway.auth;

import com.example.apigateway.config.JwtTokenProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtTokenProvider jwtTokenProvider;
    private final ReactiveRedisTemplate<String, String> redisTemplate;
    private final AuthServiceClient authServiceClient;

    public AuthenticationFilter(
            JwtTokenProvider jwtTokenProvider,
            ReactiveRedisTemplate<String, String> redisTemplate,
            AuthServiceClient authServiceClient) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
        this.authServiceClient = authServiceClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().value();

            // 1. Internal API 차단
            if (path.startsWith("/internal/")) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            // 2. 공개 경로
            if (isPublicPath(path)) {
                return chain.filter(exchange);
            }

            // 3. JWT 토큰 추출
            String token = extractToken(exchange.getRequest());
            if (token == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // 4. JWT 검증
            if (!jwtTokenProvider.validateToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // 5. 블랙리스트 체크
            return isTokenBlacklisted(token)
                    .flatMap(isBlacklisted -> {
                        if (isBlacklisted) {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }

                        // 6. JWT에서 memberId, email, role 추출
                        Integer memberId = jwtTokenProvider.getMemberIdFromToken(token);
                        String email = jwtTokenProvider.getEmailFromToken(token);
                        String role = jwtTokenProvider.getRoleFromToken(token);

                        // 7. 헤더 추가
                        ServerHttpRequest request = exchange.getRequest().mutate()
                                .header("Member-Id", memberId.toString())
                                .header("Member-Email", email)
                                .header("Member-Role", role)
                                .build();

                        return chain.filter(exchange.mutate().request(request).build());
                    })
                    .onErrorResume(e -> {
                        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        return exchange.getResponse().setComplete();
                    });
        };

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
                .flatMap(cachedMemberId -> Mono.just(Integer.parseInt(cachedMemberId)))
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
        return path.startsWith("/api/auth/login") ||
                path.startsWith("/api/auth/register") ||
                path.startsWith("/api/health/health") ||
                path.startsWith("/api/health/info") ||
                path.startsWith("/api/auth/refresh");
    }

    public static class Config {
    }
}

