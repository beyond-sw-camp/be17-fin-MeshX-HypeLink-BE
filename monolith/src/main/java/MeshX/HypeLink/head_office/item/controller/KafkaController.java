package MeshX.HypeLink.head_office.item.controller;

import MeshX.HypeLink.common.TryCatchTemplate;
import MeshX.HypeLink.head_office.item.model.dto.request.SaveItemDetailsReq;
import MeshX.HypeLink.head_office.item.model.dto.request.SaveItemReq;
import MeshX.HypeLink.head_office.item.service.ItemDetailService;
import MeshX.HypeLink.head_office.item.service.ItemService;
import MeshX.HypeLink.head_office.item.service.kafka.KafkaEnvelope;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaController {
    private final String TYPE = "type";
    private final String PAYLOAD = "payload";
    @Value("${kafka.topic.transaction.item.rollback}")
    private String itemRollbackTopic;
    @Value("${kafka.topic.transaction.itemDetail.rollback}")
    private String itemDetailRollbackTopic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, KafkaEnvelope> kafkaTemplate;

    private final ItemService itemService;
    private final ItemDetailService itemDetailService;

    @KafkaListener(topics = "${kafka.topic.transaction.item.save}",
            containerFactory = "stringKafkaListenerFactory")
    public void saveNewItem(String rawMessage) {
        TryCatchTemplate.parse(() -> {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get(TYPE).asText();
            JsonNode payload = root.get(PAYLOAD);

            logGetKafkaMessageType(type);

            if ("ITEM".equals(type)) {
                SaveItemReq dto = objectMapper.treeToValue(payload, SaveItemReq.class);
                saveTransactionItem(dto);
            } else {
                logErrorMatchTypeDto(type);
            }
        }, e -> log.error("Kafka 메시지 파싱 실패", e));
    }

    private void saveTransactionItem(SaveItemReq dto) {
        TryCatchTemplate.parse(() -> {
            itemService.saveItem(dto);
        }, e -> {
            log.error("Kafka 메시지 파싱 실패", e);
            KafkaEnvelope<Integer> envelope = KafkaEnvelope.<Integer>builder()
                    .type("ITEM")
                    .payload(dto.getId())
                    .build();
            kafkaTemplate.send(itemRollbackTopic, envelope);
        });
    }

    @KafkaListener(topics = "${kafka.topic.transaction.itemDetail.save}",
            containerFactory = "stringKafkaListenerFactory")
    public void saveNewItemDetails(String rawMessage) {
        TryCatchTemplate.parse(() -> {
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get(TYPE).asText();
            JsonNode payload = root.get(PAYLOAD);

            logGetKafkaMessageType(type);

            if ("ITEM_DETAIL".equals(type)) {
                SaveItemDetailsReq dto = objectMapper.treeToValue(payload, SaveItemDetailsReq.class);
                saveTransactionItemDetails(dto);
            } else {
                logErrorMatchTypeDto(type);
            }
        }, e -> log.error("Kafka 메시지 파싱 실패", e));
    }

    private void saveTransactionItemDetails(SaveItemDetailsReq dto) {
        TryCatchTemplate.parse(() -> {
            itemDetailService.saveItemDetails(dto);
        }, e -> {
            log.error("Kafka 메시지 파싱 실패", e);
            KafkaEnvelope<SaveItemDetailsReq> envelope = KafkaEnvelope.<SaveItemDetailsReq>builder()
                    .type("ITEM_DETAIL")
                    .payload(dto)
                    .build();
            kafkaTemplate.send(itemDetailRollbackTopic, envelope);
        });
    }

    private void logGetKafkaMessageType(String type) {
        log.info("Kafka로부터 데이터 받음 : {}", type);
    }

    private void logErrorMatchTypeDto(String type) {
        log.warn("Unknown Kafka message type: {}", type);
    }
}
