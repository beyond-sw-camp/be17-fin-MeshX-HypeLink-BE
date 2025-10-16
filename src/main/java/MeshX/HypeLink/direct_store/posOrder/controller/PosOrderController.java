package MeshX.HypeLink.direct_store.posOrder.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.posOrder.constansts.SwaggerConstants;
import MeshX.HypeLink.direct_store.posOrder.model.dto.response.PosOrderDetailRes;
import MeshX.HypeLink.direct_store.posOrder.model.dto.response.PosOrderInfoRes;
import MeshX.HypeLink.direct_store.posOrder.service.PosOrderService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "POS 매장 주문 조회", description = "직영점의 POS 시스템에서 생성된 주문 내역을 조회하는 API")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class PosOrderController {
    private final PosOrderService orderService;


    @Operation(summary = "전체 주문 목록 조회", description = "해당 직영점의 모든 주문 목록을 간략한 정보와 함께 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 주문 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.POS_ORDER_INFO_LIST_RESPONSE)))
    })
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<PosOrderInfoRes>>> readOrders() {
        List<PosOrderInfoRes> response = orderService.readAllOrders();
        return ResponseEntity.ok(BaseResponse.of(response, "전체 주문 조회 완료"));
    }

    @Operation(summary = "오늘 주문 목록 조회", description = "해당 직영점의 오늘 하루 동안 발생한 모든 주문 목록을 간략한 정보와 함께 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "오늘 주문 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.POS_ORDER_INFO_LIST_RESPONSE)))
    })
    @GetMapping("/today")
    public ResponseEntity<BaseResponse<List<PosOrderInfoRes>>> readTodayOrders() {
        List<PosOrderInfoRes> response = orderService.readTodayOrders();
        return ResponseEntity.ok(BaseResponse.of(response, "오늘 주문 조회 완료"));
    }

    @Operation(summary = "주문 상세 내역 조회 (ID)", description = "주문의 고유 ID를 사용하여 특정 주문의 상세 내역 (상품, 결제 정보 포함)을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 상세 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.POS_ORDER_DETAIL_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 주문을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PosOrderDetailRes>> readOrderDetail(@Parameter(description = "조회할 주문의 ID") @PathVariable Integer id) {
        PosOrderDetailRes response = orderService.readOrderDetail(id);
        return ResponseEntity.ok(BaseResponse.of(response, "주문 상세 조회 완료"));
    }

    @Operation(summary = "주문 상세 내역 조회 (주문 번호)", description = "주문 번호를 사용하여 특정 주문의 상세 내역 (상품, 결제 정보 포함)을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 상세 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.POS_ORDER_DETAIL_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "해당 주문 번호의 주문을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<BaseResponse<PosOrderDetailRes>> readOrderByOrderNumber(@Parameter(description = "조회할 주문의 번호") @PathVariable String orderNumber) {
        PosOrderDetailRes response = orderService.readOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(BaseResponse.of(response, "주문 번호로 조회 완료"));
    }
}
