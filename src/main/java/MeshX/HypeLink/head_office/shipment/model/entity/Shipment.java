package MeshX.HypeLink.head_office.shipment.model.entity;

import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shipment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcel_id")
    private Parcel parcel; // 출고 리스트

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
    // 배달 기사

    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus; // 출고 상태 (배송중, 배송완료, 취소, 지연)

    @Builder
    private Shipment(Parcel parcel, Driver driver, ShipmentStatus shipmentStatus) {
        this.parcel = parcel;
        this.driver = driver;
        this.shipmentStatus = shipmentStatus;
    }

    public void updateDriver(Driver driver) {
        this.driver = driver;
    }

    public void updateShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }
}
