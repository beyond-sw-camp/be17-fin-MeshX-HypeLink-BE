package MeshX.HypeLink.direct_store.payment.service;

import MeshX.HypeLink.direct_store.payment.exception.PaymentException;
import io.portone.sdk.server.PortOneClient;
import io.portone.sdk.server.payment.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static MeshX.HypeLink.direct_store.payment.exception.PaymentExceptionMessage.PORTONE_API_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortOneService {

    private final PortOneClient portOneClient;


    //  PortOne API를 통해 결제 정보를 조회해용

    public Payment getPayment(String paymentId) {
        try {
            log.info("PortOne API 호출 시작 - paymentId: {}", paymentId);
            Payment payment = portOneClient.getPayment().getPayment(paymentId).join();
            log.info("PortOne API 호출 성공 - paymentId: {}, type: {}", paymentId, payment.getClass().getSimpleName());
            return payment;
        } catch (Exception e) {
            log.error("PortOne API 호출 실패 - paymentId: {}, error: {}", paymentId, e.getMessage(), e);
            throw new PaymentException(PORTONE_API_ERROR);
        }
    }

    // 검증 실패시 포트원 서버에 취소 요청을 내리는 메서드
    public void cancelPayment(String paymentId, String reason) {
        try {
            log.warn("결제 취소 시작 - paymentId: {}, reason: {}", paymentId, reason);

            //  전액 취소
            portOneClient.getPayment().cancelPayment(
                paymentId,           // paymentId
                null,                // 취소 금액
                null,                // taxFreeAmount (면세 금액)
                null,                // vatAmount (부가세)
                reason,              // 취소 사유
                null,                // CancelRequester
                null,                // PromotionDiscountRetainOption
                null,                // easyPayDiscountAmount
                null                 // refundAccount
            ).join();

            log.info("결제 취소 성공 - paymentId: {}", paymentId);

            // TODO: 취소 성공 시 별도 테이블에 기록 (추후 정합성 체크용)
        } catch (Exception e) {
            log.error("결제 취소 실패 - paymentId: {}, error: {}", paymentId, e.getMessage(), e);



            throw new RuntimeException("결제 취소 실패: " + e.getMessage(), e);
        }
    }
    // TODO: [사용되지 않는 코드] 이 메서드는 현재 어디서도 호출하지 않음
    // TODO: PaymentService.validatePaymentAmount()가 대신 사용 중
    // TODO: 실제 배포 시 삭제하거나, 필요하면 리팩토링해서 사용할 것
    // 검증 로직
    public boolean verifyPaymentAmount(String paymentId, Integer expectedAmount) {
        try {
            Payment payment = getPayment(paymentId);

            if (!(payment instanceof Payment.Recognized)) {
                return false;
            }

            Payment.Recognized recognizedPayment = (Payment.Recognized) payment;

            Integer actualAmount = (int) recognizedPayment.getAmount().getTotal();

            return actualAmount.equals(expectedAmount);
        } catch (Exception e) {
            return false;
        }
    }
}