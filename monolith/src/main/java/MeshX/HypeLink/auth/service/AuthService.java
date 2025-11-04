package MeshX.HypeLink.auth.service;

import MeshX.HypeLink.auth.exception.AuthException;
import MeshX.HypeLink.auth.exception.AuthExceptionMessage;
import MeshX.HypeLink.auth.exception.TokenException;
import MeshX.HypeLink.auth.exception.TokenExceptionMessage;
import MeshX.HypeLink.auth.model.dto.AuthTokens;
import MeshX.HypeLink.auth.model.dto.req.LoginReqDto;
import MeshX.HypeLink.auth.model.dto.req.RegisterReqDto;
import MeshX.HypeLink.auth.model.dto.res.LoginResDto;
import MeshX.HypeLink.auth.model.entity.*;
import MeshX.HypeLink.auth.repository.DriverJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.PosJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.auth.utils.JwtUtils;
import MeshX.HypeLink.utils.geocode.model.dto.GeocodeDto;
import MeshX.HypeLink.utils.geocode.service.GeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.regex.Pattern;

import static MeshX.HypeLink.auth.exception.AuthExceptionMessage.POS_CODE_NOT_MATCH;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberJpaRepositoryVerify memberJpaRepositoryVerify;
    private final DriverJpaRepositoryVerify driverJpaRepositoryVerify;
    private final PosJpaRepositoryVerify posJpaRepositoryVerify;
    private final StoreJpaRepositoryVerify storeJpaRepositoryVerify;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;
    private final GeocodingService geocodingService;

    private static final Pattern POS_CODE_PATTERN = Pattern.compile("^[A-Z]{3}[0-9]{3}_[0-9]{2}$");
    // POSCODE를 위한 패턴 추가

    public void register(RegisterReqDto requestDto) {
        memberJpaRepositoryVerify.existsByEmail(requestDto.getEmail());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        if (requestDto.getRole().equals(MemberRole.POS_MEMBER)) {
            Store associatedStore = storeJpaRepositoryVerify.findById(requestDto.getStoreId());

            associatedStore.increasePosCount();
            storeJpaRepositoryVerify.save(associatedStore);

            Member member = Member.builder()
                    .email(requestDto.getEmail())
                    .password(encodedPassword)
                    .name(requestDto.getName())
                    .phone(associatedStore.getMember().getPhone())
                    .address(associatedStore.getMember().getAddress())
                    .role(MemberRole.POS_MEMBER)
                    .region(associatedStore.getMember().getRegion())
                    .build();

            validatePosCode(requestDto.getPosCode());


            POS pos = requestDto.toPosEntity(member,associatedStore);
            memberJpaRepositoryVerify.save(member);
            posJpaRepositoryVerify.save(pos);
            return;
        }


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

            case ADMIN:
            case MANAGER:
                break;
        }
    }

    private void validatePosCode(String posCode) {
        if (posCode == null || !POS_CODE_PATTERN.matcher(posCode).matches()) {
            throw new AuthException(POS_CODE_NOT_MATCH);
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
                .email(member.getEmail())
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
