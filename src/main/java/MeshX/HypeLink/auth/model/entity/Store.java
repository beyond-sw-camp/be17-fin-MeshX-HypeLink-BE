package MeshX.HypeLink.auth.model.entity;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
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
    private Integer posCount;
    private String storeNumber;
    private StoreState storeState;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<StoreItem> storeItems;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;



    @Builder
    private Store(Double lat, Double lon, String address, Integer posCount,
                  String storeNumber, List<StoreItem> storeItems, Member member, StoreState storeState) {
        this.lat = lat;
        this.lon = lon;
        this.posCount = posCount;
        this.storeNumber = storeNumber;
        this.storeItems = storeItems;
        this.member = member;
        this.storeState = storeState != null ? storeState : StoreState.TEMP_CLOSED;
    }

    public void increasePosCount()
    {
        this.posCount++;
    }

    public void decreasePosCount()
    {
        this.posCount--;
    }

    public void updateStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public void updateLat(Double lat) {
        this.lat = lat;
    }
    public void updateLon(Double lon) {
        this.lon = lon;
    }
    public void updateStoreState(StoreState storeState) {
        this.storeState = storeState;
    }


}
