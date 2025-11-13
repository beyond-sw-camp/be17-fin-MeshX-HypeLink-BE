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

    public MemberRegisterEventConsumer(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @KafkaListener(topics = "member-registered", groupId = "monolith-member-consumer")
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
                case ADMIN:
                case MANAGER:
                    // Member만 저장
                    break;
            }

            log.info("Successfully synced Member to Monolith: memberId={}, role={}",
                    event.getMemberId(), event.getRole());
        } catch (Exception e) {
            log.error("Failed to process MemberRegisterEvent from MSA", e);
        }
    }

    private void upsertMember(MemberRegisterEvent event) {
        LocalDateTime now = LocalDateTime.now();
        String sql = """
            INSERT INTO member (id, created_at, updated_at, address, email, name, password, phone, refresh_token, region, role, is_deleted)
            VALUES (?, ?, ?, ?, ?, ?, '', null, ?, ?, ?, false)
            ON DUPLICATE KEY UPDATE
                created_at = VALUES(created_at),
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
            INSERT INTO store (id, lat, lon, pos_count, store_number, store_state, member_id, is_deleted)
            VALUES (?, ?, ?, ?, ?, ?, ?, false)
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
                storeInfo.getStoreState() != null ? storeInfo.getStoreState().name() : null,
                event.getMemberId()
        );
        log.info("Upserted Store to Monolith: id={}, memberId={}", storeInfo.getStoreId(), event.getMemberId());
    }

    private void upsertPos(MemberRegisterEvent event) {
        MemberRegisterEvent.PosInfo posInfo = event.getPosInfo();
        String sql = """
            INSERT INTO pos (id, health_check, pos_code, member_id, store_id, is_deleted)
            VALUES (?, ?, ?, ?, ?, false)
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
            INSERT INTO driver (id, car_number, mac_address, member_id, is_deleted)
            VALUES (?, ?, ?, ?, false)
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
}
