package MeshX.HypeLink.head_office.shipment.model.entity;

import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shipment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
    private List<Parcel> parcels; // 출고 리스트

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_store_id")
    private Store toStore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_store_id")
    private Store fromStore;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
    // 배달 기사

    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus; // 출고 상태 (배송중, 배송완료, 취소, 지연)

    @Builder
    public Shipment(Integer id, List<Parcel> parcels, Store toStore, Store fromStore, Driver driver, ShipmentStatus shipmentStatus, List<Parcel> parcels1) {
        this.id = id;
        this.parcels = parcels;
        this.toStore = toStore;
        this.fromStore = fromStore;
        this.driver = driver;
        this.shipmentStatus = shipmentStatus;
        this.parcels = parcels1;
    }

    public void setFromStore(Store fromStore) {
        this.fromStore = fromStore;
    }

    public void setToStore(Store toStore) {
        this.toStore = toStore;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
