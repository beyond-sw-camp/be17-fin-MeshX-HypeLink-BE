package MeshX.HypeLink.head_office.order.model.dto.request;

import lombok.Getter;

@Getter
public class HeadOrderUpdateReq {
    private String itemName;      // 상품명
    private Integer unitPrice;       // 단가
    private Integer quantity;        // 수량
    private Integer totalPrice;      // 총액
    private String deliveryAddress;  // 납품지 주소 (매장)
    private String deliveryRequest;  // 배송 요청사항
    private String orderRequest;     // 주문 요청사항
}
