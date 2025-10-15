package MeshX.HypeLink.head_office.shipment.model.entity;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private List<ParcelItem> parcelItems;
    // 아이템들

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;


    @Builder
    public Parcel(Integer id, String trackingNumber, List<ParcelItem> parcelItems, Shipment shipment) {
        this.id = id;
        this.trackingNumber = trackingNumber;
        this.parcelItems = parcelItems;
        this.shipment = shipment;
    }
}
