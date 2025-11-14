package com.example.apiauth.adapter.in.kafka;

import MeshX.common.exception.BaseException;
import com.example.apiauth.adapter.out.external.monolith.dto.DriverSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.MemberSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.PosSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.StoreSyncDto;
import com.example.apiauth.domain.event.DataSyncEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Slf4j
@Component
public class DataSyncEventConsumer {

    private final ObjectMapper objectMapper;
    private final JdbcTemplate readJdbcTemplate;

    public DataSyncEventConsumer(
            ObjectMapper objectMapper,
            @Qualifier("readDB") DataSource readDataSource) {
        this.objectMapper = objectMapper;
        this.readJdbcTemplate = new JdbcTemplate(readDataSource);
    }

    @KafkaListener(topics = "cqrs-sync", groupId = "api-auth-cqrs-consumer", containerFactory = "cqrsKafkaListenerContainerFactory")
    @Transactional
    public void consumeEvent(DataSyncEvent event) {
        try {
            log.info("Received CQRS sync event: operation={}, entityType={}, entityId={}",
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
        } catch (IllegalStateException e) {
            // Member/Store not found 에러는 재시도를 위해 throw
            log.warn("CQRS sync 순서 문제 발생, Kafka 재시도 예정: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Failed to process CQRS sync event", e);
            throw new BaseException(null);
        }
    }

    private void handleMemberEvent(DataSyncEvent event) throws Exception {
        switch (event.getOperation()) {
            case CREATE:
            case UPDATE:
                MemberSyncDto dto = objectMapper.readValue(
                        event.getEntityData(), MemberSyncDto.class);
                upsertMember(dto);
                log.info("Upserted Member to Read DB: id={}", dto.getId());
                break;
            case DELETE:
                readJdbcTemplate.update("DELETE FROM member WHERE id = ?", event.getEntityId());
                log.info("Deleted Member from Read DB: id={}", event.getEntityId());
                break;
        }
    }

    private void upsertMember(MemberSyncDto dto) {
        String sql = """
            INSERT INTO member (id, createdAt, updatedAt, address, email, name, password, phone, refreshToken, region, role)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                createdAt = VALUES(createdAt),
                updatedAt = VALUES(updatedAt),
                address = VALUES(address),
                email = VALUES(email),
                name = VALUES(name),
                password = VALUES(password),
                phone = VALUES(phone),
                refreshToken = VALUES(refreshToken),
                region = VALUES(region),
                role = VALUES(role)
            """;

        readJdbcTemplate.update(sql,
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
                StoreSyncDto dto = objectMapper.readValue(
                        event.getEntityData(), StoreSyncDto.class);
                log.info("Store CQRS Sync - id={}, memberId={}, storeNumber={}",
                        dto.getId(), dto.getMemberId(), dto.getStoreNumber());
                upsertStore(dto);
                log.info("Upserted Store to Read DB: id={}", dto.getId());
                break;
            case DELETE:
                readJdbcTemplate.update("DELETE FROM store WHERE id = ?", event.getEntityId());
                log.info("Deleted Store from Read DB: id={}", event.getEntityId());
                break;
        }
    }

    private void upsertStore(StoreSyncDto dto) {
        // Member 존재 확인
        Integer memberExists = readJdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM member WHERE id = ?",
            Integer.class,
            dto.getMemberId()
        );

        if (memberExists == null || memberExists == 0) {
            log.warn("Member not found for Store, retrying... memberId={}, storeId={}",
                    dto.getMemberId(), dto.getId());
            throw new IllegalStateException("Member not found: " + dto.getMemberId());
        }

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
                dto.getId(),
                dto.getLat() != null ? dto.getLat() : 0.0,
                dto.getLon() != null ? dto.getLon() : 0.0,
                dto.getPosCount() != null ? dto.getPosCount() : 0,
                dto.getStoreNumber(),
                dto.getStoreState() != null ? dto.getStoreState().ordinal() : null,
                dto.getMemberId()
        );
    }

    private void handlePosEvent(DataSyncEvent event) throws Exception {
        switch (event.getOperation()) {
            case CREATE:
            case UPDATE:
                PosSyncDto dto = objectMapper.readValue(
                        event.getEntityData(), PosSyncDto.class);
                upsertPos(dto);
                log.info("Upserted Pos to Read DB: id={}", dto.getId());
                break;
            case DELETE:
                readJdbcTemplate.update("DELETE FROM pos WHERE id = ?", event.getEntityId());
                log.info("Deleted Pos from Read DB: id={}", event.getEntityId());
                break;
        }
    }

    private void upsertPos(PosSyncDto dto) {
        // Member와 Store 존재 확인
        Integer memberExists = readJdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM member WHERE id = ?",
            Integer.class,
            dto.getMemberId()
        );

        Integer storeExists = readJdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM store WHERE id = ?",
            Integer.class,
            dto.getStoreId()
        );

        if (memberExists == null || memberExists == 0) {
            log.warn("Member not found for Pos, retrying... memberId={}, posId={}",
                    dto.getMemberId(), dto.getId());
            throw new IllegalStateException("Member not found: " + dto.getMemberId());
        }

        if (storeExists == null || storeExists == 0) {
            log.warn("Store not found for Pos, retrying... storeId={}, posId={}",
                    dto.getStoreId(), dto.getId());
            throw new IllegalStateException("Store not found: " + dto.getStoreId());
        }

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
                DriverSyncDto dto = objectMapper.readValue(
                        event.getEntityData(), DriverSyncDto.class);
                upsertDriver(dto);
                log.info("Upserted Driver to Read DB: id={}", dto.getId());
                break;
            case DELETE:
                readJdbcTemplate.update("DELETE FROM driver WHERE id = ?", event.getEntityId());
                log.info("Deleted Driver from Read DB: id={}", event.getEntityId());
                break;
        }
    }

    private void upsertDriver(DriverSyncDto dto) {
        // Member 존재 확인
        Integer memberExists = readJdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM member WHERE id = ?",
            Integer.class,
            dto.getMemberId()
        );

        if (memberExists == null || memberExists == 0) {
            log.warn("Member not found for Driver, retrying... memberId={}, driverId={}",
                    dto.getMemberId(), dto.getId());
            throw new IllegalStateException("Member not found: " + dto.getMemberId());
        }

        String sql = """
            INSERT INTO driver (id, carNumber, macAddress, member_id)
            VALUES (?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                carNumber = VALUES(carNumber),
                macAddress = VALUES(macAddress),
                member_id = VALUES(member_id)
            """;

        readJdbcTemplate.update(sql,
                dto.getId(),
                dto.getCarNumber(),
                dto.getMacAddress(),
                dto.getMemberId()
        );
    }
}
