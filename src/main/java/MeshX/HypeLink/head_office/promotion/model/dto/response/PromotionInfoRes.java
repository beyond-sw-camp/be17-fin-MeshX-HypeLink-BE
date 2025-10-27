package MeshX.HypeLink.head_office.promotion.model.dto.response;

import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

@Getter
public class PromotionInfoRes {
    private Integer id;
    private String title;
    private String contents;
    private LocalDate startDate;    // 할인 시작 시정
    private LocalDate endDate;      // 할인 종료 시점
    private PromotionStatus status;

    private String couponType;
    private String couponName;
    private Integer couponId;

    public static PromotionInfoRes toDto(Promotion entity) {
        Coupon coupon = entity.getCoupon();

        return PromotionInfoRes.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .couponType(coupon.getCouponType().name())
                .couponName(coupon.getName())
                .couponId(coupon.getId())
                .build();
    }

     @Builder
    private PromotionInfoRes(
                Integer id,
                String title,
                String contents,
                LocalDate startDate,
                LocalDate endDate,
                PromotionStatus status,
                String couponType
                ,String couponName
                , Integer couponId
                ) {
            this.id = id;
            this.title = title;
            this.contents = contents;
            this.startDate = startDate;
            this.endDate = endDate;
            this.status = status;
            this.couponType = couponType;
            this.couponName = couponName;
            this.couponId = couponId;
        }

    public static Page<PromotionInfoRes> toDtoPage(Page<Promotion>  page) {
        return page.map(PromotionInfoRes::toDto);
    }

}
