package com.example.apidirect.customer.adapter.out.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.apidirect.item.adapter.in.kafka.dto.KafkaEnvelope;
import com.example.apidirect.customer.adapter.out.kafka.dto.CustomerSyncEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerSyncProducer {

    private static final String TOPIC = "customer.sync";

    private final KafkaTemplate<String, KafkaEnvelope> kafkaTemplate;

    public void sendCustomerSync(CustomerSyncEvent event) {
        try {
            String key = "customer-" + event.getId();

            KafkaEnvelope<CustomerSyncEvent> envelope = KafkaEnvelope.<CustomerSyncEvent>builder()
                    .type("CUSTOMER_CREATE")
                    .payload(event)
                    .build();

            kafkaTemplate.send(TOPIC, key, envelope);

            log.info("[KAFKA] 신규 Customer 동기화 이벤트 발송 완료 - customerId: {}, phone: {}",
                    event.getId(), event.getPhone());

        } catch (Exception e) {
            log.error("[KAFKA] Customer 동기화 이벤트 발송 실패 - customerId: {}, error: {}",
                    event.getId(), e.getMessage(), e);
        }
    }
}
