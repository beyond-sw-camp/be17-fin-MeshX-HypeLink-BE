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

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {

    private final PortOneService portOneService;
    private final PosOrderService orderService;
    private final PosOrderJpaRepositoryVerify orderRepository;
    private final PaymentJpaRepositoryVerify paymentRepository;
    private final PortOneConfig portOneConfig;


    @Transactional
    public PosOrderDetailRes validatePayment(PaymentValidationReq req) {
        try {
            Payment.Recognized portOnePayment =
                    fetchAndValidatePortOnePayment(req.getPaymentId());

            Integer actualAmount = (int) portOnePayment.getAmount().getTotal();
            Integer expectedAmount = calculateExpectedAmount(req.getOrderData());

            validatePaymentAmount(req.getPaymentId(), expectedAmount, actualAmount);

            PosOrder order = createOrderAndPayment(req, portOnePayment, actualAmount);

            orderService.updateOrderStatus(order.getId(), PosOrderStatus.PAID);

            return PosOrderDetailRes.toDto(order);

        } catch (PaymentException e) {
            throw e;
        } catch (Exception e) {
            portOneService.cancelPayment(req.getPaymentId(), "주문 생성 중 오류 발생: " + e.getMessage());
            throw new PaymentException(PAYMENT_VALIDATION_FAILED);
        }
    }


    private io.portone.sdk.server.payment.Payment.Recognized fetchAndValidatePortOnePayment(String paymentId) {
        io.portone.sdk.server.payment.Payment payment = portOneService.getPayment(paymentId);

        if (!(payment instanceof io.portone.sdk.server.payment.Payment.Recognized)) {
            throw new PaymentException(PAYMENT_VALIDATION_FAILED);
        }

        io.portone.sdk.server.payment.Payment.Recognized portOnePayment =
                (io.portone.sdk.server.payment.Payment.Recognized) payment;

        String paymentType = payment.getClass().getSimpleName();

        if (!isPaymentSuccess(payment)) {
            portOneService.cancelPayment(paymentId, "결제 상태 불일치: " + paymentType);
            throw new PaymentException(PAYMENT_STATUS_INVALID);
        }

        return portOnePayment;
    }


    private void validatePaymentAmount(String paymentId, Integer expectedAmount, Integer actualAmount) {
        if (!actualAmount.equals(expectedAmount)) {
            portOneService.cancelPayment(paymentId, "결제 금액 불일치 (예상: " + expectedAmount + ", 실제: " + actualAmount + ")");
            throw new PaymentException(PAYMENT_AMOUNT_MISMATCH);
        }
    }


    private PosOrder createOrderAndPayment(PaymentValidationReq req,
                                           Payment.Recognized portOnePayment,
                                           Integer actualAmount) {
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


    private boolean isPaymentSuccess(io.portone.sdk.server.payment.Payment payment) {
        String className = payment.getClass().getSimpleName();
        return "PaidPayment".equals(className) || "VirtualAccountIssued".equals(className);
    }


    private Integer calculateExpectedAmount(PosOrderCreateReq orderData) {
        //  총 상품 금액 계산
        Integer totalAmount = orderData.getItems().stream()
                .mapToInt(PosOrderItemDto::getSubtotal)
                .sum();

        //  최종 결제 금액 계산 (총액 - 포인트 - 쿠폰) 나중에 수정될 수도 있씀
        Integer finalAmount = totalAmount
                - (orderData.getPointsUsed() != null ? orderData.getPointsUsed() : 0)
                - (orderData.getCouponDiscount() != null ? orderData.getCouponDiscount() : 0);

        return finalAmount;
    }
}
