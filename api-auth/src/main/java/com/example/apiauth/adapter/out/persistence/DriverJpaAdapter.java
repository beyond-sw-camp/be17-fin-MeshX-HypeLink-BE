package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.kafka.DataSyncEventProducer;
import com.example.apiauth.adapter.out.persistence.entity.DriverEntity;
import com.example.apiauth.adapter.out.persistence.mapper.DriverMapper;
import com.example.apiauth.adapter.out.persistence.read.entity.DriverReadEntity;
import com.example.apiauth.adapter.out.persistence.read.mapper.DriverReadMapper;
import com.example.apiauth.domain.event.DataSyncEvent;
import com.example.apiauth.domain.model.Driver;
import com.example.apiauth.usecase.port.out.persistence.DriverCommandPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@PersistenceAdapter
@RequiredArgsConstructor
public class DriverJpaAdapter implements DriverCommandPort {

    private final DriverJpaRepository driverJpaRepository;
    private final DataSyncEventProducer eventProducer;
    private final ObjectMapper objectMapper;

    @Override
    public Driver save(Driver driver) {
        boolean isUpdate = driver.getId() != null;
        DriverEntity driverEntity = driverJpaRepository.save(DriverMapper.toEntity(driver));
        Driver savedDriver = DriverMapper.toDomain(driverEntity);

        // CQRS 이벤트 발행
        try {
            DriverReadEntity readEntity = DriverReadMapper.toEntity(savedDriver);
            DataSyncEvent event = DataSyncEvent.builder()
                    .operation(isUpdate ? DataSyncEvent.SyncOperation.UPDATE : DataSyncEvent.SyncOperation.CREATE)
                    .entityType(DataSyncEvent.EntityType.DRIVER)
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
        DataSyncEvent event = DataSyncEvent.builder()
                .operation(DataSyncEvent.SyncOperation.DELETE)
                .entityType(DataSyncEvent.EntityType.DRIVER)
                .entityId(id)
                .timestamp(LocalDateTime.now())
                .build();
        eventProducer.publishEvent(event);
    }
}
