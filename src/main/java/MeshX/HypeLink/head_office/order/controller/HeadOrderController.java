package MeshX.HypeLink.head_office.order.controller;


import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.order.constansts.SwaggerConstants;
import MeshX.HypeLink.head_office.order.model.dto.request.HeadOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.dto.request.HeadOrderUpdateReq;
import MeshX.HypeLink.head_office.order.model.dto.response.HeadOrderInfoListRes;
import MeshX.HypeLink.head_office.order.model.dto.response.HeadOrderInfoRes;
import MeshX.HypeLink.head_office.order.service.HeadOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "본사 발주 관리", description = "본사에서 원자재 또는 완제품을 공급업체로 발주하는 API")
@RestController
@RequestMapping("/api/head-office/order")
@RequiredArgsConstructor
public class HeadOrderController {
    private final HeadOrderService headOrderService;

    @Operation(summary = "신규 발주 등록", description = "본사에서 필요한 원자재/완제품에 대한 신규 발주를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "발주 등록 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "등록할 발주의 상세 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HeadOrderCreateReq.class),
                            examples = @ExampleObject(value = SwaggerConstants.HEAD_ORDER_CREATE_REQUEST)
                    )
            )
            @RequestBody HeadOrderCreateReq dto) {
        headOrderService.createOrder(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.of("본사 발주가 생성되었습니다."));
    }

    @Operation(summary = "발주 삭제", description = "발주 ID를 사용하여 특정 발주를 시스템에서 삭제합니다. (단, 배송 시작 전 발주만 가능)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 발주를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 배송이 시작되어 삭제할 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteOrder(@Parameter(description = "삭제할 발주의 ID") @PathVariable Integer id) {
        headOrderService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("본사 발주가 성공적으로 삭제 되었습니다."));
    }

    @Operation(summary = "발주 상세 조회", description = "발주 ID를 사용하여 특정 발주의 상세 내역을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주 상세 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.HEAD_ORDER_INFO_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 발주를 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<HeadOrderInfoRes>> readOrder(@Parameter(description = "조회할 발주의 ID") @PathVariable Integer id) {
        HeadOrderInfoRes headOrderInfoRes = headOrderService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(headOrderInfoRes));
    }

    @Operation(summary = "전체 발주 목록 조회", description = "본사의 모든 발주 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 발주 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.HEAD_ORDER_INFO_LIST_RESPONSE)))
    })
    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<HeadOrderInfoListRes>> readAllOrders() {
        HeadOrderInfoListRes headOrderInfoListRes = headOrderService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(headOrderInfoListRes));
    }

    @Operation(summary = "발주 정보 수정", description = "기존 발주의 정보를 수정합니다. (단, 배송 시작 전 발주만 가능)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발주 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.HEAD_ORDER_INFO_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 ID의 발주를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 배송이 시작되어 수정할 수 없습니다.", content = @Content)
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<HeadOrderInfoRes>> updateOrder(
            @Parameter(description = "수정할 발주의 ID") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정할 발주의 상세 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HeadOrderUpdateReq.class),
                            examples = @ExampleObject(value = SwaggerConstants.HEAD_ORDER_UPDATE_REQUEST)
                    )
            )
            @RequestBody HeadOrderUpdateReq dto) {
        HeadOrderInfoRes headOrderInfoRes = headOrderService.update(id, dto.getItemName(), dto.getUnitPrice(), dto.getQuantity(), dto.getTotalPrice(), dto.getDeliveryAddress(), dto.getDeliveryRequest(), dto.getOrderRequest());
        return ResponseEntity.status(200).body(BaseResponse.of(headOrderInfoRes));
    }
}
