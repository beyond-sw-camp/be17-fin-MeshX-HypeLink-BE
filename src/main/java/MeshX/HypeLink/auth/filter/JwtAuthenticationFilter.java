package MeshX.HypeLink.auth.filter;

import MeshX.HypeLink.auth.exception.TokenException;
import MeshX.HypeLink.auth.exception.TokenExceptionMessage;
import MeshX.HypeLink.auth.service.TokenStore;
import MeshX.HypeLink.auth.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 이 필터를 거치지 않을 경로 목록
    private static final String[] EXCLUDE_PATHS = {"/api/auth/", "/swagger-ui/", "/v3/api-docs/", "/favicon.ico","/api/health/"};

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final JwtUtils jwtUtils;
    private final TokenStore tokenStore;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        boolean shouldNotFilter = Arrays.stream(EXCLUDE_PATHS).anyMatch(path::startsWith);
        logger.info("JwtAuthenticationFilter: Path '" + path + "' shouldNotFilter = " + shouldNotFilter);
        return shouldNotFilter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getToken(request);

        if (StringUtils.hasText(jwt)) {
            // getAuthentication 내부에서 토큰 검증(만료 포함)이 함께 이루어집니다.
            Authentication authentication = jwtUtils.getAuthentication(jwt);

            // 블랙리스트 확인 로직 추가
            if (tokenStore.isBlacklisted(jwt)) {
                throw new TokenException(TokenExceptionMessage.BLACKLISTED_TOKEN);
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}