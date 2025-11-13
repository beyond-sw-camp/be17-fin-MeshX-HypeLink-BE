package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.external.monolith.dto.DriverSyncDto;
import com.example.apiauth.adapter.out.kafka.DataSyncEventProducer;
import com.example.apiauth.adapter.out.persistence.entity.DriverEntity;
import com.example.apiauth.adapter.out.persistence.mapper.DriverMapper;
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
            DriverSyncDto syncDto = DriverSyncDto.builder()
                    .id(savedDriver.getId())
                    .macAddress(savedDriver.getMacAddress())
                    .carNumber(savedDriver.getCarNumber())
                    .memberId(savedDriver.getMember() != null ? savedDriver.getMember().getId() : null)
                    .build();

            DataSyncEvent event = DataSyncEvent.builder()
                    .operation(isUpdate ? DataSyncEvent.SyncOperation.UPDATE : DataSyncEvent.SyncOperation.CREATE)
                    .entityType(DataSyncEvent.EntityType.DRIVER)
                    .entityId(savedDriver.getId())
                    .entityData(objectMapper.writeValueAsString(syncDto))
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
