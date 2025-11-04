package MeshX.HypeLink.head_office.order.model.dto.response;

import MeshX.HypeLink.head_office.order.model.entity.PurchaseDetailStatus;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import MeshX.HypeLink.utils.datechanger.DateChanger;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.Optional;

@Getter
@Builder
public class PurchaseOrderInfoDetailRes {
    private Integer id;
    private String itemName;      // 상품명
    private String itemDetailCode;
    private Integer quantity;        // 수량
    private String requestDay;
    private String detailState;
    private String status;
    private String deliveryAddress;  // 납품지 주소 (매장)
    private String deliveryRequest;  // 배송 요청사항

    public static  PurchaseOrderInfoDetailRes toDto(PurchaseOrder entity){
        return PurchaseOrderInfoDetailRes.builder()
                .id(entity.getId())
                .itemName(entity.getItemDetail().getItem().getKoName())
                .itemDetailCode(entity.getItemDetail().getItemDetailCode())
                .quantity(entity.getQuantity())
                .detailState(
                        Optional.ofNullable(entity.getPurchaseDetailStatus())
                                .map(PurchaseDetailStatus::getDescription)
                                .orElse(null)
                )
                .requestDay(DateChanger.toKoreanDate(entity.getCreatedAt().toString()))
                .status(entity.getPurchaseOrderState().getDescription())
                .deliveryAddress(entity.getSupplier().getName())
                .deliveryRequest(entity.getRequester().getName())
                .build();
    }

    public static Page<PurchaseOrderInfoDetailRes> toDtoPage(Page<PurchaseOrder> page){
        return page.map(PurchaseOrderInfoDetailRes::toDto);
    }
}
