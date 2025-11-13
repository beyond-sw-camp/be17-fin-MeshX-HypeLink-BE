package MeshX.HypeLink.direct_store.payment.consumer;

import MeshX.HypeLink.direct_store.payment.consumer.dto.PaymentSyncEvent;
import MeshX.HypeLink.direct_store.payment.service.PaymentSyncService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentKafkaConsumer {

    private final PaymentSyncService paymentSyncService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "payment.sync",
            groupId = "monolith-payment-sync",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumePaymentSync(String rawMessage) {
        try {
            log.info("[KAFKA] Payment 동기화 메시지 수신 - rawMessage: {}", rawMessage);

            // JSON 파싱
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get("type").asText();
            JsonNode payload = root.get("payload");

            log.info("[KAFKA] Payment 동기화 이벤트 수신 - type: {}", type);

            if ("PAYMENT".equals(type)) {
                PaymentSyncEvent event = objectMapper.treeToValue(payload, PaymentSyncEvent.class);
                paymentSyncService.syncPayment(event);
                log.info("[KAFKA] Payment 동기화 완료 - storeId: {}, merchantUid: {}",
                        event.getStoreId(), event.getReceipt().getMerchantUid());
            } else {
                log.warn("[KAFKA] 알 수 없는 타입 수신 - type: {}", type);
            }

        } catch (Exception e) {
            log.error("[KAFKA] Payment 동기화 실패 - error: {}", e.getMessage(), e);
        }
    }
}
