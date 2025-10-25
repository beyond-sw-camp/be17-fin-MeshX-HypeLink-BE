package MeshX.HypeLink.head_office.promotion.model.dto.request;


import MeshX.HypeLink.head_office.promotion.model.entity.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PromotionCreateReq {
    private PromotionType promotionType;
    private PromotionStatus status;

    private String title;
    private String contents;
    private Double discountRate;    // 할인율
    private LocalDate startDate;    // 할인 시작 시정
    private LocalDate endDate;

    private ItemCategory category; // CATEGORY용
    private Integer itemId; // PRODUCT용
    private List<Integer> storeIds;  // STORE용

    //공통 Promotion 엔티티 생성
    public Promotion toEntity() {
        return Promotion.builder()
                .title(title)
                .promotionType(promotionType)
                .status(status)
                .contents(contents)
                .discountRate(discountRate)
                .startDate(startDate)
                .endDate(endDate)
                .build();

    }
}

