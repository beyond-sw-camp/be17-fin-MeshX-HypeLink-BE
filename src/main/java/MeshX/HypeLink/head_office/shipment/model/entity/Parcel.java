package MeshX.HypeLink.head_office.shipment.model.entity;

import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.head_office.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private List<Item> items = new ArrayList<>();
    // 아이템들

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;


    @Builder
    public Parcel(Integer id, String trackingNumber, List<Item> items, Shipment shipment) {
        this.id = id;
        this.trackingNumber = trackingNumber;
        this.items = items;
        this.shipment = shipment;
    }
}
