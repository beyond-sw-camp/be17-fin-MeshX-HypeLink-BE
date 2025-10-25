package MeshX.HypeLink.head_office.promotion.model.dto.request;

import MeshX.HypeLink.head_office.promotion.model.entity.ItemCategory;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionStatus;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionType;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PromotionUpdateReq {
    private PromotionType promotionType;
    private PromotionStatus status;

    private Integer id;
    private String title;
    private String contents;
    private Double discountRate;    // 할인율
    private LocalDate startDate;    // 할인 시작 시정
    private LocalDate endDate;

    private List<Integer> storeIds; // STORE용
    private Integer itemId;         // PRODUCT용
    private ItemCategory category;  // CATEGORY용

}
