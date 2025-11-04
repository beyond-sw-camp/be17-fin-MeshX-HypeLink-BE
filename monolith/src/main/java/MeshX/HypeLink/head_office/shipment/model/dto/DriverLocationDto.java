package MeshX.HypeLink.head_office.shipment.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DriverLocationDto {
    private String driverId;
    private String name;
    private String from;    // 출발지
    private String to;      // 목적지
    private String item;    // 배송 품목
    private Integer qty;    // 수량
    private Double latitude;
    private Double longitude;
    private String status;  // 배송중 / 지연 / 완료

    @Builder
    private DriverLocationDto(String driverId, String name, String from, String to,
                              String item, Integer qty, Double latitude, Double longitude,
                              String status) {
        this.driverId = driverId;
        this.name = name;
        this.from = from;
        this.to = to;
        this.item = item;
        this.qty = qty;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }
}
