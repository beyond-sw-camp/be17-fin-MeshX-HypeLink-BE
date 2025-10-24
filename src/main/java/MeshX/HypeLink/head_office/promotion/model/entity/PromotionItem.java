package MeshX.HypeLink.head_office.promotion.model.entity;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PromotionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private StoreItem item;


    @Builder
    private PromotionItem(Promotion promotion, StoreItem item) {
        this.promotion = promotion;
        this.item = item;
    }

}
