package com.example.apiauth.adapter.out.kafka;

import com.example.apiauth.domain.event.CqrsSyncEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CqrsSyncEventProducer {

    private static final String TOPIC = "cqrs-sync";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publishEvent(CqrsSyncEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, event.getEntityType().name(), eventJson);
            log.info("Published CQRS sync event: operation={}, entityType={}, entityId={}",
                    event.getOperation(), event.getEntityType(), event.getEntityId());
        } catch (JsonProcessingException e) {
            log.error("Failed to publish CQRS sync event", e);
        }
    }
}
