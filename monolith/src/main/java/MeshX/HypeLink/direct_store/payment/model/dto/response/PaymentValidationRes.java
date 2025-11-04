package MeshX.HypeLink.direct_store.payment.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentValidationRes {
    private Boolean success;
    private String receiptId;
    private String merchantUid;
    private Integer finalAmount;
    private String message;
    private ReceiptDetailRes receiptDetail;

    @Builder
    public PaymentValidationRes(Boolean success, String receiptId, String merchantUid,
                                 Integer finalAmount, String message, ReceiptDetailRes receiptDetail) {
        this.success = success;
        this.receiptId = receiptId;
        this.merchantUid = merchantUid;
        this.finalAmount = finalAmount;
        this.message = message;
        this.receiptDetail = receiptDetail;
    }
}