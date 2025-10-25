package MeshX.HypeLink.head_office.shipment.model.dto;

import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import MeshX.HypeLink.head_office.shipment.model.entity.ShipmentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShipmentInfoDto {
    private Integer shipmentId;

    private String trackingNumber; // 송장 번호
    private String from; // 출발지 이름
    private String to;  // 도착지 이름
    private String content; // 품목 요약 정보
    private Integer quantity; // 전체 수량
    private ShipmentStatus shipmentStatus; // 배송 상태
    private Integer driverId;
    private String driverName;



    public static ShipmentInfoDto fromEntity(Shipment dto) {
        return ShipmentInfoDto.builder()
                .shipmentId(dto.getId())
                .trackingNumber(dto.getParcel().getTrackingNumber())
                .from(dto.getParcel().getSupplier().getMember().getName())
                .to(dto.getParcel().getRequester().getMember().getName())
                .content(toContentString(dto))
                .quantity(dto.getParcel().getParcelItems().size())
                .shipmentStatus(dto.getShipmentStatus())
                .driverId(dto.getDriver() != null ? dto.getDriver().getId() : null)
                .driverName(dto.getDriver() != null ? dto.getDriver().getMember().getName() : null)
                .build();
    }

    private static String toContentString(Shipment dto){
//        dto.getParcel().getName();
//        dto.getParcel().getParcelItems().size();
        // 이름 + size() -1 해서 이름 외 몇개 로

        return "임시 데이터";
    }
}
