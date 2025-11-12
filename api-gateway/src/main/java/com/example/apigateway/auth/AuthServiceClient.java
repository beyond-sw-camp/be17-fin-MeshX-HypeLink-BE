package com.example.apigateway.auth;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@AllArgsConstructor
public class AuthServiceClient {
    private final WebClient.Builder webClientBuilder;

    @CircuitBreaker(name = "authService", fallbackMethod = "getMemberIdFallback")
    public Mono<Integer> getMemberIdByEmail(String email) {
        return webClientBuilder.build()
                .get()
                .uri("lb://api-auth/internal/auth/member?email={email}", email)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .map(response -> {
                    if (response.getData() != null) {
                        return response.getData();
                    }
                    throw new RuntimeException("Failed to get memberId");
                });
    }

    private Mono<Integer> getMemberIdFallback(String email, Exception e) {
        log.error("Auth Service 서킷브레이커 작동! email: {}, error: {}", email, e.getMessage());
        return Mono.error(new RuntimeException("Auth service unavailable"));
    }

    @Data
    private static class ApiResponse {
        private Integer data;       // memberId
        private String message;
    }

}
