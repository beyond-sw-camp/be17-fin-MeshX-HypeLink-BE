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
    // TODO: [심각한 보안 취약점] 결제 취소 시 서버 장애 대응 로직 없음!
    // TODO: 문제 시나리오:
    //  1) cancelPayment() 호출 전 서버 다운 → 고객 결제만 되고 주문 없음
    //  2) cancelPayment() 실행 중 서버 다운 → 취소 성공했는지 알 수 없음
    //  3) DB 트랜잭션 커밋 전 서버 다운 → PortOne은 결제 완료, DB는 롤백
    //
    // TODO: 해결 방안 (실제 배포 시 필수 구현):
    //  1) 결제 상태 추적 테이블 생성 (payment_reconciliation)
    //     - paymentId, status(PENDING/COMPLETED/CANCELLED), retryCount, createdAt
    //  2) 스케줄러로 주기적 정합성 체크 (@Scheduled)
    //     - PENDING 상태가 10분 이상 된 결제 찾기
    //     - PortOne에서 실제 상태 조회
    //     - DB에 주문 없으면 자동 취소 요청
    //  3) 이벤트 아웃박스 패턴 (Event Outbox Pattern)
    //     - 취소 이벤트를 먼저 DB에 저장 (트랜잭션 안전)
    //     - 별도 워커가 이벤트 처리 및 PortOne API 호출
    //  4) PortOne 웹훅 구현
    //     - PortOne이 결제 완료 알림 보내면
    //     - 우리 DB에 주문 있는지 확인
    //     - 없으면 자동 환불 처리 스케줄링
    //
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

            // TODO: [중요] 취소 실패 시 재시도 로직 또는 알림 발송 필요
            // TODO: 현재는 예외만 던지고 끝 → 실패한 취소 요청 추적 불가능
            // TODO: 실제 배포 시:
            //  - 실패 이벤트 DB 저장
            //  - 관리자에게 슬랙/이메일 알림
            //  - 재시도 큐에 추가

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