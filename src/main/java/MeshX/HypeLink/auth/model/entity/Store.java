package MeshX.HypeLink.auth.model.entity;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
