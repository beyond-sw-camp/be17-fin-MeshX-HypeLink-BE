package MeshX.HypeLink.head_office.promotion.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)  // ✅ 하나의 쿠폰만 연결
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    private LocalDate startDate;    // 프로모션 하는날
    private LocalDate endDate;      // 프로모션 종료일

    @Enumerated(EnumType.STRING)
    private PromotionStatus status;

    @Builder
    private Promotion(
            String title,
            String contents,
            LocalDate startDate,
            LocalDate endDate,
            PromotionStatus status,
            Coupon coupon
            ) {
        this.title = title;
        this.contents = contents;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.coupon = coupon;
    }


    public void updateTitle(String title){
        this.title = title;
    }
    public void updateContents(String contents){
        this.contents = contents;
    }


    public void updateStartDate(LocalDate startDate){
        this.startDate = startDate;
    }

    public void updateEndDate(LocalDate endDate){
        this.endDate = endDate;
    }

    public  void updateStatus(PromotionStatus status){
        this.status = status;
    }

    public void updateCoupon(Coupon coupon){
        this.coupon = coupon;
    }




    public void autoUpdateStatus() {
        // ✅ 관리자가 수동 변경한 경우, 자동 갱신 안 함
        if (this.status == PromotionStatus.ENDED) return;

        LocalDate now = LocalDate.now();
        if (now.isBefore(startDate)) this.status = PromotionStatus.UPCOMING;
        else if (now.isAfter(endDate)) this.status = PromotionStatus.ENDED;
        else this.status = PromotionStatus.ONGOING;
    }


}
