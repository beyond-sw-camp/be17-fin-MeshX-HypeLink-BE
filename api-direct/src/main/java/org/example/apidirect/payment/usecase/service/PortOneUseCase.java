package org.example.apidirect.payment.usecase.service;

import io.portone.sdk.server.PortOneClient;
import io.portone.sdk.server.payment.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.payment.common.PaymentException;
import org.springframework.stereotype.Service;

import static org.example.apidirect.payment.common.PaymentExceptionType.PORTONE_API_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortOneUseCase {

    private final PortOneClient portOneClient;

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

            // 전액 취소
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


        } catch (Exception e) {
            log.error("결제 취소 실패 - paymentId: {}, error: {}", paymentId, e.getMessage(), e);

            throw new RuntimeException("결제 취소 실패: " + e.getMessage(), e);
        }
    }
}
