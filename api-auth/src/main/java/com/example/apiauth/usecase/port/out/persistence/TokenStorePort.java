package com.example.apiauth.usecase.port.out.persistence;

import java.time.Duration;
import java.util.Optional;

public interface TokenStorePort {
    void saveRefreshToken(String email, String refreshToken);
    Optional<String> getRefreshToken(String refreshToken);
    void deleteRefreshToken(String email);
    void blacklistToken(String token, Duration timeToLive);
}
