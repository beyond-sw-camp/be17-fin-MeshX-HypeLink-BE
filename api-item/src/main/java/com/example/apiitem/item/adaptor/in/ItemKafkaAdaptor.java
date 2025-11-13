package com.example.apiitem.item.adaptor.in;

import MeshX.common.EventInAdapter;
import MeshX.common.TryCatchTemplate;
import com.example.apiitem.item.usecase.port.in.KafkaItemInPort;
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
    private final String TYPE = "type";
    private final String PAYLOAD = "payload";

    private final ObjectMapper objectMapper;
    private final KafkaItemInPort kafkaPort;

    @KafkaListener(topics = "${kafka.topic.item}")
    public void consumeItemList(String rawMessage) {
        TryCatchTemplate.parse(() -> {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get(TYPE).asText();
            JsonNode payload = root.get(PAYLOAD);

            logGetKafkaMessageType(type);

            if ("ITEM".equals(type)) {
                KafkaItemCommand command = objectMapper.treeToValue(payload, KafkaItemCommand.class);
                kafkaPort.save(command);
            } else {
                logErrorMatchTypeDto(type);
            }
        }, e -> log.error("Kafka 메시지 파싱 실패", e));
    }

    @KafkaListener(topics = "${kafka.topic.size}")
    public void consumeSizeList(String rawMessage) {
        TryCatchTemplate.parse(() -> {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get(TYPE).asText();
            JsonNode payload = root.get(PAYLOAD);

            logGetKafkaMessageType(type);

            if ("SIZE".equals(type)) {
                KafkaSizeList command = objectMapper.treeToValue(payload, KafkaSizeList.class);
                kafkaPort.saveSizes(command);
            } else {
                logErrorMatchTypeDto(type);
            }
        }, e -> log.error("Kafka 메시지 파싱 실패", e));
    }

    @KafkaListener(topics = "${kafka.topic.category}")
    public void consumeCategory(String rawMessage) {
        TryCatchTemplate.parse(() -> {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get(TYPE).asText();
            JsonNode payload = root.get(PAYLOAD);

            logGetKafkaMessageType(type);

            if ("CATEGORY".equals(type)) {
                KafkaCategoryList command = objectMapper.treeToValue(payload, KafkaCategoryList.class);
                kafkaPort.saveCategories(command);
            } else {
                logErrorMatchTypeDto(type);
            }
        }, e -> log.error("Kafka 메시지 파싱 실패", e));
    }

    @KafkaListener(topics = "${kafka.topic.color}")
    public void consumeColor(String rawMessage) {
        TryCatchTemplate.parse(() -> {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get(TYPE).asText();
            JsonNode payload = root.get(PAYLOAD);

            logGetKafkaMessageType(type);

            if ("COLOR".equals(type)) {
                KafkaColorList command = objectMapper.treeToValue(payload, KafkaColorList.class);
                kafkaPort.saveColor(command);
            } else {
                logErrorMatchTypeDto(type);
            }
        }, e -> log.error("Kafka 메시지 파싱 실패", e));
    }

    @KafkaListener(topics = "${kafka.topic.transaction.item.rollback}")
    public void rollback(String rawMessage) {
        TryCatchTemplate.parse(() -> {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get(TYPE).asText();
            JsonNode payload = root.get(PAYLOAD);

            logGetKafkaMessageType(type);

            if ("ITEM".equals(type)) {
                Integer itemId = objectMapper.treeToValue(payload, Integer.class);
                kafkaPort.rollback(itemId);
            } else {
                logErrorMatchTypeDto(type);
            }
        }, e -> log.error("Kafka 메시지 파싱 실패", e));
    }

    private void logGetKafkaMessageType(String type) {
        log.info("Kafka로부터 데이터 받음 : {}", type);
    }

    private void logErrorMatchTypeDto(String type) {
        log.warn("Unknown Kafka message type: {}", type);
    }
}
