package com.example.apidirect.item.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiclients.annotation.GetMemberId;
import com.example.apidirect.constants.DirectSwaggerConstants;
import com.example.apidirect.item.adapter.in.web.dto.request.SaveStoreItemListRequest;
import com.example.apidirect.item.adapter.in.web.dto.request.UpdateStoreItemDetailRequest;
import com.example.apidirect.item.adapter.in.web.dto.response.StoreItemDetailResponse;
import com.example.apidirect.item.usecase.port.in.ItemCommandPort;
import com.example.apidirect.item.usecase.port.in.ItemQueryPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "매장 상품 관리", description = "매장의 의류 상품 재고 조회, 검색, 수정 등 상품 관리 API")
@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/direct/store/item")
public class ItemWebAdapter {

    private final ItemQueryPort itemQueryPort;
    private final ItemCommandPort itemCommandPort;


    @Operation(summary = "본사 상품 동기화", description = "본사의 상품 리스트를 매장으로 동기화합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "동기화 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.STORE_ITEM_UPDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content)
    })
    @PostMapping("/create/all")
    public ResponseEntity<BaseResponse<String>> saveStoreItemsFromHeadOffice(@RequestBody SaveStoreItemListRequest request) {
        itemCommandPort.saveAllItemsFromHeadOffice(request);
        return ResponseEntity.ok(BaseResponse.of("본사 아이템 리스트와 동기화가 완료되었습니다."));
    }

    @Operation(summary = "매장 상품 목록 조회", description = "현재 매장의 모든 상품 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.STORE_ITEM_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<Page<StoreItemDetailResponse>>> getItemList(
            @GetMemberId Integer memberId,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryPort.findAllItems(memberId, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "상품 검색", description = "키워드로 상품을 검색합니다. (상품명, 상품코드)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 검색 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.STORE_ITEM_SEARCH_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<Page<StoreItemDetailResponse>>> searchItems(
            @GetMemberId Integer memberId,
            @Parameter(description = "검색 키워드 (상품명, 상품코드)") @RequestParam(required = false) String keyword,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryPort.searchItems(memberId, keyword, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }


    @Operation(summary = "카테고리별 상품 조회", description = "특정 카테고리의 상품을 조회합니다. (상의, 하의, 아우터, 원피스 등)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리별 상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.STORE_ITEM_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/category")
    public ResponseEntity<BaseResponse<Page<StoreItemDetailResponse>>> getItemsByCategory(
            @GetMemberId Integer memberId,
            @Parameter(description = "카테고리 (상의, 하의, 아우터, 원피스 등)") @RequestParam String category,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryPort.findItemsByCategory(memberId, category, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }


    @Operation(summary = "재고 부족 상품 조회", description = "최소 재고량 미만인 상품을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재고 부족 상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.STORE_ITEM_LOW_STOCK_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/low-stock")
    public ResponseEntity<BaseResponse<Page<StoreItemDetailResponse>>> getLowStockItems(
            @GetMemberId Integer memberId,
            @Parameter(description = "최소 재고량 기준") @RequestParam(defaultValue = "10") Integer minStock,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryPort.findLowStockItems(memberId, minStock, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }


    @Operation(summary = "바코드로 상품 조회", description = "바코드를 스캔하여 상품 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.STORE_ITEM_DETAIL_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/barcode")
    public ResponseEntity<BaseResponse<StoreItemDetailResponse>> getItemByBarcode(@Parameter(description = "바코드 번호") @RequestParam String code) {
        StoreItemDetailResponse result = itemQueryPort.findItemByBarcode(code);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "상품 상세 조회", description = "상품 상세 코드로 상품 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 상세 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.STORE_ITEM_DETAIL_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/detail/code")
    public ResponseEntity<BaseResponse<StoreItemDetailResponse>> getItemDetail(
            @Parameter(description = "상품 상세 코드") @RequestParam String itemDetailCode,
            @Parameter(description = "매장 ID") @RequestParam Integer storeId,
            @Parameter(description = "상품 코드") @RequestParam String itemCode) {
        StoreItemDetailResponse result = itemQueryPort.findItemDetail(itemCode, itemDetailCode, storeId);
        return ResponseEntity.ok(BaseResponse.of(result));
    }


    @Operation(summary = "상품 재고 수정", description = "상품의 재고 수량을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재고 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.STORE_ITEM_UPDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음", content = @Content)
    })
    @PatchMapping("/detail/update")
    public ResponseEntity<BaseResponse<String>> updateItemStock(@RequestBody UpdateStoreItemDetailRequest request) {
        itemCommandPort.updateStock(request.getStoreId(), request.getItemDetailCode(), request.getUpdateStock());
        return ResponseEntity.ok(BaseResponse.of("성공"));
    }
}
