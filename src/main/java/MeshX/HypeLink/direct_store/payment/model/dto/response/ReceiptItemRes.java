package MeshX.HypeLink.direct_store.payment.model.dto.response;

import MeshX.HypeLink.head_office.customer.model.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReceiptItemRes {
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    public static ReceiptItemRes toDto(OrderItem item) {
        return ReceiptItemRes.builder()
                .id(item.getId())
                .productId(item.getStoreItem().getId())
                .productName(item.getStoreItem().getKoName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .build();
    }
}
