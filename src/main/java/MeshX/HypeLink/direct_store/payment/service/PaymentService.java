package MeshX.HypeLink.direct_store.payment.service;

import MeshX.HypeLink.direct_store.payment.config.PortOneConfig;
import MeshX.HypeLink.direct_store.payment.exception.PaymentException;
import MeshX.HypeLink.direct_store.payment.model.dto.request.PaymentValidationReq;
import MeshX.HypeLink.direct_store.payment.model.entity.PosPayment;
import MeshX.HypeLink.direct_store.payment.repository.PaymentJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.posOrder.model.dto.request.PosOrderCreateReq;
import MeshX.HypeLink.direct_store.posOrder.model.dto.request.PosOrderItemDto;
import MeshX.HypeLink.direct_store.posOrder.model.dto.response.PosOrderDetailRes;
import MeshX.HypeLink.direct_store.posOrder.model.entity.PosOrder;
import MeshX.HypeLink.direct_store.posOrder.model.entity.PosOrderStatus;
import MeshX.HypeLink.direct_store.posOrder.repository.PosOrderJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.posOrder.service.PosOrderService;
import io.portone.sdk.server.payment.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static MeshX.HypeLink.direct_store.payment.exception.PaymentExceptionMessage.*;

// TODO: [도메인 책임 분리 위반] PaymentService가 Order 도메인에 강하게 결합됨
// TODO: 현재 문제점:
//  - Payment 도메인 서비스인데 Order 도메인 작업(주문 생성, 상태 변경)을 수행
//  - PosOrderService, PosOrderJpaRepositoryVerify에 의존 (강결합)
//  - PosOrderDetailRes 반환 (Payment 응답이 아닌 Order 응답)
//
// TODO: 실제 배포 시 리팩토링 방안:
//  [옵션 1] API 분리
//    - POST /api/payments/validate → PaymentValidationRes 반환 (검증만)
//    - POST /api/orders → PosOrderDetailRes 반환 (주문 생성)
//    - 프론트에서 순차 호출
//
//  [옵션 2] Facade/Orchestrator 패턴
//    - PaymentOrderFacade 서비스 생성
//    - PaymentService.validateOnly() + OrderService.createOrder() 조합
//    - 트랜잭션 경계를 Facade에서 관리
//
//  [옵션 3] Event-Driven (진짜 분리)
//    - PaymentService: 검증 후 PaymentValidatedEvent 발행
//    - OrderService: 이벤트 리스닝 후 주문 생성
//    - 비동기 처리, 각 도메인 완전 독립
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {

    private final PortOneService portOneService;
    private final PosOrderService orderService;              // TODO: 제거 필요 (Order 도메인)
    private final PosOrderJpaRepositoryVerify orderRepository;  // TODO: 제거 필요 (Order 도메인)
    private final PaymentJpaRepositoryVerify paymentRepository;
    private final PortOneConfig portOneConfig;


    @Transactional
    public PosOrderDetailRes validatePayment(PaymentValidationReq req) {
        try {
            // 포트원 서버에서 결제정보를 가져와서 진짜 결제가 완료 됐는지 검증로지ㄱ
                // 프론트가 준 결제id로 포트원한테 직접 검증함.
            Payment.Recognized portOnePayment =
                    fetchAndValidatePortOnePayment(req.getPaymentId());

            Integer actualAmount = (int) portOnePayment.getAmount().getTotal();
            Integer expectedAmount = calculateExpectedAmount(req.getOrderData());

            // 결제 금액 검증
            validatePaymentAmount(req.getPaymentId(), expectedAmount, actualAmount);

            // 주문생성쪽으로 코드 다 빼기
            PosOrder order = createOrderAndPayment(req, portOnePayment, actualAmount);

            orderService.updateOrderStatus(order.getId(), PosOrderStatus.PAID);

            return PosOrderDetailRes.toDto(order);

        } catch (PaymentException e) {
            throw e;
        } catch (Exception e) {
            // TODO: [중요] 예외 발생 시 결제 취소 호출하지만, 취소 실패 가능성 있음
            // TODO: PortOneService.cancelPayment() 참고 - 서버 장애 시 보상 로직 필요
            portOneService.cancelPayment(req.getPaymentId(), "주문 생성 중 오류 발생: " );
            throw new PaymentException(PAYMENT_VALIDATION_FAILED);
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
            portOneService.cancelPayment(paymentId, "결제 상태 불일치: " + paymentType);
            throw new PaymentException(PAYMENT_STATUS_INVALID);
        }

        return portOnePayment;
    }


    // TODO: [중요] 현재는 프론트가 보낸 금액(expectedAmount)과 PortOne 금액(actualAmount)만 비교
    // TODO: 실제 배포 시 expectedAmount는 DB에서 조회한 값이어야 함 (calculateExpectedAmount 수정 필요)
    private void validatePaymentAmount(String paymentId, Integer expectedAmount, Integer actualAmount) {
        if (!actualAmount.equals(expectedAmount)) {
            portOneService.cancelPayment(paymentId, "결제 금액 불일치 (예상: " + expectedAmount + ", 실제: " + actualAmount + ")");
            throw new PaymentException(PAYMENT_AMOUNT_MISMATCH);
        }
    }


    // TODO: [도메인 책임 분리 위반] 이 메서드는 Order 도메인 작업(주문 생성)을 하고 있음
    // TODO: 메서드 이름부터 문제: "createOrder"가 먼저 나옴 → 이건 OrderService 메서드여야 함
    // TODO: 실제 배포 시:
    //  - 주문 생성은 OrderService에서만
    //  - PaymentService는 savePaymentInfo(paymentId, orderId) 형태로 분리
    private PosOrder createOrderAndPayment(PaymentValidationReq req,
                                           Payment.Recognized portOnePayment,
                                           Integer actualAmount) {
        // ❌ Order 도메인 작업 (PaymentService가 할 일이 아님!)
        PosOrderDetailRes orderDetail = orderService.createOrder(req.getOrderData());
        PosOrder order = orderRepository.findById(orderDetail.getId());

        String channelKey = portOnePayment.getChannel() != null ?
                portOnePayment.getChannel().getId() : null;

        PosPayment posPaymentEntity = req.toPaymentEntity(
                order,
                portOnePayment.getTransactionId(),
                portOneConfig.getStoreId(),
                channelKey,
                actualAmount
        );

        paymentRepository.save(posPaymentEntity);

        return order;
    }


    private boolean isPaymentSuccess(Payment payment) {
        String className = payment.getClass().getSimpleName();
        return "PaidPayment".equals(className) || "VirtualAccountIssuedPayment".equals(className);
    }


    private Integer calculateExpectedAmount(PosOrderCreateReq orderData) {
        // TODO: [보안 취약점] 프론트에서 받은 금액을 그대로 사용 중 (프로토타입용)
        // TODO: 실제 배포 시 반드시 DB에서 상품 실제 가격 조회 후 서버에서 계산해야 함!
        // TODO: 예시 코드:
        //  Integer totalAmount = 0;
        //  for (PosOrderItemDto item : orderData.getItems()) {
        //      Product product = productRepository.findById(item.getProductId())
        //          .orElseThrow(() -> new ProductNotFoundException());
        //      totalAmount += product.getPrice() * item.getQuantity();
        //  }

        //  총 상품 금액 계산 (현재: 프론트가 보낸 subtotal을 그대로 신뢰)
        Integer totalAmount = orderData.getItems().stream()
                .mapToInt(PosOrderItemDto::getSubtotal)
                .sum();

        // TODO: [보안 취약점] 포인트와 쿠폰도 DB에서 검증 필요
        // TODO: 포인트: 사용자가 실제로 해당 포인트를 보유하고 있는지 DB 확인
        // TODO: 쿠폰: 쿠폰 ID로 DB 조회 후 실제 할인 금액 확인, 유효기간/사용여부 검증

        //  최종 결제 금액 계산 (총액 - 포인트 - 쿠폰)
        Integer finalAmount = totalAmount
                - (orderData.getPointsUsed() != null ? orderData.getPointsUsed() : 0)
                - (orderData.getCouponDiscount() != null ? orderData.getCouponDiscount() : 0);

        return finalAmount;
    }
}
