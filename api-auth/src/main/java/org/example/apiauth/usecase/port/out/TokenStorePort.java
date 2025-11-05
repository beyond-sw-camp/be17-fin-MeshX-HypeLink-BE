package org.example.apiauth.usecase.port.out;

import java.time.Duration;
import java.util.Optional;

public interface TokenStorePort {
    void saveRefreshToken(String email, String refreshToken);
    Optional<String> getRefreshToken(String email);
    void deleteRefreshToken(String email);
    void blacklistToken(String token, Duration timeToLive);
    boolean isBlacklisted(String token);
}
