package MeshX.HypeLink.auth.service;

import java.time.Duration;
import java.util.Optional;

public interface TokenStore {

    void saveRefreshToken(String key, String refreshToken);

    Optional<String> getRefreshToken(String key);

    void deleteRefreshToken(String key);


    void blacklistToken(String token, Duration timeToLive);

    boolean isBlacklisted(String token);
}