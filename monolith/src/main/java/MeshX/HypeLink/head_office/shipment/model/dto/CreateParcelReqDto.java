package MeshX.HypeLink.head_office.shipment.model.dto;

import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import MeshX.HypeLink.head_office.shipment.model.entity.ParcelItem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateParcelReqDto {

    List<ParcelItem> items;
    String trackingNumber;


    @Builder
    public Parcel toEntity(String trackingNumber) {
        return Parcel.builder()
                .trackingNumber(trackingNumber)
                .parcelItems(this.items)
                .build();
    }
}
