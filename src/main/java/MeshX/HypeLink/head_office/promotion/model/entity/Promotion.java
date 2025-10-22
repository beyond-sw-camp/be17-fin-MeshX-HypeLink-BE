package MeshX.HypeLink.head_office.promotion.model.entity;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
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

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true) //변경, 삭제 시 전파
    private List<PromotionStore> promotionStores = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ItemCategory category;  // 품목

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private StoreItem item;           // (상품참조)

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
            ItemCategory category,
            PromotionStatus status,
            StoreItem item
    ) {
        this.title = title;
        this.contents = contents;
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.promotionType = promotionType;
        this.category = category;
        this.status = status;
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

    public  void updateStatus(PromotionStatus status){
        this.status = status;
    }


    public void autoUpdateStatus() {//상태 자동 갱신
        LocalDate now = LocalDate.now();
        if (now.isBefore(startDate)) this.status = PromotionStatus.UPCOMING;
        else if (now.isAfter(endDate)) this.status = PromotionStatus.ENDED;
        else this.status = PromotionStatus.ONGOING;
    }

    public void addStore(Store store) {
        PromotionStore.link(this, store);
    }


}
