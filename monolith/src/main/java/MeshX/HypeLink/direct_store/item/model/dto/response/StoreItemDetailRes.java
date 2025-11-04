package MeshX.HypeLink.direct_store.item.model.dto.response;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreItemDetailRes {
    private Integer id; // StoreItemDetail의 ID
    private Integer storeItemId; // StoreItem의 ID 추가
    private String itemDetailCode;  // 바코드
    private String productName;     // "베이직 티셔츠 블랙/M"
    private String color;
    private String colorCode;
    private String size;
    private Integer stock;
    private Integer price;          // 판매 가격
    private String category;
    private String itemCode;        // 상품 코드

    public static StoreItemDetailRes toDto(StoreItemDetail detail) {
        String productName = detail.getItem().getKoName() + " " +
                           detail.getColor() + "/" + detail.getSize();

        return StoreItemDetailRes.builder()
                .id(detail.getId())
                .storeItemId(detail.getItem().getId()) // StoreItem의 ID를 여기에 추가
                .itemDetailCode(detail.getItemDetailCode())
                .productName(productName)
                .color(detail.getColor())
                .colorCode(detail.getColorCode())
                .size(detail.getSize())
                .stock(detail.getStock())
                .price(detail.getItem().getAmount())
                .category(detail.getItem().getCategory() != null ?
                         detail.getItem().getCategory().getCategory() : null)
                .itemCode(detail.getItem().getItemCode())
                .build();
    }
}
