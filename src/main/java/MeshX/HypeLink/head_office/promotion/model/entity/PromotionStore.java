package MeshX.HypeLink.head_office.promotion.model.entity;

import MeshX.HypeLink.auth.model.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PromotionStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    private PromotionStore(Promotion promotion, Store store) {
        this.promotion = promotion;
        this.store = store;
    }

    public static PromotionStore toEntity(Promotion promotion, Store store) {
        return PromotionStore.builder()
                .promotion(promotion)
                .store(store)
                .build();
    }

    public static PromotionStore link(Promotion promotion, Store store) {
        PromotionStore link = PromotionStore.builder()
                .promotion(promotion)
                .store(store)
                .build();
        promotion.getPromotionStores().add(link);
        store.getPromotionStores().add(link);
        return link;
    }

}
