package MeshX.HypeLink.common.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSyncEventProducer {

    private static final String TOPIC = "cqrs-sync";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 모놀리식에서 api-auth로 동기화 이벤트 발행
     * @param event CQRS 동기화 이벤트
     */
    public void publishEvent(DataSyncEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, event.getEntityType().name(), eventJson);
            log.info("Published CQRS sync event to MSA: operation={}, entityType={}, entityId={}",
                    event.getOperation(), event.getEntityType(), event.getEntityId());
        } catch (JsonProcessingException e) {
            log.error("Failed to publish CQRS sync event", e);
            throw new RuntimeException("Failed to publish CQRS sync event", e);
        }
    }
}
