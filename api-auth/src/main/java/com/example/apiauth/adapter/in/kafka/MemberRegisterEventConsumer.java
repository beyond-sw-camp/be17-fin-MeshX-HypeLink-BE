package com.example.apiauth.adapter.in.kafka;

import com.example.apiauth.domain.kafka.MemberRegisterEvent;
import com.example.apiauth.domain.model.value.MemberRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Slf4j
@Component
public class MemberRegisterEventConsumer {

    private final JdbcTemplate readJdbcTemplate;

    public MemberRegisterEventConsumer(@Qualifier("readDB") DataSource readDataSource) {
        this.readJdbcTemplate = new JdbcTemplate(readDataSource);
    }

    @KafkaListener(topics = "member-registered", groupId = "api-auth-read-consumer", containerFactory = "memberRegisterKafkaListenerContainerFactory")
    @Transactional
    public void consumeMemberRegisterEvent(MemberRegisterEvent event) {
        try {
            log.info("Received MemberRegisterEvent: memberId={}, role={}", event.getMemberId(), event.getRole());

            // 1. Member 저장 (모든 Role 공통)
            upsertMember(event);

            // 2. Role별 추가 데이터 저장
            switch (event.getRole()) {
                case BRANCH_MANAGER:
                    if (event.getStoreInfo() != null) {
                        upsertStore(event);
                    }
                    break;
                case POS_MEMBER:
                    if (event.getPosInfo() != null) {
                        upsertPos(event);
                    }
                    break;
                case DRIVER:
                    if (event.getDriverInfo() != null) {
                        upsertDriver(event);
                    }
                    break;
                case ADMIN:
                case MANAGER:
                    // Member만 저장
                    break;
            }

            log.info("Successfully synced to Read DB: memberId={}, role={}", event.getMemberId(), event.getRole());
        } catch (Exception e) {
            log.error("Failed to process MemberRegisterEvent", e);
        }
    }

    private void upsertMember(MemberRegisterEvent event) {
        String sql = """
            INSERT INTO member (id, createdAt, updatedAt, address, email, name, password, phone, refreshToken, region, role)
            VALUES (?, ?, ?, ?, ?, ?, '', ?, null, ?, ?)
            ON DUPLICATE KEY UPDATE
                createdAt = VALUES(createdAt),
                updatedAt = VALUES(updatedAt),
                address = VALUES(address),
                email = VALUES(email),
                name = VALUES(name),
                phone = VALUES(phone),
                region = VALUES(region),
                role = VALUES(role)
            """;

        readJdbcTemplate.update(sql,
                event.getMemberId(),
                event.getCreatedAt(),
                event.getUpdatedAt(),
                event.getAddress(),
                event.getEmail(),
                event.getName(),
                event.getPhone(),
                event.getRegion() != null ? event.getRegion().name() : null,
                event.getRole() != null ? event.getRole().name() : null
        );
        log.info("Upserted Member to Read DB: id={}", event.getMemberId());
    }

    private void upsertStore(MemberRegisterEvent event) {
        MemberRegisterEvent.StoreInfo storeInfo = event.getStoreInfo();
        String sql = """
            INSERT INTO store (id, lat, lon, posCount, storeNumber, storeState, member_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                lat = VALUES(lat),
                lon = VALUES(lon),
                posCount = VALUES(posCount),
                storeNumber = VALUES(storeNumber),
                storeState = VALUES(storeState),
                member_id = VALUES(member_id)
            """;

        readJdbcTemplate.update(sql,
                storeInfo.getStoreId(),
                storeInfo.getLat() != null ? storeInfo.getLat() : 0.0,
                storeInfo.getLon() != null ? storeInfo.getLon() : 0.0,
                storeInfo.getPosCount() != null ? storeInfo.getPosCount() : 0,
                storeInfo.getStoreNumber(),
                storeInfo.getStoreState() != null ? storeInfo.getStoreState().ordinal() : null,
                event.getMemberId()
        );
        log.info("Upserted Store to Read DB: id={}, memberId={}", storeInfo.getStoreId(), event.getMemberId());
    }

    private void upsertPos(MemberRegisterEvent event) {
        MemberRegisterEvent.PosInfo posInfo = event.getPosInfo();
        String sql = """
            INSERT INTO pos (id, healthCheck, posCode, member_id, store_id)
            VALUES (?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                healthCheck = VALUES(healthCheck),
                posCode = VALUES(posCode),
                member_id = VALUES(member_id),
                store_id = VALUES(store_id)
            """;

        readJdbcTemplate.update(sql,
                posInfo.getPosId(),
                posInfo.getHealthCheck() != null ? posInfo.getHealthCheck() : false,
                posInfo.getPosCode(),
                event.getMemberId(),
                posInfo.getStoreId()
        );
        log.info("Upserted Pos to Read DB: id={}, memberId={}", posInfo.getPosId(), event.getMemberId());
    }

    private void upsertDriver(MemberRegisterEvent event) {
        MemberRegisterEvent.DriverInfo driverInfo = event.getDriverInfo();
        String sql = """
            INSERT INTO driver (id, carNumber, macAddress, member_id)
            VALUES (?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                carNumber = VALUES(carNumber),
                macAddress = VALUES(macAddress),
                member_id = VALUES(member_id)
            """;

        readJdbcTemplate.update(sql,
                driverInfo.getDriverId(),
                driverInfo.getCarNumber(),
                driverInfo.getMacAddress(),
                event.getMemberId()
        );
        log.info("Upserted Driver to Read DB: id={}, memberId={}", driverInfo.getDriverId(), event.getMemberId());
    }
}
