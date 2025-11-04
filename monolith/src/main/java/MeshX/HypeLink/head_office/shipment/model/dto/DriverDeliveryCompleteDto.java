package MeshX.HypeLink.head_office.shipment.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DriverDeliveryCompleteDto {
    private String driverId;
    private double lat;
    private double lng;
    private String pointId;  // 배송 완료 지점 ID
}
