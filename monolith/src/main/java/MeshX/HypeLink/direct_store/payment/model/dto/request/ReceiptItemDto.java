package MeshX.HypeLink.direct_store.payment.model.dto.request;

import lombok.Getter;


@Getter
public class ReceiptItemDto {
    private Integer storeItemDetailId;  // StoreItemDetail ID (색상/사이즈 포함)
    private String productName;
    private Integer quantity;
    private Integer unitPrice;
    private Integer subtotal;
}
