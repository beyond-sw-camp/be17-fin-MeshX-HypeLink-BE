package MeshX.HypeLink.head_office.customer.model.dto.response;

import MeshX.HypeLink.head_office.customer.model.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReceiptItemRes {
    private Integer id;
    private String productName;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    public static ReceiptItemRes toDto(OrderItem orderItem) {
        return ReceiptItemRes.builder()
                .id(orderItem.getId())
                .productName(orderItem.getStoreItem().getKoName())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }
}
