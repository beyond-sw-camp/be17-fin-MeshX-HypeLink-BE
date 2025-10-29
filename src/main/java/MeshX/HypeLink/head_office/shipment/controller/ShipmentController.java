package MeshX.HypeLink.head_office.shipment.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.shipment.constants.ShipmentSwaggerConstants;
import MeshX.HypeLink.head_office.shipment.model.dto.CreateParcelReqDto;
import MeshX.HypeLink.head_office.shipment.model.dto.ShipmentAssignmentReqDto;
import MeshX.HypeLink.head_office.shipment.model.dto.ShipmentInfoDto;
import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import MeshX.HypeLink.head_office.shipment.service.ShipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "본부 배송 관리", description = "본부에서 배송을 관리하는 API")
@RestController
@RequestMapping("/api/shipment")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @Operation(summary = "배송 기사 배정", description = "배송 기사에게 택배를 배정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "배정 성공")
    })
    @PostMapping("/connecting")
    public ResponseEntity<BaseResponse<String>> connecting(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = ShipmentSwaggerConstants.SHIPMENT_ASSIGNMENT_REQ_EXAMPLE)))
            @RequestBody ShipmentAssignmentReqDto dto) {
        shipmentService.connecting(dto);
        return ResponseEntity.ok(BaseResponse.of("성공적으로 배정 되었습니다."));
    }

    @Operation(summary = "미배정 택배 조회", description = "배송 기사에게 배정되지 않은 택배 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(examples = @ExampleObject(value = ShipmentSwaggerConstants.SHIPMENT_INFO_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/parcels/unassigned")
    public ResponseEntity<BaseResponse<List<ShipmentInfoDto>>> getUnassignedParcels() {
        List<ShipmentInfoDto> result = shipmentService.getUnassignedParcels();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "배정된 택배 조회", description = "배송 기사에게 배정된 택배 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(examples = @ExampleObject(value = ShipmentSwaggerConstants.SHIPMENT_INFO_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/parcels/assigned")
    public ResponseEntity<BaseResponse<List<ShipmentInfoDto>>> getAssignedParcels() {
        List<ShipmentInfoDto> result = shipmentService.getAssignedParcels();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

}
