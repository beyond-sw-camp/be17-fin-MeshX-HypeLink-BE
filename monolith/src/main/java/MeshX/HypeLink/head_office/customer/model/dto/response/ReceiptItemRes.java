package MeshX.HypeLink.head_office.customer.model.dto.response;

import MeshX.HypeLink.head_office.customer.model.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReceiptItemRes {
    private Integer id;
    private Integer storeItemDetailId;
    private String productName;
    private String color;
    private String size;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    public static ReceiptItemRes toDto(OrderItem orderItem) {
        return ReceiptItemRes.builder()
                .id(orderItem.getId())
                .storeItemDetailId(orderItem.getStoreItemDetail().getId())
                .productName(orderItem.getStoreItemDetail().getItem().getKoName())
                .color(orderItem.getStoreItemDetail().getColor())
                .size(orderItem.getStoreItemDetail().getSize())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }
}
