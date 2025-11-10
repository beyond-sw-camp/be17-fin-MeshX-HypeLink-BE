package com.example.apiauth.usecase.service;

import MeshX.common.UseCase;
import com.example.apiauth.adapter.in.web.dto.LoginResDto;
import com.example.apiauth.adapter.out.security.JwtTokenProvider;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.common.exception.TokenException;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.domain.model.value.AuthTokens;
import com.example.apiauth.usecase.port.in.LoginCommand;
import com.example.apiauth.usecase.port.in.RegisterCommand;
import com.example.apiauth.usecase.port.in.ReissueTokenCommand;
import com.example.apiauth.usecase.port.out.persistence.MemberPort;
import com.example.apiauth.usecase.port.out.persistence.TokenStorePort;
import com.example.apiauth.usecase.port.out.usecase.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;

import static com.example.apiauth.common.exception.AuthExceptionMessage.INVALID_CREDENTIALS;
import static com.example.apiauth.common.exception.TokenExceptionMessage.INVALID_TOKEN;
import static com.example.apiauth.common.exception.TokenExceptionMessage.TOKEN_NOT_FOUND;

@UseCase
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {


    private final MemberPort memberPort;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final TokenStorePort tokenStorePort;

    @Override
    public LoginResDto login(LoginCommand dto) {
        Member member = memberPort.findByEmail(dto.getEmail());

        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new AuthException(INVALID_CREDENTIALS);
        }

        AuthTokens authTokens = issueTokens(member.getId(), member.getEmail(), member.getRole().name());

        return LoginResDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .authTokens(authTokens)
                .build();
    }

    @Override
    public AuthTokens reissue(ReissueTokenCommand reissueTokenCommand) {

        jwtTokenProvider.validateToken(reissueTokenCommand.getRefreshToken());

        Integer memberId = jwtTokenProvider.getMemberIdFromToken(reissueTokenCommand.getRefreshToken());
        String email = jwtTokenProvider.getEmailFromToken(reissueTokenCommand.getRefreshToken());
        String role = jwtTokenProvider.getRoleFromToken(reissueTokenCommand.getRefreshToken());

        String newToken = tokenStorePort.getRefreshToken(email)
                .orElseThrow(() -> new TokenException(TOKEN_NOT_FOUND));

        if(!newToken.equals(reissueTokenCommand.getRefreshToken())) {
            throw new TokenException(INVALID_TOKEN);
        }

        return issueTokens(memberId, email, role);
    }

    @Override
    public void logout(String accessToken) {
        jwtTokenProvider.validateToken(accessToken);
        String email = jwtTokenProvider.getEmailFromToken(accessToken);

        tokenStorePort.deleteRefreshToken(email);

        Duration remainingTime = jwtTokenProvider.getRemainingTime(accessToken);
        tokenStorePort.blacklistToken(accessToken, remainingTime);
    }

    private AuthTokens issueTokens(Integer memberId, String email, String role) {
        AuthTokens tokens = jwtTokenProvider.generateTokens(memberId, email, role);
        tokenStorePort.saveRefreshToken(email, tokens.getRefreshToken());
        return tokens;
    }


    @Override
    public void register(RegisterCommand command) {

    }

}
