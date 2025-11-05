package org.example.apiauth.adapter.out.redis;

import org.example.apiauth.usecase.port.out.TokenStorePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTokenStoreAdapter implements TokenStorePort {
    private final RedisTemplate<String, String> redisTemplate;
    private final long refreshTokenExpirationMs;

    // 리프레시 토큰의 키는 "RT:" 접두사를 붙여 구분
    private static final String REFRESH_TOKEN_KEY_PREFIX = "RT:";

    public RedisTokenStoreAdapter(RedisTemplate<String, String> redisTemplate, @Value("${jwt.refresh-token-expiration-ms}") long refreshTokenExpirationMs) {
        this.redisTemplate = redisTemplate;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    @Override
    public void saveRefreshToken(String key, String refreshToken) {
        String redisKey = REFRESH_TOKEN_KEY_PREFIX + key;
        redisTemplate.opsForValue().set(redisKey, refreshToken, refreshTokenExpirationMs, TimeUnit.MILLISECONDS);
    }

    @Override
    public Optional<String> getRefreshToken(String key) {
        String redisKey = REFRESH_TOKEN_KEY_PREFIX + key;
        String token = redisTemplate.opsForValue().get(redisKey);
        return Optional.ofNullable(token);
    }

    @Override
    public void deleteRefreshToken(String key) {
        String redisKey = REFRESH_TOKEN_KEY_PREFIX + key;
        redisTemplate.delete(redisKey);
    }

    @Override
    public void blacklistToken(String token, Duration timeToLive) {
        if (timeToLive.toSeconds() > 0) {
            redisTemplate.opsForValue().set(token, "logout", timeToLive.toSeconds(), TimeUnit.SECONDS);
        }
    }

    @Override
    public boolean isBlacklisted(String token) {
        // 키(토큰)가 존재하는지 여부로 블랙리스트 포함 확인
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
