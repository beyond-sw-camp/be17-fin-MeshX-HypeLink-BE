package MeshX.HypeLink.head_office.order.model.dto.request;

import MeshX.HypeLink.head_office.order.model.entity.HeadOrder;
import lombok.Getter;

@Getter
public class HeadOrderCreateReq {
    private String itemName;      // 상품명
    private Integer unitPrice;       // 단가
    private Integer quantity;        // 수량
    private String deliveryAddress;  // 납품지 주소 (매장)
    private String deliveryRequest;  // 배송 요청사항
    private String orderRequest;     // 주문 요청사항

    public HeadOrder toEntity(){
        return HeadOrder.builder()
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
