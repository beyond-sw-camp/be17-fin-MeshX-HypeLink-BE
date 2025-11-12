package MeshX.HypeLink.direct_store.payment.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemData {
    private Integer id;
    private Integer customerReceiptId;
    private Integer storeItemDetailId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
}
