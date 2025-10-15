package MeshX.HypeLink.head_office.promotion.model.entity;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.direct_store.item.DirectItem;
import MeshX.HypeLink.head_office.store.model.entity.StoreMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String contents;
    private Double discountRate;    // 할인율

    private LocalDate startDate;    // 할인 시작 시정
    private LocalDate endDate;      // 할인 종료 시점

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PromotionType promotionType; //이벤트 종류

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;  // (가맹점 참조)

    @Enumerated(EnumType.STRING)
    private ItemCategory category;  // 품목

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private DirectItem item;           // (상품참조)

    @Builder
    private Promotion(
            String title,
            String contents,
            Double discountRate,
            LocalDate startDate,
            LocalDate endDate,
            PromotionType promotionType,
            ItemCategory category,
            Store store,
            DirectItem item
    ) {
        this.title = title;
        this.contents = contents;
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.promotionType = promotionType;
        this.category = category;
        this.store = store;
        this.item = item;
    }

    public void updatePromotionType(PromotionType promotionType){
        this.promotionType = promotionType;
    }

    public void updateCategory(ItemCategory category){
        this.category = category;
    }

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


}
