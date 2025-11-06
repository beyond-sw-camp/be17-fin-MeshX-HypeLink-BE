package com.example.apigateway.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthServiceClient {
    private final WebClient.Builder webClientBuilder;

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

    @Data
    private static class ApiResponse {
        private Integer data;       // memberId
        private String message;
    }

}
