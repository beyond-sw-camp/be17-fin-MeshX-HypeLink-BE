package MeshX.HypeLink.direct_store.payment.model.dto.request;

import lombok.Getter;

// TODO: [보안 취약점] 현재 프론트에서 가격 정보를 받고 있음 (프로토타입용)
// TODO: 실제 배포 시 productId와 quantity만 받고, 가격은 서버에서 DB 조회해야 함!
@Getter
public class ReceiptItemDto {
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Integer unitPrice;      // TODO: 삭제 필요 - 서버에서 DB 조회해야 함
    private Integer subtotal;       // TODO: 삭제 필요 - 서버에서 계산해야 함
}
