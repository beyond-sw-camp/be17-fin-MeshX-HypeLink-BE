package MeshX.HypeLink.direct_strore.order.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectOrder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String itemName;      // 상품명
    private Integer unitPrice;       // 단가
    private Integer quantity;        // 수량
    private Integer totalPrice;      // 총액
    private String deliveryAddress;  // 납품지 주소 (매장)
    private String deliveryRequest;  // 배송 요청사항
    private String orderRequest;     // 주문 요청사항

    @Builder
    private DirectOrder(String itemName, Integer unitPrice, Integer quantity, Integer totalPrice, String deliveryAddress, String deliveryRequest, String orderRequest) {
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.deliveryRequest = deliveryRequest;
        this.orderRequest = orderRequest;
    }

    public void updateItemName(String itemName){
        this.itemName = itemName;
    }

    public void updateUnitPrice(Integer unitPrice){
        this.unitPrice = unitPrice;
    }

    public void updateQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public void updateTotalPrice(Integer totalPrice){
        this.totalPrice = totalPrice;
    }

    public void updateDeliveryAddress(String deliveryAddress){
        this.deliveryAddress = deliveryAddress;
    }

    public void updateDeliveryRequest(String deliveryRequest){
        this.deliveryRequest = deliveryRequest;
    }

    public void updateOrderRequest(String orderRequest){
        this.orderRequest = orderRequest;
    }
}

