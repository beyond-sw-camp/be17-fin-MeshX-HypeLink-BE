package org.example.apidirect.item.adapter.in.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaEnvelope;
import org.example.apidirect.item.usecase.port.in.KafkaPort;
import org.example.apidirect.item.usecase.port.in.request.kafka.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemKafkaConsumer {

    private final KafkaPort kafkaPort;

    @KafkaListener(topics = "items.sync.1", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeItem(KafkaEnvelope<KafkaItemCommand> envelope) {
        try {
            if (!"ITEM".equals(envelope.getType())) {
                return;
            }

            KafkaItemCommand command = envelope.getPayload();
            kafkaPort.saveItem(command);

        } catch (Exception e) {
            log.error("[KAFKA] Item sync failed: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "category.sync.1", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCategory(KafkaEnvelope<KafkaCategoryListCommand> envelope) {
        try {
            if (!"CATEGORY".equals(envelope.getType())) {
                return;
            }

            KafkaCategoryListCommand command = envelope.getPayload();
            kafkaPort.saveCategories(command);

        } catch (Exception e) {
            log.error("[KAFKA] Category sync failed: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "color.sync.1", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeColor(KafkaEnvelope<KafkaColorListCommand> envelope) {
        try {
            if (!"COLOR".equals(envelope.getType())) {
                return;
            }

            KafkaColorListCommand command = envelope.getPayload();
            kafkaPort.saveColors(command);

        } catch (Exception e) {
            log.error("[KAFKA] Color sync failed: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "size.sync.1", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeSize(KafkaEnvelope<KafkaSizeListCommand> envelope) {
        try {
            if (!"SIZE".equals(envelope.getType())) {
                return;
            }

            KafkaSizeListCommand command = envelope.getPayload();
            kafkaPort.saveSizes(command);

        } catch (Exception e) {
            log.error("[KAFKA] Size sync failed: {}", e.getMessage(), e);
        }
    }
}
