package MeshX.HypeLink.head_office.shipment.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DriverGpsDto {
    private String driverId;
    private double lat;
    private double lng;
    private boolean isFirst;
}
