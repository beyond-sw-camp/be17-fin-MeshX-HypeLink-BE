package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.kafka.DataSyncEventProducer;
import com.example.apiauth.adapter.out.persistence.entity.PosEntity;
import com.example.apiauth.adapter.out.persistence.mapper.PosMapper;
import com.example.apiauth.adapter.out.persistence.read.entity.PosReadEntity;
import com.example.apiauth.adapter.out.persistence.read.mapper.PosReadMapper;
import com.example.apiauth.domain.event.DataSyncEvent;
import com.example.apiauth.domain.model.Pos;
import com.example.apiauth.usecase.port.out.persistence.PosCommandPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@PersistenceAdapter
@RequiredArgsConstructor
public class PosJpaAdapter implements PosCommandPort {

    private final PosJpaRepository posJpaRepository;
    private final DataSyncEventProducer eventProducer;
    private final ObjectMapper objectMapper;

    @Override
    public Pos save(Pos pos) {
        boolean isUpdate = pos.getId() != null;
        PosEntity posEntity = posJpaRepository.save(PosMapper.toEntity(pos));
        Pos savedPos = PosMapper.toDomain(posEntity);

        // CQRS 이벤트 발행
        try {
            PosReadEntity readEntity = PosReadMapper.toEntity(savedPos);
            DataSyncEvent event = DataSyncEvent.builder()
                    .operation(isUpdate ? DataSyncEvent.SyncOperation.UPDATE : DataSyncEvent.SyncOperation.CREATE)
                    .entityType(DataSyncEvent.EntityType.POS)
                    .entityId(savedPos.getId())
                    .entityData(objectMapper.writeValueAsString(readEntity))
                    .timestamp(LocalDateTime.now())
                    .build();
            eventProducer.publishEvent(event);
        } catch (Exception e) {
            // 이벤트 발행 실패 시 로깅만 수행
        }

        return savedPos;
    }

    @Override
    public void delete(Integer id) {
        posJpaRepository.deleteById(id);

        // CQRS 이벤트 발행
        DataSyncEvent event = DataSyncEvent.builder()
                .operation(DataSyncEvent.SyncOperation.DELETE)
                .entityType(DataSyncEvent.EntityType.POS)
                .entityId(id)
                .timestamp(LocalDateTime.now())
                .build();
        eventProducer.publishEvent(event);
    }
}