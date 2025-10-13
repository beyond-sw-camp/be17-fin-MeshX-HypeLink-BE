package MeshX.HypeLink.auth.utils;

import MeshX.HypeLink.auth.exception.TokenException;
import MeshX.HypeLink.auth.exception.TokenExceptionMessage;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtils {
    private static final String AUTHORITIES_KEY = "role";
    private final Key key;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;

    public JwtUtils(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.access-token-expiration-ms}") long accessTokenExpirationMs,
            @Value("${jwt.refresh-token-expiration-ms}") long refreshTokenExpirationMs) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    // Access Token 생성
    public String createAccessToken(String email, String role) {
        return generateToken(email, role, accessTokenExpirationMs);
    }

    // Refresh Token 생성 (권한 정보 없음)
    public String createRefreshToken(String email) {
        return generateToken(email, null, refreshTokenExpirationMs);
    }

    private String generateToken(String email, String role, long expirationMs) {
        long now = (new Date()).getTime();
        Date expiryDate = new Date(now + expirationMs);

        JwtBuilder builder = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256);

        if (role != null) {
            builder.claim(AUTHORITIES_KEY, role);
        }

        return builder.compact();
    }

    // 토큰에서 인증 정보 조회 (Spring Security 연동)
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
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

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}