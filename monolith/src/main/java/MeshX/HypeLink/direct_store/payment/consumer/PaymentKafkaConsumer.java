package MeshX.HypeLink.direct_store.payment.consumer;

import MeshX.HypeLink.direct_store.payment.consumer.dto.KafkaEnvelope;
import MeshX.HypeLink.direct_store.payment.consumer.dto.PaymentSyncEvent;
import MeshX.HypeLink.direct_store.payment.service.PaymentSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentKafkaConsumer {

    private final PaymentSyncService paymentSyncService;

    @KafkaListener(
            topics = "payment.sync",
            groupId = "monolith-payment-sync",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumePaymentSync(KafkaEnvelope<PaymentSyncEvent> envelope) {
        try {
            log.info("[KAFKA] Payment 동기화 이벤트 수신 - type: {}", envelope.getType());

            if ("PAYMENT".equals(envelope.getType())) {
                PaymentSyncEvent event = envelope.getPayload();
                paymentSyncService.syncPayment(event);
                log.info("[KAFKA] Payment 동기화 완료 - storeId: {}, merchantUid: {}",
                        event.getStoreId(), event.getReceipt().getMerchantUid());
            } else {
                log.warn("[KAFKA] 알 수 없는 타입 수신 - type: {}", envelope.getType());
            }

        } catch (Exception e) {
            log.error("[KAFKA] Payment 동기화 실패 - error: {}", e.getMessage(), e);
            // 예외 발생 시 Kafka가 자동 재시도
            throw e;
        }
    }
}
