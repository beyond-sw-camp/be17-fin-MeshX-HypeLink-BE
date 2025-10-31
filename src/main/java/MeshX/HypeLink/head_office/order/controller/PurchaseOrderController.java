package MeshX.HypeLink.head_office.order.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.order.constants.OrderSwaggerConstants;
import MeshX.HypeLink.head_office.order.model.dto.request.HeadPurchaseOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.dto.request.PurchaseOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.dto.request.PurchaseOrderUpdateReq;
import MeshX.HypeLink.head_office.order.model.dto.response.*;
import MeshX.HypeLink.head_office.order.service.PurchaseOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "본부 발주 관리", description = "본부에서 발주를 관리하는 API")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final PurchaseOrderService headPurchaseOrderService;

    @Operation(summary = "본사 발주 생성", description = "본사에서 발주를 생성합니다.")
    @PostMapping("/head/create")
    public ResponseEntity<BaseResponse<String>> createHeadOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = OrderSwaggerConstants.PURCHASE_ORDER_CREATE_REQ_EXAMPLE)))
            @RequestBody HeadPurchaseOrderCreateReq dto) {
        headPurchaseOrderService.createHeadOrder(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("본사 발주가 생성되었습니다."));
    }

    @Operation(summary = "발주 생성", description = "발주를 생성합니다.")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = OrderSwaggerConstants.PURCHASE_ORDER_CREATE_REQ_EXAMPLE)))
            @RequestBody PurchaseOrderCreateReq dto) {
        headPurchaseOrderService.createOrder(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("본사 발주가 생성되었습니다."));
    }

    @Operation(summary = "발주 상세 조회", description = "발주 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(examples = @ExampleObject(value = OrderSwaggerConstants.PURCHASE_ORDER_DETAIL_RES_EXAMPLE)))
    })
    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<PurchaseOrderInfoDetailRes>> getOrder(
            @Parameter(description = "발주 ID") @PathVariable Integer id) {
        PurchaseOrderInfoDetailRes headPurchaseOrderInfoRes = headPurchaseOrderService.getDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(headPurchaseOrderInfoRes));
    }

    @Operation(summary = "발주 전체 목록 조회", description = "모든 발주 목록을 조회합니다.")
    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<PurchaseOrderInfoDetailListRes>> getOrders() {
        PurchaseOrderInfoDetailListRes headOrderInfoListRes = headPurchaseOrderService.getList();
        return ResponseEntity.status(200).body(BaseResponse.of(headOrderInfoListRes));
    }

    @Operation(summary = "발주 검색 (페이징)", description = "키워드와 상태로 발주를 검색합니다.")
    @GetMapping("/page/search")
    public ResponseEntity<BaseResponse<PageRes<PurchaseOrderInfoDetailRes>>> searchPurchaseOrders(
            Pageable pageReq,
            @Parameter(description = "검색 키워드") @RequestParam String keyWord,
            @Parameter(description = "발주 상태") @RequestParam String status,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        PageRes<PurchaseOrderInfoDetailRes> pageRes = headPurchaseOrderService.searchList(pageReq, keyWord, status, userDetails);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @Operation(summary = "발주 목록 조회 (페이징)", description = "발주 목록을 페이징하여 조회합니다.")
    @GetMapping("/read/page/all")
    public ResponseEntity<BaseResponse<PageRes<PurchaseOrderInfoRes>>> getOrders(
            Pageable pageReq,
            @Parameter(description = "검색 키워드") @RequestParam String keyWord,
            @Parameter(description = "카테고리") @RequestParam String category) {
        PageRes<PurchaseOrderInfoRes> pageRes = headPurchaseOrderService.getPurchaseOrderList(pageReq, keyWord, category);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @Operation(summary = "발주 상태 목록 조회", description = "발주 상태 목록을 조회합니다.")
    @GetMapping("/read/order/state")
    public ResponseEntity<BaseResponse<PurchaseOrderStateInfoListRes>> getPurchaseOrderState() {
        PurchaseOrderStateInfoListRes purchaseStateList = headPurchaseOrderService.getPurchaseStateList();
        return ResponseEntity.status(200).body(BaseResponse.of(purchaseStateList));
    }

    @Operation(summary = "발주 상세 상태 조회", description = "발주 상세 상태를 조회합니다.")
    @GetMapping("/read/order/detail")
    public ResponseEntity<BaseResponse<PurchaseDetailsStatusInfoListRes>> getPurchaseDetailStatus() {
        PurchaseDetailsStatusInfoListRes statuses = headPurchaseOrderService.getPurchaseDetailStatuses();
        return ResponseEntity.status(200).body(BaseResponse.of(statuses));
    }

    @Operation(summary = "발주 수정", description = "발주 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(examples = @ExampleObject(value = OrderSwaggerConstants.PURCHASE_ORDER_DETAIL_RES_EXAMPLE)))
    })
    @PatchMapping("/update")
    public ResponseEntity<BaseResponse<PurchaseOrderInfoDetailRes>> updateOrder(@RequestBody PurchaseOrderUpdateReq dto) {
        PurchaseOrderInfoDetailRes headPurchaseOrderInfoRes = headPurchaseOrderService.update(dto);
        return ResponseEntity.status(200).body(BaseResponse.of(headPurchaseOrderInfoRes));
    }
}
