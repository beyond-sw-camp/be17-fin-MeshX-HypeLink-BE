package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.kafka.CqrsSyncEventProducer;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.mapper.MemberMapper;
import com.example.apiauth.adapter.out.persistence.read.entity.MemberReadEntity;
import com.example.apiauth.adapter.out.persistence.read.mapper.MemberReadMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.domain.event.CqrsSyncEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.usecase.port.out.persistence.MemberCommandPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.apiauth.common.exception.AuthExceptionMessage.USER_NAME_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberJpaAdapter implements MemberCommandPort {

    private final MemberJpaRepository memberJpaRepository;
    private final CqrsSyncEventProducer eventProducer;
    private final ObjectMapper objectMapper;

    @Override
    public Member save(Member member) {
        boolean isUpdate = member.getId() != null;
        MemberEntity memberEntity = memberJpaRepository.save(MemberMapper.toEntity(member));
        Member savedMember = MemberMapper.toDomain(memberEntity);

        // CQRS 이벤트 발행
        try {
            MemberReadEntity readEntity = MemberReadMapper.toEntity(savedMember);
            CqrsSyncEvent event = CqrsSyncEvent.builder()
                    .operation(isUpdate ? CqrsSyncEvent.SyncOperation.UPDATE : CqrsSyncEvent.SyncOperation.CREATE)
                    .entityType(CqrsSyncEvent.EntityType.MEMBER)
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
        CqrsSyncEvent event = CqrsSyncEvent.builder()
                .operation(CqrsSyncEvent.SyncOperation.DELETE)
                .entityType(CqrsSyncEvent.EntityType.MEMBER)
                .entityId(id)
                .timestamp(LocalDateTime.now())
                .build();
        eventProducer.publishEvent(event);
    }
}
