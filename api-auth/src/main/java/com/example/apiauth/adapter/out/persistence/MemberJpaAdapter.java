package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.external.monolith.dto.MemberSyncDto;
import com.example.apiauth.adapter.out.kafka.DataSyncEventProducer;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.mapper.MemberMapper;
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
            MemberSyncDto syncDto = MemberSyncDto.builder()
                    .id(savedMember.getId())
                    .email(savedMember.getEmail())
                    .password(savedMember.getPassword())
                    .name(savedMember.getName())
                    .phone(savedMember.getPhone())
                    .address(savedMember.getAddress())
                    .role(savedMember.getRole())
                    .region(savedMember.getRegion())
                    .refreshToken(savedMember.getRefreshToken())
                    .createdAt(savedMember.getCreatedAt())
                    .updatedAt(savedMember.getUpdatedAt())
                    .build();

            DataSyncEvent event = DataSyncEvent.builder()
                    .operation(isUpdate ? DataSyncEvent.SyncOperation.UPDATE : DataSyncEvent.SyncOperation.CREATE)
                    .entityType(DataSyncEvent.EntityType.MEMBER)
                    .entityId(savedMember.getId())
                    .entityData(objectMapper.writeValueAsString(syncDto))
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
