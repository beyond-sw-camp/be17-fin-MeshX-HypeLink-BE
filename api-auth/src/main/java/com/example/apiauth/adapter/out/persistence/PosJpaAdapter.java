package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.kafka.CqrsSyncEventProducer;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.entity.PosEntity;
import com.example.apiauth.adapter.out.persistence.mapper.PosMapper;
import com.example.apiauth.adapter.out.persistence.read.entity.PosReadEntity;
import com.example.apiauth.adapter.out.persistence.read.mapper.PosReadMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.common.exception.AuthExceptionMessage;
import com.example.apiauth.domain.event.CqrsSyncEvent;
import com.example.apiauth.domain.model.Pos;
import com.example.apiauth.usecase.port.out.persistence.PosCommandPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class PosJpaAdapter implements PosCommandPort {

    private final PosJpaRepository posJpaRepository;
    private final CqrsSyncEventProducer eventProducer;
    private final ObjectMapper objectMapper;

    @Override
    public Pos save(Pos pos) {
        boolean isUpdate = pos.getId() != null;
        PosEntity posEntity = posJpaRepository.save(PosMapper.toEntity(pos));
        Pos savedPos = PosMapper.toDomain(posEntity);

        // CQRS 이벤트 발행
        try {
            PosReadEntity readEntity = PosReadMapper.toEntity(savedPos);
            CqrsSyncEvent event = CqrsSyncEvent.builder()
                    .operation(isUpdate ? CqrsSyncEvent.SyncOperation.UPDATE : CqrsSyncEvent.SyncOperation.CREATE)
                    .entityType(CqrsSyncEvent.EntityType.POS)
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
        CqrsSyncEvent event = CqrsSyncEvent.builder()
                .operation(CqrsSyncEvent.SyncOperation.DELETE)
                .entityType(CqrsSyncEvent.EntityType.POS)
                .entityId(id)
                .timestamp(LocalDateTime.now())
                .build();
        eventProducer.publishEvent(event);
    }
}