package MeshX.HypeLink.direct_store.payment.model.dto.response;

import MeshX.HypeLink.direct_store.pos.posOrder.model.dto.response.PosOrderDetailRes;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentValidationRes {
    private Boolean success;
    private String orderId;
    private String orderNumber;
    private Integer finalAmount;
    private String message;
    private PosOrderDetailRes orderDetail;

    @Builder
    public PaymentValidationRes(Boolean success, String orderId, String orderNumber,
                                 Integer finalAmount, String message, PosOrderDetailRes orderDetail) {
        this.success = success;
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.finalAmount = finalAmount;
        this.message = message;
        this.orderDetail = orderDetail;
    }
}