package MeshX.HypeLink.direct_store.posOrder.model.dto.request;

import MeshX.HypeLink.direct_store.posOrder.model.entity.PosOrderItem;
import lombok.Getter;

@Getter
public class PosOrderItemDto {
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Integer unitPrice;
    private Integer discountPrice;
    private Integer subtotal;

    public PosOrderItem toEntity() {
        return PosOrderItem.builder()
                .productId(productId)
                .productName(productName)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .discountPrice(discountPrice)
                .subtotal(subtotal)
                .build();
    }
}