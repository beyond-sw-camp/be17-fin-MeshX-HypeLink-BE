package MeshX.HypeLink.direct_store.payment.service;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.direct_store.item.repository.StoreItemJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.payment.config.PortOneConfig;
import MeshX.HypeLink.direct_store.payment.exception.PaymentException;
import MeshX.HypeLink.direct_store.payment.model.dto.request.PaymentValidationReq;
import MeshX.HypeLink.direct_store.payment.model.dto.request.ReceiptCreateReq;
import MeshX.HypeLink.direct_store.payment.model.dto.request.ReceiptItemDto;
import MeshX.HypeLink.direct_store.payment.model.entity.Payments;
import MeshX.HypeLink.direct_store.payment.repository.PaymentJpaRepositoryVerify;
import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
import MeshX.HypeLink.head_office.customer.model.entity.OrderItem;
import MeshX.HypeLink.head_office.customer.model.entity.PaymentStatus;
import MeshX.HypeLink.head_office.customer.repository.CustomerJpaRepositoryVerify;
import MeshX.HypeLink.head_office.customer.repository.CustomerReceiptRepository;
import io.portone.sdk.server.payment.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static MeshX.HypeLink.direct_store.payment.exception.PaymentExceptionMessage.*;

// TODO: [도메인 책임 분리] PaymentService가 CustomerReceipt 생성을 직접 담당
// TODO: 현재 구조:
//  - Payment 도메인에서 CustomerReceipt와 OrderItem 생성
//  - 결제 검증 후 즉시 영수증 생성 (동기 처리)
//
// TODO: 실제 배포 시 고려 사항:
//  [옵션 1] Event-Driven 패턴
//    - PaymentService: 검증 후 PaymentValidatedEvent 발행
//    - ReceiptService: 이벤트 리스닝 후 영수증 생성
//    - 비동기 처리로 각 도메인 독립성 확보
//
//  [옵션 2] Facade 패턴
//    - PaymentReceiptFacade 서비스 생성
//    - PaymentService.validate() + ReceiptService.create() 조합
//    - 트랜잭션 경계를 Facade에서 관리
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {

    private final PortOneService portOneService;
    private final CustomerReceiptRepository receiptRepository;
    private final CustomerJpaRepositoryVerify customerRepository;
    private final StoreItemJpaRepositoryVerify storeItemRepository;
    private final PaymentJpaRepositoryVerify paymentRepository;
    private final PortOneConfig portOneConfig;
    private final StoreJpaRepositoryVerify storeRepository;


    @Transactional
    public CustomerReceipt validatePayment(PaymentValidationReq req) {
        try {
            // 포트원 서버에서 결제정보를 가져와서 진짜 결제가 완료 됐는지 검증
            Payment.Recognized portOnePayment = fetchAndValidatePortOnePayment(req.getPaymentId());

            Integer actualAmount = (int) portOnePayment.getAmount().getTotal();
            Integer expectedAmount = calculateExpectedAmount(req.getOrderData());

            // 결제 금액 검증
            validatePaymentAmount(req.getPaymentId(), expectedAmount, actualAmount);

            // CustomerReceipt 및 Payment 생성
            CustomerReceipt receipt = createReceiptAndPayment(req, portOnePayment, actualAmount);

            return receipt;

        } catch (Exception e) { // 어떤 종류의 예외든 여기서 한 번에 잡습니다.
            // 1. 먼저 결제를 취소합니다.
            String cancelReason = truncateCancelReason("주문 처리 중 오류 발생: " + e.getMessage());
            portOneService.cancelPayment(req.getPaymentId(), cancelReason);

            // 2. 원래의 PaymentException은 그대로 다시 던지고,
            //    그 외의 예외는 로그를 남기고 새로운 PaymentException으로 포장해서 던집니다.
            if (e instanceof PaymentException) {
                throw (PaymentException) e;
            } else {
                log.error("결제 검증 중 예상치 못한 오류 발생", e);
                throw new PaymentException(PAYMENT_VALIDATION_FAILED);
            }
        }
    }

    // 프론트에서 받은 paymentId(각종 예외처리 거치고) 형 변환해서 리턴시키는 메서드
    private Payment.Recognized fetchAndValidatePortOnePayment(String paymentId) {
        Payment payment = portOneService.getPayment(paymentId);

        if (!(payment instanceof Payment.Recognized)) {
            throw new PaymentException(PAYMENT_VALIDATION_FAILED);
        }

        Payment.Recognized portOnePayment = (Payment.Recognized) payment;

        String paymentType = payment.getClass().getSimpleName();

        //결제 상태 검증
        if (!isPaymentSuccess(payment)) {
            String cancelReason = truncateCancelReason("결제 상태 불일치: " + paymentType);
            portOneService.cancelPayment(paymentId, cancelReason);
            throw new PaymentException(PAYMENT_STATUS_INVALID);
        }

        return portOnePayment;
    }


    // TODO: [중요] 현재는 프론트가 보낸 금액(expectedAmount)과 PortOne 금액(actualAmount)만 비교
    // TODO: 실제 배포 시 expectedAmount는 DB에서 조회한 값이어야 함 (calculateExpectedAmount 수정 필요)
    private void validatePaymentAmount(String paymentId, Integer expectedAmount, Integer actualAmount) {
        if (!actualAmount.equals(expectedAmount)) {
            String cancelReason = truncateCancelReason("결제 금액 불일치 (예상: " + expectedAmount + ", 실제: " + actualAmount + ")");
            portOneService.cancelPayment(paymentId, cancelReason);
            throw new PaymentException(PAYMENT_AMOUNT_MISMATCH);
        }
    }


    private CustomerReceipt createReceiptAndPayment(PaymentValidationReq req,
                                                    Payment.Recognized portOnePayment,
                                                    Integer actualAmount) {
        log.info("createReceiptAndPayment 메서드 시작. paymentId: {}", req.getPaymentId());
        ReceiptCreateReq orderData = req.getOrderData();


        Customer customer = null;
        if (orderData.getMemberId() != null) {
            customer = customerRepository.findById(orderData.getMemberId());
            log.info("고객 정보 조회 완료. customerId: {}", customer != null ? customer.getId() : "null");
        } else {
            log.info("비회원 결제. memberId: null");
        }

        Store store = storeRepository.findById(orderData.getStoreId());
        log.info("가게 정보 조회 완료. storeId: {}", store != null ? store.getId() : "null");

        String merchantUid = "ORDER-" + UUID.randomUUID().toString();
        log.info("merchantUid 생성: {}", merchantUid);

        Integer totalAmount = orderData.getItems().stream()
                .mapToInt(ReceiptItemDto::getSubtotal)
                .sum();
        log.info("주문 상품 총액 계산 완료. totalAmount: {}", totalAmount);

        CustomerReceipt receipt = CustomerReceipt.builder()
                .pgProvider("portone")
                .pgTid(portOnePayment.getTransactionId())
                .merchantUid(merchantUid)
                .amount(actualAmount)
                .totalAmount(totalAmount)
                .discountAmount(0)
                .couponDiscount(orderData.getCouponDiscount() != null ? orderData.getCouponDiscount() : 0)
                .finalAmount(actualAmount)
                .store(store)
                .customer(customer)
                .memberName(orderData.getMemberName())
                .memberPhone(orderData.getMemberPhone())
                .status(PaymentStatus.PAID)
                .paidAt(LocalDateTime.now())
                .build();
        log.info("CustomerReceipt 객체 생성 완료. merchantUid: {}", receipt.getMerchantUid());

        log.info("OrderItem 생성 루프 시작. 총 {}개 아이템.", orderData.getItems().size());
        for (ReceiptItemDto itemDto : orderData.getItems()) {
            log.info("아이템 처리 시작. productId: {}, quantity: {}", itemDto.getProductId(), itemDto.getQuantity());
            StoreItem storeItem = storeItemRepository.findById(itemDto.getProductId());
            log.info("StoreItem 조회 완료. storeItemId: {}", storeItem.getId());

            OrderItem orderItem = OrderItem.builder()
                    .storeItem(storeItem)
                    .quantity(itemDto.getQuantity())
                    .unitPrice(itemDto.getUnitPrice())
                    .totalPrice(itemDto.getSubtotal())
                    .build();
            log.info("OrderItem 객체 생성 완료. productId: {}", orderItem.getStoreItem().getId());

            receipt.addOrderItem(orderItem);
            log.info("OrderItem 영수증에 추가 완료.");
        }
        log.info("OrderItem 생성 루프 종료.");


        receiptRepository.save(receipt);
        log.info("CustomerReceipt 저장 완료. ID: {}", receipt.getId());


        String channelKey = portOnePayment.getChannel() != null ?
                portOnePayment.getChannel().getId() : null;

        Payments paymentsEntity = Payments.builder()
                .customerReceipt(receipt)
                .paymentId(req.getPaymentId())
                .transactionId(portOnePayment.getTransactionId())
                .storeId(portOneConfig.getStoreId())
                .channelKey(channelKey)
                .amount(actualAmount)
                .status(MeshX.HypeLink.direct_store.payment.model.entity.PaymentStatus.PAID)
                .paidAt(LocalDateTime.now())
                .build();
        log.info("Payments 객체 생성 완료. paymentId: {}", paymentsEntity.getPaymentId());

        paymentRepository.save(paymentsEntity);
        log.info("Payments 저장 완료. ID: {}", paymentsEntity.getId());

        log.info("createReceiptAndPayment 메서드 종료.");
        return receipt;
    }


    private boolean isPaymentSuccess(Payment payment) {
        String className = payment.getClass().getSimpleName();
        return "PaidPayment".equals(className) || "VirtualAccountIssuedPayment".equals(className);
    }


    private Integer calculateExpectedAmount(ReceiptCreateReq orderData) {
        // TODO: [보안 취약점] 프론트에서 받은 금액을 그대로 사용 중 (프로토타입용)
        // TODO: 실제 배포 시 반드시 DB에서 상품 실제 가격 조회 후 서버에서 계산해야 함!
        // TODO: 예시 코드:
        //  Integer totalAmount = 0;
        //  for (ReceiptItemDto item : orderData.getItems()) {
        //      Product product = productRepository.findById(item.getProductId())
        //          .orElseThrow(() -> new ProductNotFoundException());
        //      totalAmount += product.getPrice() * item.getQuantity();
        //  }

        //  총 상품 금액 계산 (현재: 프론트가 보낸 subtotal을 그대로 신뢰)
        Integer totalAmount = orderData.getItems().stream()
                .mapToInt(ReceiptItemDto::getSubtotal)
                .sum();

        // TODO: [보안 취약점] 쿠폰도 DB에서 검증 필요
        // TODO: 쿠폰: 쿠폰 ID로 DB 조회 후 실제 할인 금액 확인, 유효기간/사용여부 검증

        //  최종 결제 금액 계산 (총액 - 쿠폰)
        Integer finalAmount = totalAmount
                - (orderData.getCouponDiscount() != null ? orderData.getCouponDiscount() : 0);

        return finalAmount;
    }

    /**
     * PortOne API cancelReason 길이 제한(255자)에 맞게 문자열 자르기
     * @param reason 원본 취소 사유
     * @return 255자로 제한된 취소 사유
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
}
