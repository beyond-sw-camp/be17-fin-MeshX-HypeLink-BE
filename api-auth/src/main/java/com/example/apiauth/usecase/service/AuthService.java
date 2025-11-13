package com.example.apiauth.usecase.service;

import MeshX.common.UseCase;
import com.example.apiauth.adapter.in.web.dto.LoginResDto;
import com.example.apiauth.adapter.out.external.geocoding.dto.GeocodeDto;
import com.example.apiauth.adapter.out.security.JwtTokenProvider;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.common.exception.TokenException;
import com.example.apiauth.domain.kafka.MemberRegisterEvent;
import com.example.apiauth.domain.model.Driver;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.domain.model.Pos;
import com.example.apiauth.domain.model.Store;
import com.example.apiauth.domain.model.value.AuthTokens;
import com.example.apiauth.usecase.port.in.LoginCommand;
import com.example.apiauth.usecase.port.in.RegisterCommand;
import com.example.apiauth.usecase.port.in.ReissueTokenCommand;
import com.example.apiauth.usecase.port.out.external.GeocodingPort;
import com.example.apiauth.usecase.port.out.persistence.*;
import com.example.apiauth.usecase.port.out.usecase.AuthUseCase;
import com.example.apiauth.usecase.port.out.kafka.EventPublisherPort;
import com.example.apiauth.adapter.out.kafka.MonolithSyncEventProducer;
import com.example.apiauth.adapter.out.kafka.DataSyncEventProducer;
import com.example.apiauth.domain.event.DataSyncEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.util.regex.Pattern;

import static com.example.apiauth.common.exception.AuthExceptionMessage.*;
import static com.example.apiauth.common.exception.TokenExceptionMessage.INVALID_TOKEN;
import static com.example.apiauth.common.exception.TokenExceptionMessage.TOKEN_NOT_FOUND;


