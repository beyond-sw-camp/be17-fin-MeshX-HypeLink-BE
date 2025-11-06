package com.example.apiauth.usecase.service;

import MeshX.common.UseCase;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import com.example.apiauth.adapter.in.web.dto.LoginResDto;
import com.example.apiauth.adapter.out.security.JwtTokenProvider;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.domain.model.value.AuthTokens;
import com.example.apiauth.usecase.port.in.LoginCommand;
import com.example.apiauth.usecase.port.out.usecase.AuthUseCase;
import com.example.apiauth.usecase.port.out.persistence.MemberPort;
import com.example.apiauth.usecase.port.out.persistence.TokenStorePort;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.apiauth.common.exception.AuthExceptionMessage.INVALID_CREDENTIALS;
import static com.example.apiauth.common.exception.AuthExceptionMessage.USER_NAME_NOT_FOUND;

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

        AuthTokens authTokens = issueTokens(member.getEmail(), member.getRole().name());

        return LoginResDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .authTokens(authTokens)
                .build();
    }


    private AuthTokens issueTokens(String email, String role) {
        AuthTokens tokens = jwtTokenProvider.generateTokens(email, role);
        tokenStorePort.saveRefreshToken(email, tokens.getRefreshToken());
        return tokens;
    }

}
