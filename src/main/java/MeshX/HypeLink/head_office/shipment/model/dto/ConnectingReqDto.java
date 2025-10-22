package MeshX.HypeLink.head_office.shipment.model.dto;

import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import MeshX.HypeLink.head_office.shipment.model.entity.ShipmentStatus;
import lombok.Getter;

import java.util.List;

import static MeshX.HypeLink.head_office.shipment.model.entity.ShipmentStatus.IN_PROGRESS;

@Getter
public class ConnectingReqDto {
    private List<String> trackingNumber;

    private Driver driver;
    private Store toStore;
    private Store fromStore;


    public Shipment toEntity(List<Parcel> parcels){
        return Shipment.builder()
                .parcel(parcels.get(0))
                .driver(driver)
                .shipmentStatus(IN_PROGRESS)
                .build();
    }
}
