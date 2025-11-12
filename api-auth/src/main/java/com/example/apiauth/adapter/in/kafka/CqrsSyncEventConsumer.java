package com.example.apiauth.adapter.in.kafka;

import com.example.apiauth.adapter.out.persistence.read.entity.DriverReadEntity;
import com.example.apiauth.adapter.out.persistence.read.entity.MemberReadEntity;
import com.example.apiauth.adapter.out.persistence.read.entity.PosReadEntity;
import com.example.apiauth.adapter.out.persistence.read.entity.StoreReadEntity;
import com.example.apiauth.adapter.out.persistence.read.DriverReadRepository;
import com.example.apiauth.adapter.out.persistence.read.MemberReadRepository;
import com.example.apiauth.adapter.out.persistence.read.PosReadRepository;
import com.example.apiauth.adapter.out.persistence.read.StoreReadRepository;
import com.example.apiauth.domain.event.CqrsSyncEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CqrsSyncEventConsumer {

    private final MemberReadRepository memberReadRepository;
    private final StoreReadRepository storeReadRepository;
    private final PosReadRepository posReadRepository;
    private final DriverReadRepository driverReadRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "cqrs-sync", groupId = "api-auth-cqrs-consumer")
    @Transactional
    public void consumeEvent(String eventJson) {
        try {
            CqrsSyncEvent event = objectMapper.readValue(eventJson, CqrsSyncEvent.class);

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
        } catch (Exception e) {
            log.error("Failed to process CQRS sync event", e);
        }
    }

    private void handleMemberEvent(CqrsSyncEvent event) throws Exception {
        switch (event.getOperation()) {
            case CREATE:
            case UPDATE:
                MemberReadEntity memberEntity = objectMapper.readValue(
                        event.getEntityData(), MemberReadEntity.class);
                memberReadRepository.save(memberEntity);
                log.info("Synced Member to Read DB: id={}", memberEntity.getId());
                break;
            case DELETE:
                memberReadRepository.deleteById(event.getEntityId());
                log.info("Deleted Member from Read DB: id={}", event.getEntityId());
                break;
        }
    }

    private void handleStoreEvent(CqrsSyncEvent event) throws Exception {
        switch (event.getOperation()) {
            case CREATE:
            case UPDATE:
                StoreReadEntity storeEntity = objectMapper.readValue(
                        event.getEntityData(), StoreReadEntity.class);
                storeReadRepository.save(storeEntity);
                log.info("Synced Store to Read DB: id={}", storeEntity.getId());
                break;
            case DELETE:
                storeReadRepository.deleteById(event.getEntityId());
                log.info("Deleted Store from Read DB: id={}", event.getEntityId());
                break;
        }
    }

    private void handlePosEvent(CqrsSyncEvent event) throws Exception {
        switch (event.getOperation()) {
            case CREATE:
            case UPDATE:
                PosReadEntity posEntity = objectMapper.readValue(
                        event.getEntityData(), PosReadEntity.class);
                posReadRepository.save(posEntity);
                log.info("Synced Pos to Read DB: id={}", posEntity.getId());
                break;
            case DELETE:
                posReadRepository.deleteById(event.getEntityId());
                log.info("Deleted Pos from Read DB: id={}", event.getEntityId());
                break;
        }
    }

    private void handleDriverEvent(CqrsSyncEvent event) throws Exception {
        switch (event.getOperation()) {
            case CREATE:
            case UPDATE:
                DriverReadEntity driverEntity = objectMapper.readValue(
                        event.getEntityData(), DriverReadEntity.class);
                driverReadRepository.save(driverEntity);
                log.info("Synced Driver to Read DB: id={}", driverEntity.getId());
                break;
            case DELETE:
                driverReadRepository.deleteById(event.getEntityId());
                log.info("Deleted Driver from Read DB: id={}", event.getEntityId());
                break;
        }
    }
}
