package com.example.apiauth.adapter.out.kafka;

import com.example.apiauth.domain.event.DataSyncEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonolithSyncEventProducer {

    private static final String TOPIC = "monolith-sync";
    private final KafkaTemplate<String, DataSyncEvent> kafkaTemplate;

    /**
     * api-auth에서 모놀리식으로 동기화 이벤트 발행
     * @param event CQRS 동기화 이벤트
     */
    public void publishToMonolith(DataSyncEvent event) {
        kafkaTemplate.send(TOPIC, event.getEntityType().name(), event);
        log.info("Published to monolith: operation={}, entityType={}, entityId={}",
                event.getOperation(), event.getEntityType(), event.getEntityId());
    }
}
