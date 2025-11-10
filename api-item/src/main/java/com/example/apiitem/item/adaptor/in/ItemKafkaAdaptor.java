package com.example.apiitem.item.adaptor.in;

import MeshX.common.EventInAdapter;
import com.example.apiitem.item.usecase.port.in.KafkaPort;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaCategoryList;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaColorList;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaItemCommand;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaSizeList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@EventInAdapter
@RequiredArgsConstructor
public class ItemKafkaAdaptor {
    private final ObjectMapper objectMapper;
    private final KafkaPort kafkaPort;

    @KafkaListener(topics = "items.sync.1")
    public void consumeItemList(String rawMessage) {
        try {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get("type").asText();
            JsonNode payload = root.get("payload");

            log.info("Kafka로부터 데이터 받음 : {}", type);

            if ("ITEM".equals(type)) {
                KafkaItemCommand command = objectMapper.treeToValue(payload, KafkaItemCommand.class);
                kafkaPort.save(command);
            } else {
                log.warn("⚠️ Unknown Kafka message type: {}", type);
            }
        } catch (Exception e) {
            log.error("Kafka 메시지 파싱 실패", e);
        }
    }

    @KafkaListener(topics = "size.sync.1")
    public void consumeSizeList(String rawMessage) {
        try {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get("type").asText();
            JsonNode payload = root.get("payload");

            log.info("Kafka로부터 데이터 받음 : {}", type);

            if ("SIZE".equals(type)) {
                KafkaSizeList command = objectMapper.treeToValue(payload, KafkaSizeList.class);
                kafkaPort.saveSizes(command);
            } else {
                log.warn("⚠️ Unknown Kafka message type: {}", type);
            }
        } catch (Exception e) {
            log.error("❌ Kafka 메시지 파싱 실패", e);
        }
    }

    @KafkaListener(topics = "category.sync.1")
    public void consumeCategory(String rawMessage) {
        try {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get("type").asText();
            JsonNode payload = root.get("payload");

            log.info("Kafka로부터 데이터 받음 : {}", type);

            if ("CATEGORY".equals(type)) {
                KafkaCategoryList command = objectMapper.treeToValue(payload, KafkaCategoryList.class);
                kafkaPort.saveCategories(command);
            } else {
                log.warn("⚠️ Unknown Kafka message type: {}", type);
            }
        } catch (Exception e) {
            log.error("❌ Kafka 메시지 파싱 실패", e);
        }
    }

    @KafkaListener(topics = "color.sync.1")
    public void consumeColor(String rawMessage) {
        try {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get("type").asText();
            JsonNode payload = root.get("payload");

            log.info("Kafka로부터 데이터 받음 : {}", type);

            if ("COLOR".equals(type)) {
                KafkaColorList command = objectMapper.treeToValue(payload, KafkaColorList.class);
                kafkaPort.saveColor(command);
            } else {
                log.warn("⚠️ Unknown Kafka message type: {}", type);
            }
        } catch (Exception e) {
            log.error("❌ Kafka 메시지 파싱 실패", e);
        }
    }
}
