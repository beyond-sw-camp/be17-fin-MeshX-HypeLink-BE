package MeshX.HypeLink.common.kafka;

import MeshX.HypeLink.auth.model.dto.sync.DriverSyncDto;
import MeshX.HypeLink.auth.model.dto.sync.MemberSyncDto;
import MeshX.HypeLink.auth.model.dto.sync.PosSyncDto;
import MeshX.HypeLink.auth.model.dto.sync.StoreSyncDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Slf4j
@Component
public class MsaSyncEventConsumer {

    private final ObjectMapper objectMapper;
    private final JdbcTemplate jdbcTemplate;

    public MsaSyncEventConsumer(ObjectMapper objectMapper, DataSource dataSource) {
        this.objectMapper = objectMapper;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @KafkaListener(topics = "monolith-sync", groupId = "monolith-consumer")
    @Transactional
    public void consumeFromMsa(DataSyncEvent event) {
        try {

            log.info("Received sync event from MSA: operation={}, entityType={}, entityId={}",
                    event.getOperation(), event.getEntityType(), event.getEntityId());

            switch (event.getEntityType()) {
                case MEMBER:
                    handleMemberEvent(event);
                    break;
                case STORE:
                    handleStoreEvent(event);
                    break;
                case POS:
                    handlePosEvent(event);
                    break;
                case DRIVER:
                    handleDriverEvent(event);
                    break;
            }
        } catch (Exception e) {
            log.error("Failed to process MSA sync event", e);
        }
    }

    private void handleMemberEvent(DataSyncEvent event) throws Exception {
        switch (event.getOperation()) {
            case CREATE:
            case UPDATE:
                MemberSyncDto dto = objectMapper.readValue(event.getEntityData(), MemberSyncDto.class);
                upsertMember(dto);
                log.info("Upserted Member from MSA: id={}", dto.getId());
                break;

            case DELETE:
                jdbcTemplate.update("DELETE FROM member WHERE id = ?", event.getEntityId());
                log.info("Deleted Member from MSA: id={}", event.getEntityId());
                break;
        }
    }

    private void upsertMember(MemberSyncDto dto) {
        String sql = """
            INSERT INTO member (id, created_at, updated_at, address, email, name, password, phone, refresh_token, region, role, is_deleted)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, false)
            ON DUPLICATE KEY UPDATE
                created_at = VALUES(created_at),
                updated_at = VALUES(updated_at),
                address = VALUES(address),
                email = VALUES(email),
                name = VALUES(name),
                password = VALUES(password),
                phone = VALUES(phone),
                refresh_token = VALUES(refresh_token),
                region = VALUES(region),
                role = VALUES(role)
            """;

        jdbcTemplate.update(sql,
                dto.getId(),
                dto.getCreatedAt(),
                dto.getUpdatedAt(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getName(),
                dto.getPassword(),
                dto.getPhone(),
                dto.getRefreshToken(),
                dto.getRegion() != null ? dto.getRegion().name() : null,
                dto.getRole() != null ? dto.getRole().name() : null
        );
    }

    private void handleStoreEvent(DataSyncEvent event) throws Exception {
        switch (event.getOperation()) {
            case CREATE:
            case UPDATE:
                StoreSyncDto dto = objectMapper.readValue(event.getEntityData(), StoreSyncDto.class);
                upsertStore(dto);
                log.info("Upserted Store from MSA: id={}", dto.getId());
                break;

            case DELETE:
                jdbcTemplate.update("DELETE FROM store WHERE id = ?", event.getEntityId());
                log.info("Deleted Store from MSA: id={}", event.getEntityId());
                break;
        }
    }

    private void upsertStore(StoreSyncDto dto) {
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
                dto.getId(),
                dto.getLat() != null ? dto.getLat() : 0.0,
                dto.getLon() != null ? dto.getLon() : 0.0,
                dto.getPosCount() != null ? dto.getPosCount() : 0,
                dto.getStoreNumber(),
                dto.getStoreState() != null ? dto.getStoreState().name() : null,
                dto.getMemberId()
        );
    }

    private void handlePosEvent(DataSyncEvent event) throws Exception {
        switch (event.getOperation()) {
            case CREATE:
            case UPDATE:
                PosSyncDto dto = objectMapper.readValue(event.getEntityData(), PosSyncDto.class);
                upsertPos(dto);
                log.info("Upserted POS from MSA: id={}", dto.getId());
                break;

            case DELETE:
                jdbcTemplate.update("DELETE FROM pos WHERE id = ?", event.getEntityId());
                log.info("Deleted POS from MSA: id={}", event.getEntityId());
                break;
        }
    }

    private void upsertPos(PosSyncDto dto) {
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
                dto.getId(),
                dto.getHealthCheck() != null ? dto.getHealthCheck() : false,
                dto.getPosCode(),
                dto.getMemberId(),
                dto.getStoreId()
        );
    }

    private void handleDriverEvent(DataSyncEvent event) throws Exception {
        switch (event.getOperation()) {
            case CREATE:
            case UPDATE:
                DriverSyncDto dto = objectMapper.readValue(event.getEntityData(), DriverSyncDto.class);
                upsertDriver(dto);
                log.info("Upserted Driver from MSA: id={}", dto.getId());
                break;

            case DELETE:
                jdbcTemplate.update("DELETE FROM driver WHERE id = ?", event.getEntityId());
                log.info("Deleted Driver from MSA: id={}", event.getEntityId());
                break;
        }
    }

    private void upsertDriver(DriverSyncDto dto) {
        String sql = """
            INSERT INTO driver (id, car_number, mac_address, member_id, is_deleted)
            VALUES (?, ?, ?, ?, false)
            ON DUPLICATE KEY UPDATE
                car_number = VALUES(car_number),
                mac_address = VALUES(mac_address),
                member_id = VALUES(member_id)
            """;

        jdbcTemplate.update(sql,
                dto.getId(),
                dto.getCarNumber(),
                dto.getMacAddress(),
                dto.getMemberId()
        );
    }
}
