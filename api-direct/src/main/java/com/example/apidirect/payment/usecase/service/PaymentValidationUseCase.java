package com.example.apidirect.payment.usecase.service;

import MeshX.common.UseCase;
import io.portone.sdk.server.payment.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.apidirect.auth.domain.POS;
import com.example.apidirect.auth.domain.Store;
import com.example.apidirect.auth.usecase.port.in.POSQueryPort;
import com.example.apidirect.auth.usecase.port.in.StoreQueryPort;
import com.example.apidirect.config.PortOneConfig;
import com.example.apidirect.customer.domain.Customer;
import com.example.apidirect.customer.usecase.port.in.CustomerQueryPort;
import com.example.apidirect.customer.usecase.port.out.CouponFeignPort;
import com.example.apidirect.item.domain.StoreItemDetail;
import com.example.apidirect.item.usecase.port.in.ItemQueryPort;
import com.example.apidirect.item.usecase.port.out.ItemDetailPersistencePort;
import com.example.apidirect.payment.common.PaymentException;
import com.example.apidirect.payment.domain.CustomerReceipt;
import com.example.apidirect.payment.domain.OrderItem;
import com.example.apidirect.payment.domain.PaymentStatus;
import com.example.apidirect.payment.domain.Payments;
import com.example.apidirect.payment.usecase.port.in.PaymentValidationWebPort;
import com.example.apidirect.payment.usecase.port.in.request.PaymentValidationCommand;
import com.example.apidirect.payment.usecase.port.in.request.ReceiptCreateCommand;
import com.example.apidirect.payment.usecase.port.in.request.ReceiptItemCommand;
import com.example.apidirect.payment.usecase.port.out.CustomerReceiptPersistencePort;
import com.example.apidirect.payment.usecase.port.out.OrderItemPersistencePort;
import com.example.apidirect.payment.usecase.port.out.PaymentsPersistencePort;
import com.example.apidirect.payment.adapter.out.kafka.PaymentSyncProducer;
import com.example.apidirect.payment.adapter.out.kafka.dto.PaymentSyncEvent;
import com.example.apidirect.payment.adapter.out.kafka.mapper.PaymentSyncMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.apidirect.payment.common.PaymentExceptionType.*;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentValidationUseCase implements PaymentValidationWebPort {

    private final PortOneUseCase portOneUseCase;
    private final POSQueryPort posQueryPort;
    private final StoreQueryPort storeQueryPort;
    private final ItemQueryPort itemQueryPort;
    private final CustomerQueryPort customerQueryPort;
    private final ItemDetailPersistencePort itemDetailPersistencePort;
    private final CustomerReceiptPersistencePort customerReceiptPersistencePort;
    private final OrderItemPersistencePort orderItemPersistencePort;
    private final PaymentsPersistencePort paymentsPersistencePort;
    private final PortOneConfig portOneConfig;
    private final CouponFeignPort couponFeignPort;
    private final PaymentSyncProducer paymentSyncProducer;

    @Override
    @Transactional
    public void validatePayment(PaymentValidationCommand command, Integer memberId) {
        try {
            // 1. PortOne에서 결제 정보 조회 및 검증
            Payment.Recognized portOnePayment = fetchAndValidatePortOnePayment(command.getPaymentId());

            // 2. 예상 결제 금액 계산
            Integer expectedAmount = calculateExpectedAmount(command.getOrderData());
            Integer actualAmount = (int) portOnePayment.getAmount().getTotal();

            // 3. 결제 금액 검증
            validatePaymentAmount(command.getPaymentId(), expectedAmount, actualAmount);

            // 4. POS 및 Store 조회
            POS pos = posQueryPort.findByMemberId(memberId);
            Store store = storeQueryPort.findById(pos.getStoreId());

            // 5. CustomerReceipt 및 OrderItem 생성
            createReceiptAndPayment(command, portOnePayment, actualAmount, pos, store);

            log.info("결제 검증 완료 - paymentId: {}", command.getPaymentId());

        } catch (Exception e) {
            // 검증 실패 시 결제 취소
            String cancelReason = truncateCancelReason("주문 처리 중 오류 발생: " + e.getMessage());
            portOneUseCase.cancelPayment(command.getPaymentId(), cancelReason);

            // 예외 재발생
            if (e instanceof PaymentException) {
                throw (PaymentException) e;
            } else {
                log.error("결제 검증 중 예상치 못한 오류 발생", e);
                throw new PaymentException(PAYMENT_VALIDATION_FAILED);
            }
        }
    }

    @Override
    public void testPaymentKafka(ReceiptCreateCommand command, Integer memberId) {

    }

    /**
     * PortOne에서 결제 정보 조회 및 상태 검증
     */
    private Payment.Recognized fetchAndValidatePortOnePayment(String paymentId) {
        Payment payment = portOneUseCase.getPayment(paymentId);

        if (!(payment instanceof Payment.Recognized)) {
            throw new PaymentException(PAYMENT_VALIDATION_FAILED);
        }

        Payment.Recognized portOnePayment = (Payment.Recognized) payment;
        String paymentType = payment.getClass().getSimpleName();

        // 결제 상태 검증
        if (!isPaymentSuccess(payment)) {
            String cancelReason = truncateCancelReason("결제 상태 불일치: " + paymentType);
            portOneUseCase.cancelPayment(paymentId, cancelReason);
            throw new PaymentException(PAYMENT_STATUS_INVALID);
        }

        return portOnePayment;
    }

    /**
     * 결제 금액 검증
     */
    private void validatePaymentAmount(String paymentId, Integer expectedAmount, Integer actualAmount) {
        if (!actualAmount.equals(expectedAmount)) {
            String cancelReason = truncateCancelReason("결제 금액 불일치 (예상: " + expectedAmount + ", 실제: " + actualAmount + ")");
            portOneUseCase.cancelPayment(paymentId, cancelReason);
            throw new PaymentException(PAYMENT_AMOUNT_MISMATCH);
        }
    }

    /**
     * 결제 성공 여부 확인
     */
    private boolean isPaymentSuccess(Payment payment) {
        String className = payment.getClass().getSimpleName();
        return "PaidPayment".equals(className) || "VirtualAccountIssuedPayment".equals(className);
    }

    /**
     * 예상 결제 금액 계산
     */
    private Integer calculateExpectedAmount(ReceiptCreateCommand orderData) {
        // 총 상품 금액 계산
        Integer totalAmount = orderData.getItems().stream()
                .mapToInt(item -> item.getSubtotal())
                .sum();

        // 최종 결제 금액 = 총액 - 쿠폰 할인
        Integer finalAmount = totalAmount
                - (orderData.getCouponDiscount() != null ? orderData.getCouponDiscount() : 0);

        return finalAmount;
    }

    /**
     * PortOne API cancelReason 길이 제한(255자)에 맞게 문자열 자르기
     */
    private String truncateCancelReason(String reason) {
        if (reason == null) {
            return null;
        }
        if (reason.length() <= 255) {
            return reason;
        }
        return reason.substring(0, 252) + "...";
    }

    /**
     * CustomerReceipt, OrderItem, Payment 생성 및 재고 차감
     */
    private void createReceiptAndPayment(PaymentValidationCommand command,
                                         Payment.Recognized portOnePayment,
                                         Integer actualAmount,
                                         POS pos,
                                         Store store) {
        ReceiptCreateCommand orderData = command.getOrderData();

        // 1. Customer 조회
        // - 로컬 DB 우선 조회
        // - 없으면 본사 Feign 호출 → 로컬 DB에 저장
        // - 없으면 null (비회원 구매)
        Integer customerId = null;
        if (orderData.getMemberId() != null) {
            try {
                Customer customer = customerQueryPort.getCustomerForPayment(orderData.getMemberId());
                customerId = customer.getId();
                log.info("Customer 조회 완료 - customerId: {}, name: {}", customerId, customer.getName());
            } catch (Exception e) {
                log.warn("Customer 조회 실패, 비회원으로 처리 - memberId: {}, error: {}",
                        orderData.getMemberId(), e.getMessage());
                // Customer 조회 실패해도 비회원으로 결제 진행
                customerId = null;
            }
        }

        // 2. merchantUid 생성
        String merchantUid = pos.getPosCode() + "-" + UUID.randomUUID().toString().substring(0, 8);

        // 3. 총 금액 계산
        Integer totalAmount = orderData.getItems().stream()
                .mapToInt(ReceiptItemCommand::getSubtotal)
                .sum();

        // 4. CustomerReceipt 생성
        CustomerReceipt receipt = CustomerReceipt.builder()
                .pgProvider("portone")
                .pgTid(portOnePayment.getTransactionId())
                .merchantUid(merchantUid)
                .totalAmount(totalAmount)
                .discountAmount(0)
                .couponDiscount(orderData.getCouponDiscount() != null ? orderData.getCouponDiscount() : 0)
                .finalAmount(actualAmount)
                .storeId(store.getId())
                .customerId(customerId)  // null 가능
                .memberName(orderData.getMemberName())
                .memberPhone(orderData.getMemberPhone())
                .status(PaymentStatus.PAID)
                .paidAt(LocalDateTime.now())
                .build();

        log.info("CustomerReceipt 객체 생성 완료. merchantUid: {}", receipt.getMerchantUid());

        // 5. CustomerReceipt 저장
        CustomerReceipt savedReceipt = customerReceiptPersistencePort.save(receipt);
        log.info("CustomerReceipt 저장 완료. ID: {}", savedReceipt.getId());

        // 6. OrderItem 생성 및 재고 차감 루프
        log.info("OrderItem 생성 루프 시작. 총 {}개 아이템.", orderData.getItems().size());
        List<OrderItem> orderItems = new ArrayList<>();
        for (ReceiptItemCommand itemDto : orderData.getItems()) {
            log.info("아이템 처리 시작. storeItemDetailId: {}, quantity: {}",
                    itemDto.getStoreItemDetailId(), itemDto.getQuantity());

            // 비관적 락으로 재고 조회
            StoreItemDetail storeItemDetail = itemQueryPort.findByIdWithLock(itemDto.getStoreItemDetailId());

            // 재고 확인
            if (storeItemDetail.getStock() < itemDto.getQuantity()) {
                log.error("재고 부족 - itemDetailId: {}, 요청: {}, 재고: {}",
                        itemDto.getStoreItemDetailId(), itemDto.getQuantity(), storeItemDetail.getStock());
                throw new PaymentException(INSUFFICIENT_STOCK);
            }

            // 재고 차감
            storeItemDetail.decreaseStock(itemDto.getQuantity());
            itemDetailPersistencePort.save(storeItemDetail);
            log.info("재고 차감 완료. 남은 재고: {}", storeItemDetail.getStock());

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
            log.info("OrderItem 저장 완료. storeItemDetailId: {}", orderItem.getStoreItemDetailId());
        }
        log.info("OrderItem 생성 루프 종료.");

        // 쿠폰 사용 처리
        if (orderData.getCustomerCouponId() != null) {
            try {
                couponFeignPort.useCoupon(orderData.getCustomerCouponId());
                log.info("쿠폰 사용 처리 완료 - couponId: {}", orderData.getCustomerCouponId());
            } catch (Exception e) {
                log.error("쿠폰 사용 처리 실패 - couponId: {}, error: {}",
                        orderData.getCustomerCouponId(), e.getMessage());
                // 쿠폰 실패해도 결제는 진행 (or 예외 발생)
            }
        }

        // 8. Payments 엔티티 생성 및 저장
        String channelKey = portOnePayment.getChannel() != null ?
                portOnePayment.getChannel().getId() : null;

        Payments payment = Payments.builder()
                .customerReceiptId(savedReceipt.getId())
                .paymentId(command.getPaymentId())
                .transactionId(portOnePayment.getTransactionId())
                .storeId(portOneConfig.getStoreId())
                .channelKey(channelKey)
                .amount(actualAmount)
                .status(PaymentStatus.PAID)
                .paidAt(LocalDateTime.now())
                .build();

        Payments savedPayment = paymentsPersistencePort.save(payment);
        log.info("Payments 저장 완료. paymentId: {}", payment.getPaymentId());

        // 9. 본사 동기화 (Kafka)
        try {
            PaymentSyncEvent event = PaymentSyncMapper.toEvent(savedReceipt, orderItems, savedPayment);
            paymentSyncProducer.sendPaymentSync(event);
            log.info("본사 결제 동기화 이벤트 발송 완료 - merchantUid: {}", savedReceipt.getMerchantUid());
        } catch (Exception e) {
            log.error("본사 결제 동기화 이벤트 발송 실패 - merchantUid: {}, error: {}",
                    savedReceipt.getMerchantUid(), e.getMessage(), e);
            // Kafka 전송 실패해도 예외 던지지 않음 (가맹점 결제는 이미 완료)
        }
    }

}
