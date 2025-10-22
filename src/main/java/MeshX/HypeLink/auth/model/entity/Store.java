package MeshX.HypeLink.auth.model.entity;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionStore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double lat;
    private Double lon;
    private String address;
    private Integer posCount;
    private String storeNumber;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<StoreItem> storeItems;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromotionStore> promotionStores = new ArrayList<>();


    @Builder
    private Store(Double lat, Double lon, String address, Integer posCount,
                  String storeNumber, List<StoreItem> storeItems, Member member) {
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.posCount = posCount;
        this.storeNumber = storeNumber;
        this.storeItems = storeItems;
        this.member = member;
    }

    public void addPromotion(Promotion promotion) {
        PromotionStore.link(promotion, this);
    }
}
