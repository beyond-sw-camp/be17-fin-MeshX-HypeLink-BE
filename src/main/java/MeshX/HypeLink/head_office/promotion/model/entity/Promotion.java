package MeshX.HypeLink.head_office.promotion.model.entity;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String contents;
    private Coupon coupon;


    private LocalDate startDate;    // 할인 시작 시정
    private LocalDate endDate;      // 할인 종료 시점

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PromotionType promotionType; //이벤트 종류

    @Enumerated(EnumType.STRING)
    private PromotionStatus status;

    @Builder
    private Promotion(
            String title,
            String contents,
            Double discountRate,
            LocalDate startDate,
            LocalDate endDate,
            PromotionType promotionType,
            PromotionStatus status
    ) {
        this.title = title;
        this.contents = contents;
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.promotionType = promotionType;
        this.status = status;
    }

    public void updatePromotionType(PromotionType promotionType){
        this.promotionType = promotionType;
    }

//    public void updateCategory(ItemCategory category){
//        this.category = category;
//    }

    public void updateTitle(String title){
        this.title = title;
    }
    public void updateContents(String contents){
        this.contents = contents;
    }

    public void updateDiscountRate(Double discountRate){
        this.discountRate = discountRate;
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


    public void autoUpdateStatus() {
        // ✅ 관리자가 수동 변경한 경우, 자동 갱신 안 함
        if (this.status == PromotionStatus.ENDED) return;

        LocalDate now = LocalDate.now();
        if (now.isBefore(startDate)) this.status = PromotionStatus.UPCOMING;
        else if (now.isAfter(endDate)) this.status = PromotionStatus.ENDED;
        else this.status = PromotionStatus.ONGOING;
    }

    public void addStore(Store store) {
        PromotionStore.link(this, store);
    }


}
