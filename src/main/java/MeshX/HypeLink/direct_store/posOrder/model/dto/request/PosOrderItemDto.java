package MeshX.HypeLink.direct_store.posOrder.model.dto.request;

import MeshX.HypeLink.direct_store.posOrder.model.entity.PosOrderItem;
import lombok.Getter;

// TODO: [보안 취약점] 현재 프론트에서 가격 정보를 받고 있음 (프로토타입용)
// TODO: 실제 배포 시 productId와 quantity만 받고, 가격은 서버에서 DB 조회해야 함!
// TODO: 수정 후 DTO 구조:
//  @Getter
//  public class PosOrderItemDto {
//      private Integer productId;   // 상품 ID만
//      private Integer quantity;    // 수량만
//      // unitPrice, discountPrice, subtotal 필드 삭제
//  }
@Getter
public class PosOrderItemDto {
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Integer unitPrice;      // TODO: 삭제 필요 - 서버에서 DB 조회해야 함
    private Integer discountPrice;  // TODO: 삭제 필요 - 서버에서 계산해야 함
    private Integer subtotal;       // TODO: 삭제 필요 - 서버에서 계산해야 함

    public PosOrderItem toEntity() {
        return PosOrderItem.builder()
                .productId(productId)
                .productName(productName)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .discountPrice(discountPrice)
                .subtotal(subtotal)
                .build();
    }
}