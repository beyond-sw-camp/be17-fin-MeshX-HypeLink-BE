package MeshX.HypeLink.head_office.shipment.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DriverLocationDto {
    private String driverId;
    private String name;
    private double latitude;
    private double longitude;
    private String status;  // 배송중 / 지연 / 완료

    @Builder
    private DriverLocationDto(String driverId, String name, double latitude, double longitude, String status) {
        this.driverId = driverId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }
}
