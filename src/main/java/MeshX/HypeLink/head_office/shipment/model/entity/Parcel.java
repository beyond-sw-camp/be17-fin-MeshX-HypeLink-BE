package MeshX.HypeLink.head_office.shipment.model.entity;

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
public class Parcel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String trackingNumber;
    // 송장 번호

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parcel")
    private List<ParcelItem> parcelItems;

    @OneToOne(mappedBy = "parcel")
    private Shipment shipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private Store requester;       // 발주를 요청한 본사 or 매장

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Store supplier;        // 발주를 수락하고 공급하는 쪽

    @Builder
    private Parcel(String trackingNumber, List<ParcelItem> parcelItems, Shipment shipment, Store requester,
                   Store supplier) {
        this.trackingNumber = trackingNumber;
        this.parcelItems = parcelItems;
        this.shipment = shipment;
        this.requester = requester;
        this.supplier = supplier;
    }
}
