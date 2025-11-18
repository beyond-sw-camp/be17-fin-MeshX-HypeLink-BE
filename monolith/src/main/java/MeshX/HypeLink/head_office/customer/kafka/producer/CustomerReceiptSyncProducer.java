package MeshX.HypeLink.head_office.customer.kafka.producer;

import MeshX.HypeLink.head_office.customer.kafka.dto.CustomerReceiptListSyncEvent;
import MeshX.HypeLink.head_office.customer.kafka.dto.CustomerReceiptSyncEvent;
import MeshX.HypeLink.head_office.item.service.kafka.KafkaEnvelope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerReceiptSyncProducer {

    private static final String TOPIC = "customer-receipt.sync";

    private final KafkaTemplate<String, KafkaEnvelope> kafkaTemplate;

    /**
     * 전체 영수증 대량 동기화 (최초 1회)
     */
    public void sendBulkReceiptSync(List<CustomerReceiptSyncEvent> receipts) {
        try {
            CustomerReceiptListSyncEvent listEvent = CustomerReceiptListSyncEvent.builder()
                    .receipts(receipts)
                    .build();

            KafkaEnvelope<CustomerReceiptListSyncEvent> envelope = KafkaEnvelope.<CustomerReceiptListSyncEvent>builder()
                    .type("CUSTOMER_RECEIPT_BULK_SYNC")
                    .payload(listEvent)
                    .build();

            kafkaTemplate.send(TOPIC, "bulk-sync", envelope);

            log.info("[KAFKA] 영수증 대량 동기화 이벤트 발송 성공 - count: {}", receipts.size());

        } catch (Exception e) {
            log.error("[KAFKA] 영수증 대량 동기화 이벤트 발송 실패 - error: {}", e.getMessage(), e);
        }
    }

    /**
     * 단일 영수증 동기화 이벤트 발송
     */
    public void sendReceiptSync(CustomerReceiptSyncEvent event) {
        try {
            String key = event.getStoreId() + "-" + event.getMerchantUid();

            KafkaEnvelope<CustomerReceiptSyncEvent> envelope = KafkaEnvelope.<CustomerReceiptSyncEvent>builder()
                    .type("CUSTOMER_RECEIPT")
                    .payload(event)
                    .build();

            kafkaTemplate.send(TOPIC, key, envelope);

            log.info("[KAFKA] 영수증 동기화 이벤트 발송 성공 - id: {}, merchantUid: {}",
                    event.getId(), event.getMerchantUid());

        } catch (Exception e) {
            log.error("[KAFKA] 영수증 동기화 이벤트 발송 실패 - id: {}, error: {}",
                    event.getId(), e.getMessage(), e);
        }
    }
}
