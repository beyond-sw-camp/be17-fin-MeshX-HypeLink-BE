package MeshX.HypeLink.head_office.shipment_driver.model.entity;

import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentDriver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "shipmentDriver", cascade = CascadeType.ALL)
    private List<Shipment> shipments;

    @ManyToOne
    @JoinColumn(name = "delivery_zone_id")
    private DeliveryZone deliveryZone;
}
