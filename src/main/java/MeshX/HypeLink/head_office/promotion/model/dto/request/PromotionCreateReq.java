package MeshX.HypeLink.head_office.promotion.model.dto.request;


import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import MeshX.HypeLink.head_office.promotion.model.entity.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PromotionCreateReq {
    private PromotionStatus status;

    private String title;
    private String contents;
    private LocalDate startDate;    // 할인 시작 시정
    private LocalDate endDate;

    private Integer couponId;

    //공통 Promotion 엔티티 생성
    public Promotion toEntity(Coupon coupon) {
        return Promotion.builder()
                .title(title)
                .status(status)
                .contents(contents)
                .startDate(startDate)
                .endDate(endDate)
                .coupon(coupon)
                .build();

    }
}

