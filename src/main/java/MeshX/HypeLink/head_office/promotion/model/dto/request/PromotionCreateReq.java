package MeshX.HypeLink.head_office.promotion.model.dto.request;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.head_office.promotion.model.entity.ItemCategory;
import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionStatus;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionType;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PromotionCreateReq {
    private PromotionType promotionType;
    private ItemCategory category;
    private PromotionStatus status;

    private List<Integer> storeIds;
    private Integer itemId; // 외래키용 필드 추가

    private String title;
    private String contents;
    private Double discountRate;    // 할인율
    private LocalDate startDate;    // 할인 시작 시정
    private LocalDate endDate;

    public Promotion toEntity() {
        return Promotion.builder()
                .promotionType(promotionType)
                .category(category)
                .status(status)
                .item(promotionType == PromotionType.PRODUCT && itemId != null
                                ? StoreItem.builder().build()
                                : null)
                .title(title)
                .contents(contents)
                .discountRate(discountRate)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
