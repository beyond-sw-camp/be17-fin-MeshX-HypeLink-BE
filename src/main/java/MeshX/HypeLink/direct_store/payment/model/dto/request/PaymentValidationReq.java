package MeshX.HypeLink.direct_store.payment.model.dto.request;

import lombok.Getter;

@Getter
public class PaymentValidationReq {
    private String name;             // 멤버 이름
    private String paymentId;        // PortOne 결제 ID
    private ReceiptCreateReq orderData;  // 영수증 데이터
}