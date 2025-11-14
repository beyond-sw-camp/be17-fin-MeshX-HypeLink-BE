package org.example.apidirect.payment.adapter.in.web;

import MeshX.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.auth.domain.Store;
import org.example.apidirect.auth.usecase.port.in.StoreQueryPort;
import org.example.apidirect.customer.domain.Customer;
import org.example.apidirect.customer.usecase.port.in.CustomerQueryPort;
import org.example.apidirect.item.domain.StoreItemDetail;
import org.example.apidirect.item.usecase.port.in.ItemQueryPort;
import org.example.apidirect.item.usecase.port.out.ItemDetailPersistencePort;
import org.example.apidirect.payment.adapter.out.kafka.PaymentSyncProducer;
import org.example.apidirect.payment.adapter.out.kafka.dto.PaymentSyncEvent;
import org.example.apidirect.payment.adapter.out.kafka.mapper.PaymentSyncMapper;
import org.example.apidirect.payment.domain.CustomerReceipt;
import org.example.apidirect.payment.domain.OrderItem;
import org.example.apidirect.payment.domain.PaymentStatus;
import org.example.apidirect.payment.domain.Payments;
import org.example.apidirect.payment.usecase.port.in.request.ReceiptCreateCommand;
import org.example.apidirect.payment.usecase.port.in.request.ReceiptItemCommand;
import org.example.apidirect.payment.usecase.port.out.CustomerReceiptPersistencePort;
import org.example.apidirect.payment.usecase.port.out.OrderItemPersistencePort;
import org.example.apidirect.payment.usecase.port.out.PaymentsPersistencePort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentTestController {

    private final StoreQueryPort storeQueryPort;
    private final ItemQueryPort itemQueryPort;
    private final CustomerQueryPort customerQueryPort;
    private final ItemDetailPersistencePort itemDetailPersistencePort;
    private final CustomerReceiptPersistencePort customerReceiptPersistencePort;
    private final OrderItemPersistencePort orderItemPersistencePort;
    private final PaymentsPersistencePort paymentsPersistencePort;
    private final PaymentSyncProducer paymentSyncProducer;

    /**
     * PortOne 검증 없이 Kafka 전송만 테스트
     */
    @PostMapping("/kafka-test")
    @Transactional
    public ResponseEntity<BaseResponse<String>> testPaymentKafka(
            @RequestBody ReceiptCreateCommand command) {

        log.info("[TEST] Kafka 결제 테스트 시작 - storeId: {}", command.getStoreId());

        try {
            // 1. Store 조회 (storeId를 command에서 받음)
            Store store = storeQueryPort.findById(command.getStoreId());

            // 2. Customer 조회 (nullable)
            Integer customerId = null;
            if (command.getMemberId() != null) {
                try {
                    Customer customer = customerQueryPort.getCustomerForPayment(command.getMemberId());
                    customerId = customer.getId();
                    log.info("[TEST] Customer 조회 완료 - customerId: {}", customerId);
                } catch (Exception e) {
                    log.warn("[TEST] Customer 조회 실패, 비회원으로 처리");
                }
            }

            // 3. merchantUid 생성
            String merchantUid = "STORE" + command.getStoreId() + "-TEST-" + UUID.randomUUID().toString().substring(0, 8);

            // 4. 총 금액 계산
            Integer totalAmount = command.getItems().stream()
                    .mapToInt(ReceiptItemCommand::getSubtotal)
                    .sum();

            Integer finalAmount = totalAmount
                    - (command.getCouponDiscount() != null ? command.getCouponDiscount() : 0);

            // 5. CustomerReceipt 생성
            CustomerReceipt receipt = CustomerReceipt.builder()
                    .pgProvider("test")
                    .pgTid("TEST-TID-" + System.currentTimeMillis())
                    .merchantUid(merchantUid)
                    .totalAmount(totalAmount)
                    .discountAmount(0)
                    .couponDiscount(command.getCouponDiscount() != null ? command.getCouponDiscount() : 0)
                    .finalAmount(finalAmount)
                    .storeId(store.getId())
                    .customerId(customerId)
                    .memberName(command.getMemberName())
                    .memberPhone(command.getMemberPhone())
                    .posCode("TEST-POS-001")
                    .status(PaymentStatus.PAID)
                    .paidAt(LocalDateTime.now())
                    .build();

            CustomerReceipt savedReceipt = customerReceiptPersistencePort.save(receipt);
            log.info("[TEST] CustomerReceipt 저장 완료 - id: {}, merchantUid: {}",
                    savedReceipt.getId(), savedReceipt.getMerchantUid());

            // 6. OrderItem 생성 및 재고 차감
            List<OrderItem> orderItems = new ArrayList<>();
            for (ReceiptItemCommand itemDto : command.getItems()) {
                // 비관적 락으로 재고 조회
                StoreItemDetail storeItemDetail = itemQueryPort.findByIdWithLock(itemDto.getStoreItemDetailId());

                // 재고 확인
                if (storeItemDetail.getStock() < itemDto.getQuantity()) {
                    log.error("[TEST] 재고 부족 - itemDetailId: {}, 요청: {}, 재고: {}",
                            itemDto.getStoreItemDetailId(), itemDto.getQuantity(), storeItemDetail.getStock());
                    throw new RuntimeException("재고 부족");
                }

                // 재고 차감
                storeItemDetail.decreaseStock(itemDto.getQuantity());
                itemDetailPersistencePort.save(storeItemDetail);
                log.info("[TEST] 재고 차감 완료 - 남은 재고: {}", storeItemDetail.getStock());

                // OrderItem 생성
                OrderItem orderItem = OrderItem.builder()
                        .customerReceiptId(savedReceipt.getId())
                        .storeItemDetailId(itemDto.getStoreItemDetailId())
                        .quantity(itemDto.getQuantity())
                        .unitPrice(itemDto.getUnitPrice())
                        .totalPrice(itemDto.getSubtotal())
                        .build();

                OrderItem savedOrderItem = orderItemPersistencePort.save(orderItem);
                orderItems.add(savedOrderItem);
                log.info("[TEST] OrderItem 저장 완료 - storeItemDetailId: {}", orderItem.getStoreItemDetailId());
            }

            // 7. Payments 엔티티 생성
            Payments payment = Payments.builder()
                    .customerReceiptId(savedReceipt.getId())
                    .paymentId("TEST-PAYMENT-" + System.currentTimeMillis())
                    .transactionId("TEST-TRANSACTION-" + System.currentTimeMillis())
                    .storeId("test-store")
                    .channelKey("test-channel")
                    .amount(finalAmount)
                    .status(PaymentStatus.PAID)
                    .paidAt(LocalDateTime.now())
                    .build();

            Payments savedPayment = paymentsPersistencePort.save(payment);
            log.info("[TEST] Payments 저장 완료 - paymentId: {}", savedPayment.getPaymentId());

            // 8. Kafka 전송
            PaymentSyncEvent event = PaymentSyncMapper.toEvent(savedReceipt, orderItems, savedPayment);
            paymentSyncProducer.sendPaymentSync(event);
            log.info("[TEST] Kafka 전송 완료 - merchantUid: {}", savedReceipt.getMerchantUid());

            return ResponseEntity.ok(BaseResponse.of(
                    "테스트 완료: merchantUid=" + savedReceipt.getMerchantUid() +
                            ", receiptId=" + savedReceipt.getId()));

        } catch (Exception e) {
            log.error("[TEST] Kafka 결제 테스트 실패", e);
            return ResponseEntity.badRequest().body(BaseResponse.of("테스트 실패: " + e.getMessage()));
        }
    }
}
