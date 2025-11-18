package com.example.apidirect.payment.adapter.in.kafka;

import com.example.apidirect.payment.adapter.in.kafka.dto.CustomerReceiptSyncEvent;
import com.example.apidirect.payment.adapter.out.persistence.CustomerReceiptRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerReceiptKafkaConsumer {

    private final CustomerReceiptRepository customerReceiptRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "customer-receipt.sync",
            groupId = "api-direct-receipt-sync",
            containerFactory = "stringKafkaListenerFactory"
    )
    @Transactional
    public void consumeReceiptSync(String rawMessage) {
        try {
            log.info("[KAFKA] CustomerReceipt 동기화 메시지 수신");

            // JSON 파싱
            JsonNode root = objectMapper.readTree(rawMessage);
            String type = root.get("type").asText();
            JsonNode payload = root.get("payload");

            log.info("[KAFKA] CustomerReceipt 동기화 이벤트 수신 - type: {}", type);

            if ("CUSTOMER_RECEIPT_BULK_SYNC".equals(type)) {
                // 전체 영수증 대량 동기화 (모놀리스 서버 시작 시)
                JsonNode receiptsNode = payload.get("receipts");
                List<CustomerReceiptSyncEvent> events = objectMapper.convertValue(
                        receiptsNode,
                        new TypeReference<List<CustomerReceiptSyncEvent>>() {}
                );
                syncReceipts(events);
                log.info("[KAFKA] 전체 CustomerReceipt 동기화 완료 - count: {}", events.size());

            } else if ("CUSTOMER_RECEIPT".equals(type)) {
                // 단일 영수증 동기화
                CustomerReceiptSyncEvent event = objectMapper.treeToValue(payload, CustomerReceiptSyncEvent.class);
                upsertReceipt(event);
                log.info("[KAFKA] CustomerReceipt 동기화 완료 - id: {}, merchantUid: {}",
                        event.getId(), event.getMerchantUid());
            } else {
                log.warn("[KAFKA] 알 수 없는 타입 수신 - type: {}", type);
            }

        } catch (Exception e) {
            log.error("[KAFKA] CustomerReceipt 동기화 실패 - error: {}", e.getMessage(), e);
        }
    }

    private void syncReceipts(List<CustomerReceiptSyncEvent> events) {
        for (CustomerReceiptSyncEvent event : events) {
            upsertReceipt(event);
        }
    }

    private void upsertReceipt(CustomerReceiptSyncEvent event) {
        try {
            customerReceiptRepository.upsertReceipt(
                    event.getId(),
                    event.getPgProvider(),
                    event.getPgTid(),
                    event.getMerchantUid(),
                    event.getTotalAmount(),
                    event.getDiscountAmount(),
                    event.getCouponDiscount(),
                    event.getFinalAmount(),
                    event.getStoreId(),
                    event.getCustomerId(),
                    event.getMemberName(),
                    event.getMemberPhone(),
                    null, // posCode - 모놀리스에는 없는 필드
                    event.getStatus(),
                    event.getPaidAt(),
                    event.getCancelledAt(),
                    event.getCreatedAt(),
                    event.getUpdatedAt()
            );

            log.debug("[KAFKA] CustomerReceipt UPSERT 완료 - id: {}, merchantUid: {}",
                    event.getId(), event.getMerchantUid());

        } catch (Exception e) {
            log.error("[KAFKA] CustomerReceipt 저장 실패 - id: {}, error: {}",
                    event.getId(), e.getMessage(), e);
            throw e;
        }
    }
}
