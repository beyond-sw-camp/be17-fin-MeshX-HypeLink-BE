package com.example.apiauth.common.security;

import com.example.apiauth.common.exception.TokenException;
import com.example.apiauth.common.exception.TokenExceptionMessage;
import com.example.apiauth.domain.model.value.AuthTokens;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final String AUTHORITIES_KEY = "role";
    private final Key key;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;

    public JwtTokenProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.access-token-expiration-ms}") long accessTokenExpirationMs,
            @Value("${jwt.refresh-token-expiration-ms}") long refreshTokenExpirationMs) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    public String createAccessToken(String email, String role) {
        return generateToken(email, role, accessTokenExpirationMs);
    }

    public String createRefreshToken(String email, String role) {
        return generateToken(email, role, refreshTokenExpirationMs);
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new TokenException(TokenExceptionMessage.INVALID_SIGNATURE);
        } catch (ExpiredJwtException e) {
            throw new TokenException(TokenExceptionMessage.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new TokenException(TokenExceptionMessage.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new TokenException(TokenExceptionMessage.INVALID_TOKEN);
        }
    }

    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return parseClaims(token).get(AUTHORITIES_KEY, String.class);
    }

    public Duration getRemainingTime(String token) {
        Date expiration = parseClaims(token).getExpiration();
        long remainingMillis = expiration.getTime() - System.currentTimeMillis();
        return Duration.ofMillis(remainingMillis > 0 ? remainingMillis : 0);
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            // 만료된 토큰의 경우 예외를 다시 던져서 상위에서 처리하도록 함
            throw e;
        }
    }

    public AuthTokens generateTokens(String email, String role) {
        String accessToken = createAccessToken(email, role);
        String refreshToken = createRefreshToken(email, role);
        return new AuthTokens(accessToken, refreshToken);
    }

    private String generateToken(String email, String role, long expirationMs) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        JwtBuilder builder = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256);

        if (role != null) {
            builder.claim(AUTHORITIES_KEY, role);
        }

        return builder.compact();
    }
}
