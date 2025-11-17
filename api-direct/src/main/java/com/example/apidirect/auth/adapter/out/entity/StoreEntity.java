package com.example.apidirect.auth.adapter.out.entity;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreEntity extends BaseEntity {
    @Id
    private Integer id;

    private Double lat;
    private Double lon;
    private Integer posCount;

    @Column(unique = true)
    private String storeNumber;

    @Column(nullable = false)
    private String storeState;

    @Column(name = "member_id")
    private Integer memberId;

    @Builder
    private StoreEntity(Integer id, Double lat, Double lon, Integer posCount,
                       String storeNumber, String storeState, Integer memberId) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.posCount = posCount;
        this.storeNumber = storeNumber;
        this.storeState = storeState != null ? storeState : "TEMP_CLOSED";
        this.memberId = memberId;
    }

    public void increasePosCount() {
        this.posCount++;
    }

    public void decreasePosCount() {
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

    public void updateStoreState(String storeState) {
        this.storeState = storeState;
    }
}
