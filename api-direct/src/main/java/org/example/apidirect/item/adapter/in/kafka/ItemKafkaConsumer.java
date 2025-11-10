package org.example.apidirect.item.adapter.in.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.item.adapter.in.kafka.dto.*;
import org.example.apidirect.item.usecase.port.in.KafkaPort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemKafkaConsumer {

    private final KafkaPort kafkaPort;

    @KafkaListener(topics = "items.sync.1", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeItem(KafkaEnvelope<KafkaItemDto> envelope) {
        try {
            if (!"ITEM".equals(envelope.getType())) {
                return;
            }

            KafkaItemDto payload = envelope.getPayload();
            kafkaPort.saveItem(payload);

        } catch (Exception e) {
            log.error("[KAFKA] Item sync failed: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "category.sync.1", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCategory(KafkaEnvelope<KafkaCategoryList> envelope) {
        try {
            if (!"CATEGORY".equals(envelope.getType())) {
                return;
            }

            KafkaCategoryList payload = envelope.getPayload();
            kafkaPort.saveCategories(payload);

        } catch (Exception e) {
            log.error("[KAFKA] Category sync failed: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "color.sync.1", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeColor(KafkaEnvelope<KafkaColorList> envelope) {
        try {
            if (!"COLOR".equals(envelope.getType())) {
                return;
            }

            KafkaColorList payload = envelope.getPayload();
            kafkaPort.saveColors(payload);

        } catch (Exception e) {
            log.error("[KAFKA] Color sync failed: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "size.sync.1", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeSize(KafkaEnvelope<KafkaSizeList> envelope) {
        try {
            if (!"SIZE".equals(envelope.getType())) {
                return;
            }

            KafkaSizeList payload = envelope.getPayload();
            kafkaPort.saveSizes(payload);

        } catch (Exception e) {
            log.error("[KAFKA] Size sync failed: {}", e.getMessage(), e);
        }
    }
}
