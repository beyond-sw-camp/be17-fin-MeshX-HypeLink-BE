package MeshX.HypeLink.direct_store.posOrder.model.dto.response;

import MeshX.HypeLink.direct_store.posOrder.model.entity.PosOrderItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PosOrderItemRes {
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Integer unitPrice;
    private Integer discountPrice;
    private Integer subtotal;

    public static PosOrderItemRes toDto(PosOrderItem item) {
        return PosOrderItemRes.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .productName(item.getProductName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .discountPrice(item.getDiscountPrice())
                .subtotal(item.getSubtotal())
                .build();
    }
}