@UseCase
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private static final Pattern POS_CODE_PATTERN = Pattern.compile("^[A-Z]{3}[0-9]{3}_[0-9]{2}$");

    private final MemberQueryPort memberQueryPort;
    private final MemberCommandPort memberCommandPort;
    private final StoreQueryPort storeQueryPort;
    private final StoreCommandPort storeCommandPort;
    private final PosCommandPort posCommandPort;
    private final DriverCommandPort driverCommandPort;
    private final GeocodingPort geocodingPort;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final TokenStorePort tokenStorePort;
    private final EventPublisherPort eventPublisherPort;
    private final MonolithSyncEventProducer monolithSyncEventProducer;
    private final DataSyncEventProducer dataSyncEventProducer;
    private final ObjectMapper objectMapper;

    @Override
    public LoginResDto login(LoginCommand dto) {
        Member member = memberQueryPort.findByEmail(dto.getEmail());

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

    @Override
    public AuthTokens reissue(ReissueTokenCommand reissueTokenCommand) {

        jwtTokenProvider.validateToken(reissueTokenCommand.getRefreshToken());

        String email = jwtTokenProvider.getEmailFromToken(reissueTokenCommand.getRefreshToken());
        String role = jwtTokenProvider.getRoleFromToken(reissueTokenCommand.getRefreshToken());

        String newToken = tokenStorePort.getRefreshToken(email)
                .orElseThrow(() -> new TokenException(TOKEN_NOT_FOUND));

        if (!newToken.equals(reissueTokenCommand.getRefreshToken())) {
            throw new TokenException(INVALID_TOKEN);
        }

        return issueTokens(email, role);
    }

    @Override
    public void logout(String accessToken) {
        jwtTokenProvider.validateToken(accessToken);
        String email = jwtTokenProvider.getEmailFromToken(accessToken);

        tokenStorePort.deleteRefreshToken(email);

        Duration remainingTime = jwtTokenProvider.getRemainingTime(accessToken);
        tokenStorePort.blacklistToken(accessToken, remainingTime);
    }


    @Override
    public void register(RegisterCommand command) {
        if (memberQueryPort.existsByEmail(command.getEmail())) {
            throw new AuthException(EMAIL_ALREADY_EXISTS);
        }

        String encodePassword = passwordEncoder.encode(command.getPassword());

        Member member = Member.createNew(command.getEmail(), encodePassword, command.getName(), command.getPhone(),
                command.getAddress(), command.getRole(), command.getRegion());
        Member savedMember = memberCommandPort.save(member);

        Store savedStore = null;
        Pos savedPos = null;
        Driver savedDriver = null;

        switch (savedMember.getRole()) {
            case BRANCH_MANAGER:
                savedStore = registerStore(command, savedMember);
                break;
            case POS_MEMBER:
                savedPos = registerPos(command, savedMember);
                break;
            case DRIVER:
                savedDriver = registerDriver(command, savedMember);
                break;
        }

        MemberRegisterEvent temp = MemberRegisterEvent.of(savedMember, savedStore, savedDriver, savedPos);
        eventPublisherPort.publishMemberRegistered(temp);

        // CQRS Read DB 동기화
        publishCqrsEvent(savedMember, savedStore, savedPos, savedDriver);

        // 모놀리식 동기화
        publishMonolithSyncEvent(savedMember, savedStore, savedPos, savedDriver);
    }

    private void publishCqrsEvent(Member member, Store store, Pos pos, Driver driver) {
        try {
            // Member CQRS 이벤트
            DataSyncEvent memberEvent = DataSyncEvent.builder()
                    .operation(DataSyncEvent.SyncOperation.CREATE)
                    .entityType(DataSyncEvent.EntityType.MEMBER)
                    .entityId(member.getId())
                    .entityData(objectMapper.writeValueAsString(member))
                    .build();
            dataSyncEventProducer.publishEvent(memberEvent);

            // Store CQRS 이벤트
            if (store != null) {
                DataSyncEvent storeEvent = DataSyncEvent.builder()
                        .operation(DataSyncEvent.SyncOperation.CREATE)
                        .entityType(DataSyncEvent.EntityType.STORE)
                        .entityId(store.getId())
                        .entityData(objectMapper.writeValueAsString(store))
                        .build();
                dataSyncEventProducer.publishEvent(storeEvent);
            }

            // Pos CQRS 이벤트
            if (pos != null) {
                DataSyncEvent posEvent = DataSyncEvent.builder()
                        .operation(DataSyncEvent.SyncOperation.CREATE)
                        .entityType(DataSyncEvent.EntityType.POS)
                        .entityId(pos.getId())
                        .entityData(objectMapper.writeValueAsString(pos))
                        .build();
                dataSyncEventProducer.publishEvent(posEvent);
            }

            // Driver CQRS 이벤트
            if (driver != null) {
                DataSyncEvent driverEvent = DataSyncEvent.builder()
                        .operation(DataSyncEvent.SyncOperation.CREATE)
                        .entityType(DataSyncEvent.EntityType.DRIVER)
                        .entityId(driver.getId())
                        .entityData(objectMapper.writeValueAsString(driver))
                        .build();
                dataSyncEventProducer.publishEvent(driverEvent);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish CQRS event", e);
        }
    }

    private void publishMonolithSyncEvent(Member member, Store store, Pos pos, Driver driver) {
        try {
            // Member → 모놀리식 동기화
            DataSyncEvent memberEvent = DataSyncEvent.builder()
                    .operation(DataSyncEvent.SyncOperation.CREATE)
                    .entityType(DataSyncEvent.EntityType.MEMBER)
                    .entityId(member.getId())
                    .entityData(objectMapper.writeValueAsString(member))
                    .build();
            monolithSyncEventProducer.publishToMonolith(memberEvent);

            // Store → 모놀리식 동기화
            if (store != null) {
                DataSyncEvent storeEvent = DataSyncEvent.builder()
                        .operation(DataSyncEvent.SyncOperation.CREATE)
                        .entityType(DataSyncEvent.EntityType.STORE)
                        .entityId(store.getId())
                        .entityData(objectMapper.writeValueAsString(store))
                        .build();
                monolithSyncEventProducer.publishToMonolith(storeEvent);
            }

            // Pos → 모놀리식 동기화
            if (pos != null) {
                DataSyncEvent posEvent = DataSyncEvent.builder()
                        .operation(DataSyncEvent.SyncOperation.CREATE)
                        .entityType(DataSyncEvent.EntityType.POS)
                        .entityId(pos.getId())
                        .entityData(objectMapper.writeValueAsString(pos))
                        .build();
                monolithSyncEventProducer.publishToMonolith(posEvent);
            }

            // Driver → 모놀리식 동기화
            if (driver != null) {
                DataSyncEvent driverEvent = DataSyncEvent.builder()
                        .operation(DataSyncEvent.SyncOperation.CREATE)
                        .entityType(DataSyncEvent.EntityType.DRIVER)
                        .entityId(driver.getId())
                        .entityData(objectMapper.writeValueAsString(driver))
                        .build();
                monolithSyncEventProducer.publishToMonolith(driverEvent);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish monolith sync event", e);
        }
    }

    private AuthTokens issueTokens(String email, String role) {
        AuthTokens tokens = jwtTokenProvider.generateTokens(email, role);
        tokenStorePort.saveRefreshToken(email, tokens.getRefreshToken());
        return tokens;
    }

    private Store registerStore(RegisterCommand command, Member member) {
        GeocodeDto geocodeDto = geocodingPort.getCoordinates(command.getAddress());
        Store store = Store.createNew(member, geocodeDto.getLatAsDouble(), geocodeDto.getLatAsDouble(),
                command.getStoreNumber());
        return storeCommandPort.save(store);
    }

    private Pos registerPos(RegisterCommand command, Member member) {
        validatePosCode(command.getPosCode());
        Store store = storeQueryPort.findById(command.getStoreId());

        store = store.increasePosCount();
        storeCommandPort.save(store);

        Pos pos = Pos.createNew(member, store, command.getPosCode());

        return posCommandPort.save(pos);
    }

    private void validatePosCode(String posCode) {
        if (posCode == null || !POS_CODE_PATTERN.matcher(posCode).matches()) {
            throw new AuthException(POS_CODE_NOT_MATCH);
        }
    }

    private Driver registerDriver(RegisterCommand command, Member member) {
        Driver driver = Driver.createNew(member, command.getMacAddress(), command.getCarNumber());
        return driverCommandPort.save(driver);
    }

}
