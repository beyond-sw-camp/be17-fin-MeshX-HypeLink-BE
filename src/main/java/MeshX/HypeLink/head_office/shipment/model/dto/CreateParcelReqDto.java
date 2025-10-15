package MeshX.HypeLink.head_office.shipment.model.dto;

import MeshX.HypeLink.head_office.item.Item;
import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateParcelReqDto {

    List<Item> items;
    String trackingNumber;


    @Builder
    public Parcel toEntity(String trackingNumber) {
        return Parcel.builder()
                .trackingNumber(trackingNumber)
                .items(this.items)
                .build();
    }
}
