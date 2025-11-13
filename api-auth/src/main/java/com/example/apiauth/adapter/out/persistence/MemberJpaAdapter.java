package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.kafka.DataSyncEventProducer;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.mapper.MemberMapper;
import com.example.apiauth.adapter.out.persistence.read.entity.MemberReadEntity;
import com.example.apiauth.adapter.out.persistence.read.mapper.MemberReadMapper;
import com.example.apiauth.domain.event.DataSyncEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.usecase.port.out.persistence.MemberCommandPort;

import java.time.LocalDateTime;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberJpaAdapter implements MemberCommandPort {

    private final MemberJpaRepository memberJpaRepository;
    private final DataSyncEventProducer eventProducer;
    private final ObjectMapper objectMapper;

    @Override
    public Member save(Member member) {
        boolean isUpdate = member.getId() != null;
        MemberEntity memberEntity = memberJpaRepository.save(MemberMapper.toEntity(member));
        Member savedMember = MemberMapper.toDomain(memberEntity);

        // CQRS 이벤트 발행
        try {
            MemberReadEntity readEntity = MemberReadMapper.toEntity(savedMember);
            DataSyncEvent event = DataSyncEvent.builder()
                    .operation(isUpdate ? DataSyncEvent.SyncOperation.UPDATE : DataSyncEvent.SyncOperation.CREATE)
                    .entityType(DataSyncEvent.EntityType.MEMBER)
                    .entityId(savedMember.getId())
                    .entityData(objectMapper.writeValueAsString(readEntity))
                    .timestamp(LocalDateTime.now())
                    .build();
            eventProducer.publishEvent(event);
        } catch (Exception e) {
            // 이벤트 발행 실패 시 로깅만 수행 (Write DB는 이미 저장됨)
        }

        return savedMember;
    }

    @Override
    public void delete(Integer id) {
        memberJpaRepository.deleteById(id);

        // CQRS 이벤트 발행
        DataSyncEvent event = DataSyncEvent.builder()
                .operation(DataSyncEvent.SyncOperation.DELETE)
                .entityType(DataSyncEvent.EntityType.MEMBER)
                .entityId(id)
                .timestamp(LocalDateTime.now())
                .build();
        eventProducer.publishEvent(event);
    }
}
