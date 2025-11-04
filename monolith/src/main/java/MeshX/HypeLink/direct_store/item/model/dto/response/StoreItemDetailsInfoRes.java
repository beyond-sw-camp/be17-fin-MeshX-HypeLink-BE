package MeshX.HypeLink.direct_store.item.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreItemDetailsInfoRes {
    private Integer id;
    private String itemKoName;      // 상품 한글 명
    private String itemEnName;      // 상품 영어 명
    private String category;
    private String color;
    private String colorCode;
    private String itemCode;        // 상품 코드
    private String itemDetailCode;  // 상품 상세 코드
    private Integer stock;
    private Integer totalQuantity;       // 수량

    @QueryProjection
    public StoreItemDetailsInfoRes(Integer id, String itemKoName, String itemEnName, String category,
                                   String color, String colorCode, String itemCode, String itemDetailCode,
                                   Integer stock, int totalQuantity) {
        this.id = id;
        this.itemKoName = itemKoName;
        this.itemEnName = itemEnName;
        this.category = category;
        this.color = color;
        this.colorCode = colorCode;
        this.itemCode = itemCode;
        this.itemDetailCode = itemDetailCode;
        this.stock = stock;
        this.totalQuantity = totalQuantity;
    }
}
