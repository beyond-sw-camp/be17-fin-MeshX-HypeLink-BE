package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.external.monolith.dto.StoreSyncDto;
import com.example.apiauth.adapter.out.kafka.DataSyncEventProducer;
import com.example.apiauth.adapter.out.persistence.entity.StoreEntity;
import com.example.apiauth.adapter.out.persistence.mapper.StoreMapper;
import com.example.apiauth.domain.event.DataSyncEvent;
import com.example.apiauth.domain.model.Store;
import com.example.apiauth.usecase.port.out.persistence.StoreCommandPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@PersistenceAdapter
@RequiredArgsConstructor
public class StoreJpaAdapter implements StoreCommandPort {

    private final StoreJpaRepository storeJpaRepository;
    private final DataSyncEventProducer eventProducer;
    private final ObjectMapper objectMapper;

    @Override
    public Store save(Store store) {
        boolean isUpdate = store.getId() != null;
        StoreEntity storeEntity = storeJpaRepository.save(StoreMapper.toEntity(store));
        Store savedStore = StoreMapper.toDomain(storeEntity);

        // CQRS 이벤트 발행
        try {
            Integer memberId = savedStore.getMember() != null ? savedStore.getMember().getId() : null;

            StoreSyncDto syncDto = StoreSyncDto.builder()
                    .id(savedStore.getId())
                    .lat(savedStore.getLat())
                    .lon(savedStore.getLon())
                    .posCount(savedStore.getPosCount())
                    .storeNumber(savedStore.getStoreNumber())
                    .storeState(savedStore.getStoreState())
                    .memberId(memberId)
                    .build();

            System.out.println("===== StoreJpaAdapter.save() =====");
            System.out.println("Store ID: " + savedStore.getId());
            System.out.println("Member ID: " + memberId);
            System.out.println("Store Number: " + savedStore.getStoreNumber());
            System.out.println("SyncDto JSON: " + objectMapper.writeValueAsString(syncDto));

            DataSyncEvent event = DataSyncEvent.builder()
                    .operation(isUpdate ? DataSyncEvent.SyncOperation.UPDATE : DataSyncEvent.SyncOperation.CREATE)
                    .entityType(DataSyncEvent.EntityType.STORE)
                    .entityId(savedStore.getId())
                    .entityData(objectMapper.writeValueAsString(syncDto))
                    .timestamp(LocalDateTime.now())
                    .build();
            eventProducer.publishEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
            // 이벤트 발행 실패 시 로깅만 수행
        }

        return savedStore;
    }

    @Override
    public void delete(Integer id) {
        storeJpaRepository.deleteById(id);

        // CQRS 이벤트 발행
        DataSyncEvent event = DataSyncEvent.builder()
                .operation(DataSyncEvent.SyncOperation.DELETE)
                .entityType(DataSyncEvent.EntityType.STORE)
                .entityId(id)
                .timestamp(LocalDateTime.now())
                .build();
        eventProducer.publishEvent(event);
    }
}
