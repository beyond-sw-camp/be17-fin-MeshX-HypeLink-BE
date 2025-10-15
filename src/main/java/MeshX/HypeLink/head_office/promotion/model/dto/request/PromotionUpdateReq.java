package MeshX.HypeLink.head_office.promotion.model.dto.request;

import MeshX.HypeLink.head_office.promotion.model.entity.ItemCategory;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PromotionUpdateReq {
    private PromotionType promotionType;
    private ItemCategory category;

    private String title;
    private String contents;
    private Double discountRate;    // 할인율
    private LocalDate startDate;    // 할인 시작 시정
    private LocalDate endDate;

}
