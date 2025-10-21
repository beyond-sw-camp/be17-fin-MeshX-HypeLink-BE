package MeshX.HypeLink.direct_store.order.model.dto.response;

import MeshX.HypeLink.direct_store.order.model.entity.DirectOrder;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class DirectOrderInfoRes {
    private String itemName;      // 상품명
    private Integer unitPrice;       // 단가
    private Integer quantity;        // 수량
    private Integer totalPrice;      // 총액
    private String deliveryAddress;  // 납품지 주소 (매장)
    private String deliveryRequest;  // 배송 요청사항
    private String orderRequest;     // 주문 요청사항

    public static DirectOrderInfoRes toDto(DirectOrder entity){
        return DirectOrderInfoRes.builder()
                .itemName(entity.getItemName())
                .unitPrice(entity.getUnitPrice())
                .quantity(entity.getQuantity())
                .totalPrice(entity.getTotalPrice())
                .deliveryAddress(entity.getDeliveryAddress())
                .deliveryRequest(entity.getDeliveryRequest())
                .orderRequest(entity.getOrderRequest())
                .build();
    }

    @Builder
    private DirectOrderInfoRes(String itemName, Integer unitPrice, Integer quantity, Integer totalPrice, String deliveryAddress, String deliveryRequest, String orderRequest){
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.deliveryRequest = deliveryRequest;
        this.orderRequest = orderRequest;
    }
    public static Page<DirectOrderInfoRes> toDtoPage(Page<DirectOrder> page) {
        return page.map(DirectOrderInfoRes::toDto);
    }
}
