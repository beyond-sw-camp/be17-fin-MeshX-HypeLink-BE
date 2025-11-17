package MeshX.HypeLink.head_office.customer.kafka.producer;

import MeshX.HypeLink.head_office.customer.kafka.dto.CustomerListSyncEvent;
import MeshX.HypeLink.head_office.customer.kafka.dto.CustomerSyncEvent;
import MeshX.HypeLink.head_office.item.service.kafka.KafkaEnvelope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerSyncProducer {

    private static final String TOPIC = "customer.sync";

    private final KafkaTemplate<String, KafkaEnvelope> kafkaTemplate;

    /**
     * 전체 Customer 목록 동기화
     */
    public void sendBulkCustomerSync(List<CustomerSyncEvent> customers) {
        try {
            CustomerListSyncEvent listEvent = CustomerListSyncEvent.builder()
                    .customers(customers)
                    .build();

            KafkaEnvelope<CustomerListSyncEvent> envelope = KafkaEnvelope.<CustomerListSyncEvent>builder()
                    .type("CUSTOMER_BULK_SYNC")
                    .payload(listEvent)
                    .build();

            kafkaTemplate.send(TOPIC, "bulk-sync", envelope);

            log.info("[KAFKA] 전체 Customer 동기화 이벤트 발송 완료 - count: {}", customers.size());

        } catch (Exception e) {
            log.error("[KAFKA] 전체 Customer 동기화 이벤트 발송 실패 - count: {}, error: {}",
                    customers.size(), e.getMessage(), e);
        }
    }
}
