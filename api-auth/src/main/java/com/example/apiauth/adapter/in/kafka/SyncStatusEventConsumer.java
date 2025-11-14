package com.example.apiauth.adapter.in.kafka;

import com.example.apiauth.adapter.out.persistence.SyncRetryJpaRepository;
import com.example.apiauth.adapter.out.persistence.entity.SyncRetryEntity;
import com.example.apiauth.adapter.out.persistence.mapper.SyncRetryMapper;
import com.example.apiauth.domain.kafka.MemberRegisterEvent;
import com.example.apiauth.domain.kafka.SyncFailedEvent;
import com.example.apiauth.domain.kafka.SyncSuccessEvent;
import com.example.apiauth.domain.model.SyncRetry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class SyncStatusEventConsumer {

    private final JdbcTemplate writeJdbcTemplate;
    private final JdbcTemplate readJdbcTemplate;
    private final SyncRetryJpaRepository syncRetryRepository;
    private final ObjectMapper objectMapper;

    public SyncStatusEventConsumer(
            @Qualifier("writeDB") DataSource writeDataSource,
            @Qualifier("readDB") DataSource readDataSource,
            SyncRetryJpaRepository syncRetryRepository,
            ObjectMapper objectMapper
    ) {
        this.writeJdbcTemplate = new JdbcTemplate(writeDataSource);
        this.readJdbcTemplate = new JdbcTemplate(readDataSource);
        this.syncRetryRepository = syncRetryRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "sync-success", groupId = "api-auth-sync-status", containerFactory = "sagaKafkaListenerContainerFactory")
    public void SyncSuccess(SyncSuccessEvent event) {

        String sql = "UPDATE " + getTableName(event.getEntityType()) +
                " SET syncStatus = 'SYNCED' WHERE id = ?";

        writeJdbcTemplate.update(sql, event.getEntityId());
        readJdbcTemplate.update(sql, event.getEntityId());

        // sync_retry 테이블에서 삭제
        deleteSyncRetry(event.getEntityType(), event.getEntityId());
    }

    private void deleteSyncRetry(String entityType, Integer entityId) {
        String sql = "DELETE FROM sync_retry WHERE entityType = ? AND entityId = ?";
        int deleted = writeJdbcTemplate.update(sql, entityType, entityId);

        if (deleted > 0) {
            log.info("Deleted SyncRetry: entityType={}, entityId={}", entityType, entityId);
        }
    }

    @KafkaListener(topics = "sync-failed", groupId = "api-auth-sync-status", containerFactory = "sagaKafkaListenerContainerFactory")
    public void SyncFailed(SyncFailedEvent event) {
        String sql = "UPDATE " + getTableName(event.getEntityType()) +
                " SET syncStatus = 'SYNC_FAILED' WHERE id = ?";

        writeJdbcTemplate.update(sql, event.getEntityId());
        readJdbcTemplate.update(sql, event.getEntityId());

        saveSyncRetry(event);
    }

    private void saveSyncRetry(SyncFailedEvent event) {
        try {
            MemberRegisterEvent originalEvent = event.getOriginalEvent();
            String eventJson = objectMapper.writeValueAsString(originalEvent);

            SyncRetry syncRetry = SyncRetry.create(
                    event.getEntityType(),
                    event.getEntityId(),
                    eventJson,
                    event.getErrorMessage(),
                    event.getRetryAt()
            );

            SyncRetryEntity entity = SyncRetryMapper.toEntity(syncRetry);
            syncRetryRepository.save(entity);

            log.info("Saved SyncRetry: entityType={}, entityId={}", event.getEntityType(), event.getEntityId());

        } catch (JsonProcessingException e) {
            log.error("Failed to serialize MemberRegisterEvent for entityId={}", event.getEntityId(), e);
        }
    }

    private static final Map<String, String> TABLE_MAP = Map.of(
            "MEMBER", "member",
            "STORE", "store",
            "POS", "pos",
            "DRIVER", "driver"
    );

    private String getTableName(String entityType) {
        return Optional.ofNullable(TABLE_MAP.get(entityType))
                .orElseThrow(() -> new IllegalArgumentException("Unknown: " + entityType));
    }
}
