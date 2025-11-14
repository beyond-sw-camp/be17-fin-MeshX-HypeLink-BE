package com.example.apidirect.customer.adapter.in.kafka;

import com.example.apidirect.customer.adapter.out.kafka.dto.CustomerListSyncEvent;
import com.example.apidirect.customer.adapter.out.kafka.dto.CustomerSyncEvent;
import com.example.apidirect.customer.domain.Customer;
import com.example.apidirect.customer.usecase.port.out.CustomerPersistencePort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerKafkaConsumer {

    private final CustomerPersistencePort customerPersistencePort;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "customer.sync",
            groupId = "api-direct-customer-sync",
            containerFactory = "stringKafkaListenerFactory"
    )
    public void consumeCustomerSync(String rawMessage) {
        try {
            log.info("[KAFKA] Customer 동기화 메시지 수신 - 완료~~ 본사 > 가맹점으로~");

            // JSON 파싱
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get("type").asText();
            JsonNode payload = root.get("payload");

            log.info("[KAFKA] Customer 동기화 이벤트 수신 - type: {}", type);

            if ("CUSTOMER_BULK_SYNC".equals(type)) {
                // 전체 Customer 동기화 (본사 서버 시작 시)
                CustomerListSyncEvent event = objectMapper.treeToValue(payload, CustomerListSyncEvent.class);
                syncCustomers(event.getCustomers());
                log.info("[KAFKA] 전체 Customer 동기화 완료 - count: {}", event.getCustomers().size());
            } else if ("CUSTOMER_CREATE".equals(type)) {
                // 신규 Customer 동기화 (다른 가맹점에서 생성)
                CustomerSyncEvent event = objectMapper.treeToValue(payload, CustomerSyncEvent.class);
                syncSingleCustomer(event);
                log.info("[KAFKA] 신규 Customer 동기화 완료 - id: {}, phone: {}", event.getId(), event.getPhone());
            } else {
                log.warn("[KAFKA] 알 수 없는 타입 수신 - type: {}", type);
            }

        } catch (Exception e) {
            log.error("[KAFKA] Customer 동기화 실패 - error: {}", e.getMessage(), e);
        }
    }

    private void syncCustomers(List<CustomerSyncEvent> customers) {
        for (CustomerSyncEvent event : customers) {
            syncSingleCustomer(event);
        }
    }

    private void syncSingleCustomer(CustomerSyncEvent event) {
        try {
            customerPersistencePort.upsert(
                    event.getId(),
                    event.getName(),
                    event.getPhone(),
                    event.getBirthDate(),
                    event.getCreatedAt(),
                    event.getUpdatedAt()
            );
            log.debug("[KAFKA] Customer UPSERT 완료 - id: {}, phone: {}", event.getId(), event.getPhone());

        } catch (Exception e) {
            log.error("[KAFKA] Customer 저장 실패 - id: {}, error: {}", event.getId(), e.getMessage(), e);
        }
    }
}
