package MeshX.HypeLink.direct_store.payment.model.dto.response;

import MeshX.HypeLink.head_office.customer.model.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReceiptItemRes {
    private Integer id;
    private Integer storeItemDetailId;  // StoreItemDetail ID
    private String productName;
    private String color;               // 색상 추가
    private String size;                // 사이즈 추가
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    public static ReceiptItemRes toDto(OrderItem item) {
        return ReceiptItemRes.builder()
                .id(item.getId())
                .storeItemDetailId(item.getStoreItemDetail().getId())
                .productName(item.getStoreItemDetail().getItem().getKoName())
                .color(item.getStoreItemDetail().getColor())
                .size(item.getStoreItemDetail().getSize())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .build();
    }
}
