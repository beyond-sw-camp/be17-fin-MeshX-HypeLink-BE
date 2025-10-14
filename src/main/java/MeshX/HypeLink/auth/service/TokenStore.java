package MeshX.HypeLink.auth.service;

import java.time.Duration;
import java.util.Optional;

public interface TokenStore {

    void saveRefreshToken(String key, String refreshToken);

    Optional<String> getRefreshToken(String key);

    void deleteRefreshToken(String key);


    /**
     * 로그아웃 등으로 무효화된 토큰(주로 Access Token)을 블랙리스트에 추가합니다.
     * 남은 만료 시간만큼만 저장하여 메모리를 효율적으로 사용합니다.
     * @param token 블랙리스트에 추가할 토큰
     * @param timeToLive 남은 유효 시간
     */
    void blacklistToken(String token, Duration timeToLive);

    /**
     * 해당 토큰이 블랙리스트에 포함되어 있는지 확인합니다.
     * @param token 확인할 토큰
     * @return 블랙리스트에 있으면 true, 아니면 false
     */
    boolean isBlacklisted(String token);
}