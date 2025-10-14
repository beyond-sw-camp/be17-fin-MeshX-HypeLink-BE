package MeshX.HypeLink.auth.service;

import MeshX.HypeLink.auth.exception.AuthException;
import MeshX.HypeLink.auth.exception.AuthExceptionMessage;
import MeshX.HypeLink.auth.exception.TokenException;
import MeshX.HypeLink.auth.exception.TokenExceptionMessage;
import MeshX.HypeLink.auth.model.dto.AuthTokens;
import MeshX.HypeLink.auth.model.dto.LoginReqDto;
import MeshX.HypeLink.auth.model.dto.RegisterReqDto;
import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.auth.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberJpaRepositoryVerify memberJpaRepositoryVerify;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore; // TokenStore 주입

    public AuthTokens register(RegisterReqDto requestDto) {
        memberJpaRepositoryVerify.existsByEmail(requestDto.getEmail());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        Member member = requestDto.toEntity(encodedPassword);
        memberJpaRepositoryVerify.save(member);

        return issueTokens(member.getEmail(), member.getRole().name());
    }

    @Transactional
    public AuthTokens login(LoginReqDto requestDto) {
        Member member = memberJpaRepositoryVerify.findByEmail(requestDto.getEmail());

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new AuthException(AuthExceptionMessage.INVALID_CREDENTIALS);
        }

        return issueTokens(member.getEmail(), member.getRole().name());
    }

    @Transactional
    public AuthTokens reissueTokens(String refreshToken) {
        jwtUtils.validateToken(refreshToken);

        String email = jwtUtils.getEmailFromToken(refreshToken);
        String role = jwtUtils.getRoleFromToken(refreshToken);

        // 저장소의 토큰과 일치하는지 확인
        String storedToken = tokenStore.getRefreshToken(email)
                .orElseThrow(() -> new TokenException(TokenExceptionMessage.TOKEN_NOT_FOUND));

        if (!storedToken.equals(refreshToken)) {
            throw new TokenException(TokenExceptionMessage.INVALID_TOKEN);
        }

        return issueTokens(email, role);
    }

    public void logout(String accessToken) {
        jwtUtils.validateToken(accessToken);
        String email = jwtUtils.getEmailFromToken(accessToken);

        tokenStore.deleteRefreshToken(email);

        Duration remainingTime = jwtUtils.getRemainingTime(accessToken);
        tokenStore.blacklistToken(accessToken, remainingTime);
    }

    /**
     * 토큰 발급 및 저장 공통 메서드
     */
    private AuthTokens issueTokens(String email, String role) {
        AuthTokens tokens = jwtUtils.generateTokens(email, role);
        tokenStore.saveRefreshToken(email, tokens.getRefreshToken());
        return tokens;
    }
}
