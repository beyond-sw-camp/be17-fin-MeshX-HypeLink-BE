package MeshX.HypeLink.direct_store.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.direct_store.item.constansts.ItemSwaggerConstants;
import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreItemListReq;
import MeshX.HypeLink.direct_store.item.model.dto.request.UpdateStoreItemDetailReq;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreItemDetailInfoRes;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreItemDetailsInfoRes;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreItemDetailRes;
import MeshX.HypeLink.direct_store.item.service.StoreItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "직영점 상품 관리", description = "직영점의 상품 정보 및 재고를 관리하는 API")
@RestController
@RequestMapping("/api/store/item")
@RequiredArgsConstructor
public class StoreItemController {
    private final StoreItemService storeItemService;

    @Operation(summary = "본사 아이템 동기화", description = "본사 아이템 리스트와 직영점 상품 정보를 동기화합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "동기화 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.SAVE_ITEM_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping("/create/all")
    public ResponseEntity<BaseResponse<String>> saveStoreItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "저장할 상품 목록",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaveStoreItemListReq.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.SAVE_STORE_ITEM_LIST_REQ_EXAMPLE)
                    )
            )
            @RequestBody SaveStoreItemListReq dto) {
        storeItemService.saveAll(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("본사 아이템 리스트와 동기화가 완료되었습니다."));
    }

    @Operation(summary = "구매 주문 목록 조회", description = "특정 매장의 구매 주문 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구매 주문 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.STORE_ITEM_DETAILS_INFO_RES_PAGE_EXAMPLE)))
    })
    @GetMapping("/purchase/list")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailsInfoRes>>> getItemDetailsList(
            @Parameter(description = "매장 ID") @RequestParam Integer storeId,
            @Parameter(hidden = true) Pageable pageReq,
            @Parameter(description = "검색 키워드 (상품명, 영문명, 바코드)") @RequestParam String keyWord,
            @Parameter(description = "카테고리") @RequestParam String category) {
        PageRes<StoreItemDetailsInfoRes> result = storeItemService.findPurchaseOrderList(storeId, pageReq, keyWord, category);
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "상품 상세 정보 조회", description = "상품 상세 코드, 매장 ID, 상품 코드를 사용하여 상품의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 상세 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.STORE_ITEM_DETAIL_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/detail/code")
    public ResponseEntity<BaseResponse<StoreItemDetailInfoRes>> getItemDetail(
            @Parameter(description = "상품 상세 코드") @RequestParam String itemDetailCode,
            @Parameter(description = "매장 ID") @RequestParam Integer storeId,
            @Parameter(description = "상품 코드") @RequestParam String itemCode) {

        StoreItemDetailInfoRes result = storeItemService.findItemDetailByItemDetailCode(itemCode, itemDetailCode, storeId);
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "상품 재고 업데이트", description = "상품 상세 ID를 사용하여 상품의 재고를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재고 업데이트 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_STOCK_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/detail/update")
    public ResponseEntity<BaseResponse<String>> updateItemDetail(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "업데이트할 재고 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateStoreItemDetailReq.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_STORE_ITEM_DETAIL_REQ_EXAMPLE)
                    )
            )
            @RequestBody UpdateStoreItemDetailReq dto) {
        storeItemService.updateItemDetail(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("재고 업데이트가 성공했습니다."));
    }

    @Operation(summary = "특정 매장의 전체 상품 조회 (페이징)", description = "현재 로그인된 사용자의 매장에 등록된 모든 상품을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.STORE_ITEM_DETAIL_RES_PAGE_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> getItemList(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "50") @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreId(userDetails, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "상품 검색 (페이징)", description = "현재 로그인된 사용자의 매장에서 상품을 한글명, 영문명, 바코드로 검색하여 페이징 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 검색 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.STORE_ITEM_DETAIL_RES_PAGE_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> searchItems(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "검색 키워드 (한글명, 영문명, 바코드)") @RequestParam String keyword,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "50") @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreIdAndSearch(userDetails, keyword, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "카테고리별 상품 조회 (페이징)", description = "현재 로그인된 사용자의 매장에서 특정 카테고리에 속하는 상품을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리별 상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.STORE_ITEM_DETAIL_RES_PAGE_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/category")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> getItemsByCategory(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "카테고리명") @RequestParam String category,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "50") @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreIdAndCategory(userDetails, category, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "재고 부족 상품 조회 (페이징)", description = "현재 로그인된 사용자의 매장에서 재고가 부족한 상품을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재고 부족 상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.STORE_ITEM_DETAIL_RES_PAGE_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/low-stock")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> getLowStockItems(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "최소 재고 수량", example = "10") @RequestParam(defaultValue = "10") Integer minStock,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "50") @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreIdAndLowStock(userDetails, minStock, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "바코드로 상품 조회", description = "바코드 값을 사용하여 상품 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.STORE_ITEM_DETAIL_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없습니다.", content = @Content) 
    })
    @GetMapping("/barcode")
    public ResponseEntity<BaseResponse<StoreItemDetailRes>> getItemByBarcode(
            @Parameter(description = "바코드 값") @RequestParam String code) {
        StoreItemDetailRes result = storeItemService.findItemDetailByBarcode(code);
        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
