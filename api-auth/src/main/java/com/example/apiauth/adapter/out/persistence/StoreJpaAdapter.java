package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.kafka.CqrsSyncEventProducer;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.entity.StoreEntity;
import com.example.apiauth.adapter.out.persistence.mapper.StoreMapper;
import com.example.apiauth.adapter.out.persistence.read.entity.StoreReadEntity;
import com.example.apiauth.adapter.out.persistence.read.mapper.StoreReadMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.domain.event.CqrsSyncEvent;
import com.example.apiauth.domain.model.Store;
import com.example.apiauth.usecase.port.out.persistence.StoreCommandPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.apiauth.common.exception.AuthExceptionMessage.USER_NAME_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class StoreJpaAdapter implements StoreCommandPort {

    private final StoreJpaRepository storeJpaRepository;
    private final CqrsSyncEventProducer eventProducer;
    private final ObjectMapper objectMapper;

    @Override
    public Store save(Store store) {
        boolean isUpdate = store.getId() != null;
        StoreEntity storeEntity = storeJpaRepository.save(StoreMapper.toEntity(store));
        Store savedStore = StoreMapper.toDomain(storeEntity);

        // CQRS 이벤트 발행
        try {
            StoreReadEntity readEntity = StoreReadMapper.toEntity(savedStore);
            CqrsSyncEvent event = CqrsSyncEvent.builder()
                    .operation(isUpdate ? CqrsSyncEvent.SyncOperation.UPDATE : CqrsSyncEvent.SyncOperation.CREATE)
                    .entityType(CqrsSyncEvent.EntityType.STORE)
                    .entityId(savedStore.getId())
                    .entityData(objectMapper.writeValueAsString(readEntity))
                    .timestamp(LocalDateTime.now())
                    .build();
            eventProducer.publishEvent(event);
        } catch (Exception e) {
            // 이벤트 발행 실패 시 로깅만 수행
        }

        return savedStore;
    }

    @Override
    public void delete(Integer id) {
        storeJpaRepository.deleteById(id);

        // CQRS 이벤트 발행
        CqrsSyncEvent event = CqrsSyncEvent.builder()
                .operation(CqrsSyncEvent.SyncOperation.DELETE)
                .entityType(CqrsSyncEvent.EntityType.STORE)
                .entityId(id)
                .timestamp(LocalDateTime.now())
                .build();
        eventProducer.publishEvent(event);
    }
}
