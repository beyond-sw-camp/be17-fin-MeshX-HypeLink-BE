package MeshX.HypeLink.direct_store.payment.service;

import MeshX.HypeLink.direct_store.pos.payment.exception.PaymentException;
import io.portone.sdk.server.PortOneClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static MeshX.HypeLink.direct_store.pos.payment.exception.PaymentExceptionMessage.PORTONE_API_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortOneService {

    private final PortOneClient portOneClient;

    /**
     * PortOne API를 통해 결제 정보를 조회합니다.
     * @param paymentId PortOne 결제 ID
     * @return 결제 정보
     */
    public io.portone.sdk.server.payment.Payment getPayment(String paymentId) {
        try {
            log.info("Requesting payment info from PortOne API. paymentId: {}", paymentId);
            io.portone.sdk.server.payment.Payment response = portOneClient.getPayment().getPayment(paymentId).join();
            log.info("Successfully retrieved payment info from PortOne API");
            return response;
        } catch (Exception e) {
            log.error("Failed to get payment from PortOne API. paymentId: {}, error: {}", paymentId, e.getMessage(), e);
            throw new PaymentException(PORTONE_API_ERROR);
        }
    }

    /**
     * 결제를 전액 취소합니다.
     * @param paymentId PortOne 결제 ID
     * @param reason 취소 사유
     */
    public void cancelPayment(String paymentId, String reason) {
        try {
            log.info("Requesting payment cancellation from PortOne API. paymentId: {}, reason: {}", paymentId, reason);

            // 1. 먼저 결제 정보 조회하여 금액 확인
            io.portone.sdk.server.payment.Payment payment = portOneClient.getPayment().getPayment(paymentId).join();

            if (payment instanceof io.portone.sdk.server.payment.Payment.Recognized) {
                io.portone.sdk.server.payment.Payment.Recognized recognizedPayment =
                    (io.portone.sdk.server.payment.Payment.Recognized) payment;

                long amount = recognizedPayment.getAmount().getTotal();

                // 2. 전액 취소
                // cancelPayment(storeId, paymentId, amount, taxFreeAmount, vatAmount, reason, requester, option, easyPayDiscountAmount, refundAccount)
                portOneClient.getPayment().cancelPayment(
                    paymentId,           // paymentId
                    amount,              // 취소 금액
                    null,                // taxFreeAmount (면세 금액)
                    null,                // vatAmount (부가세)
                    reason,              // 취소 사유
                    null,                // CancelRequester
                    null,                // PromotionDiscountRetainOption
                    null,                // easyPayDiscountAmount
                    null                 // refundAccount
                ).join();

                log.info("Successfully cancelled payment from PortOne API. amount: {}, reason: {}", amount, reason);
            } else {
                log.warn("Cannot cancel payment: Payment is not in Recognized state");
            }
        } catch (Exception e) {
            log.error("Failed to cancel payment from PortOne API. paymentId: {}, error: {}", paymentId, e.getMessage(), e);
            // 취소 실패해도 예외를 던지지 않음 (이미 검증 실패한 상태이므로)
            // 관리자가 수동으로 환불 처리해야 함
        }
    }

    /**
     * 결제 금액을 검증합니다.
     * @param paymentId PortOne 결제 ID
     * @param expectedAmount 예상 결제 금액
     * @return 금액이 일치하면 true
     */
    public boolean verifyPaymentAmount(String paymentId, Integer expectedAmount) {
        try {
            io.portone.sdk.server.payment.Payment payment = getPayment(paymentId);

            // Payment.Recognized 타입 확인
            if (!(payment instanceof io.portone.sdk.server.payment.Payment.Recognized)) {
                return false;
            }

            io.portone.sdk.server.payment.Payment.Recognized recognizedPayment =
                (io.portone.sdk.server.payment.Payment.Recognized) payment;

            Integer actualAmount = (int) recognizedPayment.getAmount().getTotal();

            return actualAmount.equals(expectedAmount);
        } catch (Exception e) {
            return false;
        }
    }
}