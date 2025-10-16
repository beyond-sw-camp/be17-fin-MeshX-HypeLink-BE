package MeshX.HypeLink.direct_store.order.model.dto.request;

import MeshX.HypeLink.direct_store.order.model.entity.DirectOrder;
import lombok.Getter;

@Getter
public class DirectOrderCreateReq {
    private String itemName;      // 상품명
    private Integer unitPrice;       // 단가
    private Integer quantity;        // 수량
    private String deliveryAddress;  // 납품지 주소 (매장)
    private String deliveryRequest;  // 배송 요청사항
    private String orderRequest;     // 주문 요청사항

    public DirectOrder toEntity(){
        return DirectOrder.builder()
                .itemName(itemName)
                .unitPrice(unitPrice)
                .quantity(quantity)
                .totalPrice(unitPrice * quantity)
                .deliveryAddress(deliveryAddress)
                .deliveryRequest(deliveryRequest)
                .orderRequest(orderRequest)
                .build();
    }
}
