package MeshX.HypeLink.direct_store.payment.model.dto.request;

import MeshX.HypeLink.direct_store.payment.model.entity.PaymentMethod;
import MeshX.HypeLink.direct_store.payment.model.entity.PaymentStatus;
import MeshX.HypeLink.direct_store.payment.model.entity.Payment;
import MeshX.HypeLink.direct_store.posOrder.model.dto.request.PosOrderCreateReq;
import MeshX.HypeLink.direct_store.posOrder.model.entity.PosOrder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentValidationReq {
    private String paymentId;        // PortOne 결제 ID
    private PosOrderCreateReq orderData;  // 주문 데이터

    public Payment toPaymentEntity(PosOrder order, String transactionId, String storeId,
                                   String channelKey, Integer amount) {
        return Payment.builder()
                .order(order)
                .paymentId(paymentId)
                .transactionId(transactionId)
                .storeId(storeId)
                .channelKey(channelKey)
                .paymentMethod(PaymentMethod.PORTONE)
                .amount(amount)
                .status(PaymentStatus.PAID)
                .paidAt(LocalDateTime.now())
                .build();
    }
}