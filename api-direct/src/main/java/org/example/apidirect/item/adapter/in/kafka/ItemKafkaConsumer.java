package org.example.apidirect.item.adapter.in.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.item.usecase.port.in.KafkaPort;
import org.example.apidirect.item.usecase.port.in.request.kafka.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemKafkaConsumer {

    private static final String TYPE = "type";
    private static final String PAYLOAD = "payload";

    private final ObjectMapper objectMapper;
    private final KafkaPort kafkaPort;

    @KafkaListener(topics = "items.sync.1", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "stringKafkaListenerFactory")
    public void consumeItem(String rawMessage) {
        try {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get(TYPE).asText();
            JsonNode payload = root.get(PAYLOAD);

            log.info("[KAFKA] Item 동기화 메시지 수신 - type: {}", type);

            if ("ITEM".equals(type)) {
                KafkaItemCommand command = objectMapper.treeToValue(payload, KafkaItemCommand.class);
                kafkaPort.saveItem(command);
            } else {
                log.warn("[KAFKA] Unknown type: {}", type);
            }

        } catch (Exception e) {
            log.error("[KAFKA] Item sync failed: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "category.sync.1", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCategory(String rawMessage) {
        try {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get(TYPE).asText();
            JsonNode payload = root.get(PAYLOAD);

            log.info("[KAFKA] Category 동기화 메시지 수신 - type: {}", type);

            if ("CATEGORY".equals(type)) {
                KafkaCategoryListCommand command = objectMapper.treeToValue(payload, KafkaCategoryListCommand.class);
                kafkaPort.saveCategories(command);
            } else {
                log.warn("[KAFKA] Unknown type: {}", type);
            }

        } catch (Exception e) {
            log.error("[KAFKA] Category sync failed: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "color.sync.1", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeColor(String rawMessage) {
        try {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get(TYPE).asText();
            JsonNode payload = root.get(PAYLOAD);

            log.info("[KAFKA] Color 동기화 메시지 수신 - type: {}", type);

            if ("COLOR".equals(type)) {
                KafkaColorListCommand command = objectMapper.treeToValue(payload, KafkaColorListCommand.class);
                kafkaPort.saveColors(command);
            } else {
                log.warn("[KAFKA] Unknown type: {}", type);
            }

        } catch (Exception e) {
            log.error("[KAFKA] Color sync failed: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "size.sync.1", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeSize(String rawMessage) {
        try {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get(TYPE).asText();
            JsonNode payload = root.get(PAYLOAD);

            log.info("[KAFKA] Size 동기화 메시지 수신 - type: {}", type);

            if ("SIZE".equals(type)) {
                KafkaSizeListCommand command = objectMapper.treeToValue(payload, KafkaSizeListCommand.class);
                kafkaPort.saveSizes(command);
            } else {
                log.warn("[KAFKA] Unknown type: {}", type);
            }

        } catch (Exception e) {
            log.error("[KAFKA] Size sync failed: {}", e.getMessage(), e);
        }
    }
}
