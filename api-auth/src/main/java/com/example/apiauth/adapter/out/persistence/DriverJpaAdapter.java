package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.kafka.CqrsSyncEventProducer;
import com.example.apiauth.adapter.out.persistence.entity.DriverEntity;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.mapper.DriverMapper;
import com.example.apiauth.adapter.out.persistence.mapper.MemberMapper;
import com.example.apiauth.adapter.out.persistence.read.entity.DriverReadEntity;
import com.example.apiauth.adapter.out.persistence.read.mapper.DriverReadMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.common.exception.AuthExceptionMessage;
import com.example.apiauth.domain.event.CqrsSyncEvent;
import com.example.apiauth.domain.model.Driver;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.usecase.port.out.persistence.DriverCommandPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class DriverJpaAdapter implements DriverCommandPort {

    private final DriverJpaRepository driverJpaRepository;
    private final CqrsSyncEventProducer eventProducer;
    private final ObjectMapper objectMapper;

    @Override
    public Driver save(Driver driver) {
        boolean isUpdate = driver.getId() != null;
        DriverEntity driverEntity = driverJpaRepository.save(DriverMapper.toEntity(driver));
        Driver savedDriver = DriverMapper.toDomain(driverEntity);

        // CQRS 이벤트 발행
        try {
            DriverReadEntity readEntity = DriverReadMapper.toEntity(savedDriver);
            CqrsSyncEvent event = CqrsSyncEvent.builder()
                    .operation(isUpdate ? CqrsSyncEvent.SyncOperation.UPDATE : CqrsSyncEvent.SyncOperation.CREATE)
                    .entityType(CqrsSyncEvent.EntityType.DRIVER)
                    .entityId(savedDriver.getId())
                    .entityData(objectMapper.writeValueAsString(readEntity))
                    .timestamp(LocalDateTime.now())
                    .build();
            eventProducer.publishEvent(event);
        } catch (Exception e) {
            // 이벤트 발행 실패 시 로깅만 수행
        }

        return savedDriver;
    }

    @Override
    public void delete(Integer id) {
        driverJpaRepository.deleteById(id);

        // CQRS 이벤트 발행
        CqrsSyncEvent event = CqrsSyncEvent.builder()
                .operation(CqrsSyncEvent.SyncOperation.DELETE)
                .entityType(CqrsSyncEvent.EntityType.DRIVER)
                .entityId(id)
                .timestamp(LocalDateTime.now())
                .build();
        eventProducer.publishEvent(event);
    }
}
