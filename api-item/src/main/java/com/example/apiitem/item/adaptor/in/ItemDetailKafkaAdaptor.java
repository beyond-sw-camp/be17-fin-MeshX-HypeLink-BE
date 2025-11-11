package com.example.apiitem.item.adaptor.in;

import MeshX.common.EventInAdapter;
import MeshX.common.TryCatchTemplate;
import com.example.apiitem.item.usecase.port.in.KafkaItemDetailInPort;
import com.example.apiitem.item.usecase.port.in.request.kafka.ItemDetailUpdateCommand;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@EventInAdapter
@RequiredArgsConstructor
public class ItemDetailKafkaAdaptor {
    private final String TYPE = "type";
    private final String PAYLOAD = "payload";

    private final ObjectMapper objectMapper;

    private final KafkaItemDetailInPort kafkaItemDetailInPort;

    @KafkaListener(topics = "${kafka.topic.itemDetail}")
    public void updateItemDetailStock(String rawMessage) {
        TryCatchTemplate.parse(() -> {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get(TYPE).asText();
            JsonNode payload = root.get(PAYLOAD);

            logGetKafkaMessageType(type);

            if ("ITEM_DETAIL".equals(type)) {
                ItemDetailUpdateCommand command = objectMapper.treeToValue(payload, ItemDetailUpdateCommand.class);
                kafkaItemDetailInPort.updateItemDetail(command);
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
