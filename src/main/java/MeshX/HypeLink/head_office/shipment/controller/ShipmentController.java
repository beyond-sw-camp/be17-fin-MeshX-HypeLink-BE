package MeshX.HypeLink.head_office.shipment.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.shipment.constansts.SwaggerConstants;
import MeshX.HypeLink.head_office.shipment.model.dto.ConnectingReqDto;
import MeshX.HypeLink.head_office.shipment.model.dto.CreateParcelReqDto;
import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import MeshX.HypeLink.head_office.shipment.service.ShipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "출하 및 배송 관리", description = "물류 출고 및 배송 기사 할당 등 출하 프로세스를 관리하는 API")
@RestController
@RequestMapping("/shipment")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @Operation(summary = "송장 생성", description = "출고할 상품 목록을 담은 송장(Parcel)을 생성합니다. 이 단계에서는 아직 배송 기사가 할당되지 않습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "송장 생성 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.PARCEL_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Parcel>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "생성할 송장의 상품 목록과 송장 번호",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateParcelReqDto.class),
                            examples = @ExampleObject(value = SwaggerConstants.CREATE_PARCEL_REQUEST)
                    )
            )
            @RequestBody CreateParcelReqDto dto) {
        Parcel result = shipmentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.of(result));
    }


    @Operation(summary = "배송 기사 할당 및 출하 시작", description = "생성된 송장(들)을 특정 배송 기사에게 할당하고, 출발지와 도착지를 지정하여 배송(Shipment)을 시작합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "배송 기사 할당 및 출하 시작 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.SHIPMENT_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다. (존재하지 않는 송장, 기사, 또는 지점 정보)", content = @Content)
    })
    @PostMapping("/connecting")
    public ResponseEntity<BaseResponse<Shipment>> connecting(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "출하할 송장 번호 목록, 담당할 배송 기사, 출발 및 도착 지점 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConnectingReqDto.class),
                            examples = @ExampleObject(value = SwaggerConstants.CONNECTING_REQUEST)
                    )
            )
            @RequestBody ConnectingReqDto dto) {
        Shipment result = shipmentService.connetcting(dto);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

}
