package MeshX.HypeLink.head_office.customer.kafka.consumer;

import MeshX.HypeLink.head_office.customer.kafka.dto.CustomerSyncEvent;
import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import MeshX.HypeLink.head_office.customer.repository.CustomerJpaRepositoryVerify;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerKafkaConsumer {

    private final CustomerJpaRepositoryVerify customerRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "customer.sync",
            groupId = "monolith-customer-sync-local",
            containerFactory = "stringKafkaListenerFactory"
    )
    public void consumeCustomerSync(String rawMessage) {
        try {
            log.info("[KAFKA] Customer 동기화 메시지 수신 - rawMessage: ");

            // JSON 파싱
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get("type").asText();
            JsonNode payload = root.get("payload");

            log.info("[KAFKA] Customer 동기화 이벤트 수신 - type: {}", type);

            if ("CUSTOMER_CREATE".equals(type)) {
                // 직영점에서 생성된 신규 Customer 수신
                CustomerSyncEvent event = objectMapper.treeToValue(payload, CustomerSyncEvent.class);
                syncNewCustomer(event);
                log.info("[KAFKA] 신규 Customer 동기화 완료 - phone: {}", event.getPhone());
            } else if ("CUSTOMER_BULK_SYNC".equals(type)) {
                // 본사 자신이 보낸 메시지, 무시
                log.debug("[KAFKA] CUSTOMER_BULK_SYNC 메시지 수신 (본사 자신이 보낸 것) - 스킵");
            } else {
                log.warn("[KAFKA] 알 수 없는 타입 수신 - type: {}", type);
            }

        } catch (Exception e) {
            log.error("[KAFKA] Customer 동기화 실패 - error: {}", e.getMessage(), e);
        }
    }

    private void syncNewCustomer(CustomerSyncEvent event) {
        try {
            Customer customer = Customer.builder()
                    .name(event.getName())
                    .phone(event.getPhone())
                    .birthDate(event.getBirthDate())
                    .build();

            customerRepository.save(customer);
            log.info("[KAFKA] 신규 Customer 저장 완료 - phone: {}", event.getPhone());

        } catch (Exception e) {
            log.error("[KAFKA] Customer 저장 실패 - phone: {}, error: {}", event.getPhone(), e.getMessage(), e);
        }
    }
}
