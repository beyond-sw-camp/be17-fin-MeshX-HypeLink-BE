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
public class SagaPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String SYNC_SUCCESS_TOPIC = "sync-success";
    private static final String SYNC_FAILED_TOPIC = "sync-failed";

    public void syncSuccess(SagaSuccessEvent event) {
        kafkaTemplate.send(SYNC_SUCCESS_TOPIC, event.getEntityId().toString(), event);
    }

    public void syncFailed(SagaFailedEvent event) {
        kafkaTemplate.send(SYNC_FAILED_TOPIC, event.getEntityId().toString(), event);

    }
}
