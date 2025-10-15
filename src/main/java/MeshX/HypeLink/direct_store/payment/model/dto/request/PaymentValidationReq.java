package MeshX.HypeLink.direct_store.payment.model.dto.request;

import MeshX.HypeLink.direct_store.pos.payment.model.entity.PaymentMethod;
import MeshX.HypeLink.direct_store.pos.payment.model.entity.PaymentStatus;
import MeshX.HypeLink.direct_store.pos.payment.model.entity.PosPayment;
import MeshX.HypeLink.direct_store.pos.posOrder.model.dto.request.PosOrderCreateReq;
import MeshX.HypeLink.direct_store.pos.posOrder.model.entity.PosOrder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentValidationReq {
    private String paymentId;        // PortOne 결제 ID
    private PosOrderCreateReq orderData;  // 주문 데이터

    public PosPayment toPaymentEntity(PosOrder order, String transactionId, String storeId,
                                      String channelKey, Integer amount) {
        return PosPayment.builder()
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