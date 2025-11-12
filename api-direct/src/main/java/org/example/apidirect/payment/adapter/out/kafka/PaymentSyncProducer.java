package org.example.apidirect.payment.adapter.out.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaEnvelope;
import org.example.apidirect.payment.adapter.out.kafka.dto.PaymentSyncEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentSyncProducer {

    private static final String TOPIC = "payment.sync";

    private final KafkaTemplate<String, KafkaEnvelope> kafkaTemplate;

    public void sendPaymentSync(PaymentSyncEvent event) {
        try {
            String key = event.getStoreId() + "-" + event.getReceipt().getMerchantUid();

            KafkaEnvelope<PaymentSyncEvent> envelope = KafkaEnvelope.<PaymentSyncEvent>builder()
                    .type("PAYMENT")
                    .payload(event)
                    .build();

            kafkaTemplate.send(TOPIC, key, envelope);

        } catch (Exception e) {
            log.error("[KAFKA] 결제 동기화 이벤트 발송 실패 - storeId: {}, error: {}",
                    event.getStoreId(), e.getMessage(), e);
            // Kafka 전송 실패해도 예외 던지지 않음 (가맹점 결제는 이미 완료)
            // 나중에 배치로 재동기화 가능
        }
    }
}
