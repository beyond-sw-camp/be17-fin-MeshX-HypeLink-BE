package MeshX.HypeLink.head_office.shipment.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.shipment.constansts.ShipmentSwaggerConstants;
import MeshX.HypeLink.head_office.shipment.model.dto.CreateParcelReqDto;
import MeshX.HypeLink.head_office.shipment.model.dto.ShipmentAssignmentReqDto;
import MeshX.HypeLink.head_office.shipment.model.dto.ShipmentInfoDto;
import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import MeshX.HypeLink.head_office.shipment.service.ShipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "배송 관리", description = "배송 기사 배정 및 배송 관리 API")
@RestController
@RequestMapping("/api/shipment")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @Operation(summary = "배송 기사 배정", description = "배송 기사를 특정 배송건에 배정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "배정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ShipmentSwaggerConstants.SHIPMENT_ASSIGNMENT_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "404", description = "배송건 또는 기사를 찾을 수 없음", content = @Content)
    })
    @PostMapping("/connecting")
    public ResponseEntity<BaseResponse<String>> connecting(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "배송 기사 배정 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShipmentAssignmentReqDto.class),
                            examples = @ExampleObject(value = ShipmentSwaggerConstants.SHIPMENT_ASSIGNMENT_REQ_EXAMPLE)
                    )
            )
            @RequestBody ShipmentAssignmentReqDto dto) {
        shipmentService.connecting(dto);
        return ResponseEntity.ok(BaseResponse.of("성공적으로 배정 되었습니다."));
    }

    @Operation(summary = "미배정 배송 목록 조회", description = "아직 배송 기사가 배정되지 않은 배송 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ShipmentSwaggerConstants.SHIPMENT_INFO_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/parcels/unassigned")
    public ResponseEntity<BaseResponse<List<ShipmentInfoDto>>> getUnassignedParcels() {
        List<ShipmentInfoDto> result = shipmentService.getUnassignedParcels();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "배정된 배송 목록 조회", description = "배송 기사가 배정된 배송 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ShipmentSwaggerConstants.SHIPMENT_INFO_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/parcels/assigned")
    public ResponseEntity<BaseResponse<List<ShipmentInfoDto>>> getAssignedParcels() {
        List<ShipmentInfoDto> result = shipmentService.getAssignedParcels();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "배송 기사의 활성 배송 확인", description = "특정 배송 기사에게 현재 진행 중인 배송이 있는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ShipmentSwaggerConstants.HAS_ACTIVE_SHIPMENTS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "배송 기사를 찾을 수 없음", content = @Content)
    })
    @GetMapping("/driver/{driverId}/has-active")
    public ResponseEntity<BaseResponse<Boolean>> hasActiveShipments(
            @Parameter(description = "배송 기사 ID", required = true, example = "10")
            @PathVariable Integer driverId) {
        boolean hasActive = shipmentService.hasActiveShipmentsByDriver(driverId);
        return ResponseEntity.ok(BaseResponse.of(hasActive));
    }

}
