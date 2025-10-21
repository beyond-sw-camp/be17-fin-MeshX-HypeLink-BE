package MeshX.HypeLink.head_office.order.model.dto.response;

import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PurchaseOrderInfoDetailRes {
    private Integer id;
    private String itemName;      // 상품명
    private Integer quantity;        // 수량
    private String requestDay;
    private String status;
    private String deliveryAddress;  // 납품지 주소 (매장)
    private String deliveryRequest;  // 배송 요청사항

    public static  PurchaseOrderInfoDetailRes toDto(PurchaseOrder entity){
        return PurchaseOrderInfoDetailRes.builder()
                .id(entity.getId())
                .itemName(entity.getItem().getKoName())
                .quantity(entity.getQuantity())
                .requestDay(entity.getCreatedAt().toString())
                .status(entity.getPurchaseOrderState().getDescription())
                .deliveryAddress(entity.getSupplier().getName())
                .deliveryRequest(entity.getRequester().getName())
                .build();
    }

    @Builder
    private PurchaseOrderInfoDetailRes(Integer id, String itemName, Integer quantity, String requestDay,
                                       String status, String deliveryAddress, String deliveryRequest) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.requestDay = requestDay;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.deliveryRequest = deliveryRequest;
    }

    public static Page<PurchaseOrderInfoDetailRes> toDtoPage(Page<PurchaseOrder> page){
        return page.map(PurchaseOrderInfoDetailRes::toDto);
    }
}
