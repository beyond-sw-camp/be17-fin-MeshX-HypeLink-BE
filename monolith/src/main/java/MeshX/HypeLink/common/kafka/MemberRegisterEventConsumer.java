package MeshX.HypeLink.common.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Slf4j
@Component
public class MemberRegisterEventConsumer {

    private final JdbcTemplate jdbcTemplate;
    private final SagaPublisher sagaPublisher;

    public MemberRegisterEventConsumer(DataSource dataSource, SagaPublisher sagaPublisher) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.sagaPublisher = sagaPublisher;
    }

    @KafkaListener(topics = "member-registered", groupId = "monolith-member-consumer", containerFactory = "memberRegisterKafkaListenerFactory")
    @Transactional
    public void consumeMemberRegisterEvent(MemberRegisterEvent event) {
        try {
            log.info("Received MemberRegisterEvent from MSA: memberId={}, role={}",
                    event.getMemberId(), event.getRole());

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
            }


            log.info("Successfully synced Member to Monolith: memberId={}, role={}",
                    event.getMemberId(), event.getRole());
            syncSuccessEvents(event);
        } catch (Exception e) {
            log.error("Failed to process MemberRegisterEvent from MSA", e);
            syncFailedEvents(event, e.getMessage());
        }
    }

    private void upsertMember(MemberRegisterEvent event) {
        LocalDateTime now = LocalDateTime.now();
        String sql = """
                INSERT INTO member (id, created_at, updated_at, address, email, name, password, phone, refresh_token, region, role)
                VALUES (?, ?, ?, ?, ?, ?, '', null, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    updated_at = VALUES(updated_at),
                    address = VALUES(address),
                    email = VALUES(email),
                    name = VALUES(name),
                    phone = VALUES(phone),
                    region = VALUES(region),
                    role = VALUES(role)
                """;

        jdbcTemplate.update(sql,
                event.getMemberId(),
                now,
                now,
                event.getAddress(),
                event.getEmail(),
                event.getName(),
                event.getPhone(),
                event.getRegion() != null ? event.getRegion().name() : null,
                event.getRole() != null ? event.getRole().name() : null
        );
        log.info("Upserted Member to Monolith: id={}", event.getMemberId());
    }

    private void upsertStore(MemberRegisterEvent event) {
        MemberRegisterEvent.StoreInfo storeInfo = event.getStoreInfo();
        String sql = """
                INSERT INTO store (id, lat, lon, pos_count, store_number, store_state, member_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    lat = VALUES(lat),
                    lon = VALUES(lon),
                    pos_count = VALUES(pos_count),
                    store_number = VALUES(store_number),
                    store_state = VALUES(store_state),
                    member_id = VALUES(member_id)
                """;

        jdbcTemplate.update(sql,
                storeInfo.getStoreId(),
                storeInfo.getLat() != null ? storeInfo.getLat() : 0.0,
                storeInfo.getLon() != null ? storeInfo.getLon() : 0.0,
                storeInfo.getPosCount() != null ? storeInfo.getPosCount() : 0,
                storeInfo.getStoreNumber(),
                storeInfo.getStoreState() != null ? storeInfo.getStoreState().ordinal() : 2,
                event.getMemberId()
        );
        log.info("Upserted Store to Monolith: id={}, memberId={}", storeInfo.getStoreId(), event.getMemberId());
    }

    private void upsertPos(MemberRegisterEvent event) {
        MemberRegisterEvent.PosInfo posInfo = event.getPosInfo();
        String sql = """
                INSERT INTO pos (id, health_check, pos_code, member_id, store_id)
                VALUES (?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    health_check = VALUES(health_check),
                    pos_code = VALUES(pos_code),
                    member_id = VALUES(member_id),
                    store_id = VALUES(store_id)
                """;

        jdbcTemplate.update(sql,
                posInfo.getPosId(),
                posInfo.getHealthCheck() != null ? posInfo.getHealthCheck() : false,
                posInfo.getPosCode(),
                event.getMemberId(),
                posInfo.getStoreId()
        );
        log.info("Upserted Pos to Monolith: id={}, memberId={}", posInfo.getPosId(), event.getMemberId());
    }

    private void upsertDriver(MemberRegisterEvent event) {
        MemberRegisterEvent.DriverInfo driverInfo = event.getDriverInfo();
        String sql = """
                INSERT INTO driver (id, car_number, mac_address, member_id)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    car_number = VALUES(car_number),
                    mac_address = VALUES(mac_address),
                    member_id = VALUES(member_id)
                """;

        jdbcTemplate.update(sql,
                driverInfo.getDriverId(),
                driverInfo.getCarNumber(),
                driverInfo.getMacAddress(),
                event.getMemberId()
        );
        log.info("Upserted Driver to Monolith: id={}, memberId={}", driverInfo.getDriverId(), event.getMemberId());
    }

    private void syncSuccessEvents(MemberRegisterEvent event) {
        sagaPublisher.syncSuccess(SagaSuccessEvent.of("MEMBER", event.getMemberId()));

        switch (event.getRole()) {
            case BRANCH_MANAGER:
                if (event.getStoreInfo() != null) {
                    sagaPublisher.syncSuccess(SagaSuccessEvent.of("STORE", event.getStoreInfo().getStoreId()));
                }
                break;
            case POS_MEMBER:
                if (event.getPosInfo() != null) {
                    sagaPublisher.syncSuccess(SagaSuccessEvent.of("POS", event.getPosInfo().getPosId()));
                }
                break;
            case DRIVER:
                if (event.getDriverInfo() != null) {
                    sagaPublisher.syncSuccess(SagaSuccessEvent.of("DRIVER", event.getDriverInfo().getDriverId()));
                }
                break;
        }

    }

    private void syncFailedEvents(MemberRegisterEvent event, String errorMessage) {
        sagaPublisher.syncFailed(SagaFailedEvent.of("MEMBER", event.getMemberId(), errorMessage, event));

        switch (event.getRole()) {
            case BRANCH_MANAGER:
                if (event.getStoreInfo() != null) {
                    sagaPublisher.syncFailed(SagaFailedEvent.of("STORE", event.getStoreInfo().getStoreId(), errorMessage, event));
                }
                break;
            case POS_MEMBER:
                if (event.getPosInfo() != null) {
                    sagaPublisher.syncFailed(SagaFailedEvent.of("POS", event.getPosInfo().getPosId(), errorMessage, event));
                }
                break;
            case DRIVER:
                if (event.getDriverInfo() != null) {
                    sagaPublisher.syncFailed(SagaFailedEvent.of("DRIVER", event.getDriverInfo().getDriverId(), errorMessage, event));
                }
                break;
        }
    }

}
