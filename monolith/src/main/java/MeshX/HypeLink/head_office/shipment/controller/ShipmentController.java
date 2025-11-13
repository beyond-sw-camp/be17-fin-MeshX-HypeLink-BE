package MeshX.HypeLink.head_office.shipment.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.shipment.model.dto.CreateParcelReqDto;
import MeshX.HypeLink.head_office.shipment.model.dto.ShipmentAssignmentReqDto;
import MeshX.HypeLink.head_office.shipment.model.dto.ShipmentInfoDto;
import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import MeshX.HypeLink.head_office.shipment.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipment")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping("/connecting")
    public ResponseEntity<BaseResponse<String>> connecting(@RequestBody ShipmentAssignmentReqDto dto) {
        shipmentService.connecting(dto);
        return ResponseEntity.ok(BaseResponse.of("성공적으로 배정 되었습니다."));
    }

    @GetMapping("/parcels/unassigned")
    public ResponseEntity<BaseResponse<List<ShipmentInfoDto>>> getUnassignedParcels() {
        List<ShipmentInfoDto> result = shipmentService.getUnassignedParcels();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/parcels/assigned")
    public ResponseEntity<BaseResponse<List<ShipmentInfoDto>>> getAssignedParcels() {
        List<ShipmentInfoDto> result = shipmentService.getAssignedParcels();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/driver/{driverId}/has-active")
    public ResponseEntity<BaseResponse<Boolean>> hasActiveShipments(@PathVariable Integer driverId) {
        boolean hasActive = shipmentService.hasActiveShipmentsByDriver(driverId);
        return ResponseEntity.ok(BaseResponse.of(hasActive));
    }

}
