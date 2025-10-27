package MeshX.HypeLink.direct_store.payment.model.dto.request;

import lombok.Getter;

@Getter
public class PaymentValidationReq {
    private String paymentId;        // PortOne 결제 ID
    private ReceiptCreateReq orderData;  // 영수증 데이터
}