package MeshX.HypeLink.auth.service;

import MeshX.HypeLink.auth.exception.AuthException;
import MeshX.HypeLink.auth.exception.AuthExceptionMessage;
import MeshX.HypeLink.auth.exception.TokenException;
import MeshX.HypeLink.auth.exception.TokenExceptionMessage;
import MeshX.HypeLink.auth.model.dto.AuthTokens;
import MeshX.HypeLink.auth.model.dto.LoginReqDto;
import MeshX.HypeLink.auth.model.dto.LoginResDto;
import MeshX.HypeLink.auth.model.dto.RegisterReqDto;
import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.POS;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.*;
import MeshX.HypeLink.auth.utils.JwtUtils;
import MeshX.HypeLink.utils.geocode.model.dto.GeocodeDto;
import MeshX.HypeLink.utils.geocode.service.GeocodingService;
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
    private final DriverJpaRepositoryVerify driverJpaRepositoryVerify;
    private final PosJpaRepositoryVerify posJpaRepositoryVerify;
    private final StoreJpaRepositoryVerify storeJpaRepositoryVerify;
    private final StoreRepository storeRepository; // for finding store for POS
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;
    private final GeocodingService geocodingService;

    public void register(RegisterReqDto requestDto) {
        memberJpaRepositoryVerify.existsByEmail(requestDto.getEmail());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        Member member = requestDto.toMemberEntity(encodedPassword);
        memberJpaRepositoryVerify.save(member);

        switch (requestDto.getRole()) {
            case BRANCH_MANAGER:
                GeocodeDto geocodeDto = geocodingService.getCoordinates(requestDto.getAddress());

                Store store = requestDto.toStoreEntity(member, geocodeDto);
                storeJpaRepositoryVerify.save(store);
                break;

            case DRIVER:
                Driver driver = requestDto.toDriverEntity(member);
                driverJpaRepositoryVerify.save(driver);
                break;

            case POS_MEMBER:
                Store associatedStore = storeRepository.findById(requestDto.getStoreId())
                        .orElseThrow(() -> new AuthException(AuthExceptionMessage.STORE_NOT_FOUND));

                POS pos = requestDto.toPosEntity(member, associatedStore);
                posJpaRepositoryVerify.save(pos);
                break;

            case ADMIN:
            case MANAGER:
                break;
        }
    }

    @Transactional
    public LoginResDto login(LoginReqDto requestDto) {
        Member member = memberJpaRepositoryVerify.findByEmail(requestDto.getEmail());

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new AuthException(AuthExceptionMessage.INVALID_CREDENTIALS);
        }

        AuthTokens authTokens = issueTokens(member.getEmail(), member.getRole().name());

        return LoginResDto.builder()
                .authTokens(authTokens)
                .name(member.getName())
                .role(member.getRole().name())
                .build();
    }

    @Transactional
    public AuthTokens reissueTokens(String refreshToken) {
        jwtUtils.validateToken(refreshToken);

        String email = jwtUtils.getEmailFromToken(refreshToken);
        String role = jwtUtils.getRoleFromToken(refreshToken);

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

    private AuthTokens issueTokens(String email, String role) {
        AuthTokens tokens = jwtUtils.generateTokens(email, role);
        tokenStore.saveRefreshToken(email, tokens.getRefreshToken());
        return tokens;
    }
}
