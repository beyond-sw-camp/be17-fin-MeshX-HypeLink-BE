package org.example.apiauth.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "Store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double lat;
    private Double lon;
    private Integer posCount;
    private String storeNumber;
    private StoreState storeState;

//    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
//    private List<StoreItem> storeItems;

    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Builder
    private StoreEntity(Double lat, Double lon, String address, Integer posCount,
                  String storeNumber, /*List<StoreItem> storeItems,*/ MemberEntity member, StoreState storeState) {
        this.lat = lat;
        this.lon = lon;
        this.posCount = posCount;
        this.storeNumber = storeNumber;
//        this.storeItems = storeItems;
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
