package com.example.apiauth.adapter.out.kafka;

import com.example.apiauth.domain.event.DataSyncEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSyncEventProducer {

    private static final String TOPIC = "cqrs-sync";
    private final KafkaTemplate<String, DataSyncEvent> kafkaTemplate;

    public void publishEvent(DataSyncEvent event) {
        kafkaTemplate.send(TOPIC, event.getEntityType().name(), event);
        log.info("Published CQRS sync event: operation={}, entityType={}, entityId={}",
                event.getOperation(), event.getEntityType(), event.getEntityId());
    }
}
