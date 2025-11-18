package MeshX.HypeLink.direct_store.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.direct_store.constansts.DirectStoreSwaggerConstants;
import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreItemListReq;
import MeshX.HypeLink.direct_store.item.model.dto.request.UpdateStoreItemDetailReq;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreItemDetailInfoRes;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreItemDetailsInfoRes;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreItemDetailRes;
import MeshX.HypeLink.direct_store.item.service.StoreItemService;
import com.example.apiclients.annotation.GetMemberEmail;
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

@Tag(name = "직영점 상품 관리", description = "직영점의 상품 재고 관리 및 동기화 API")
@RestController
@RequestMapping("/api/store/item")
@RequiredArgsConstructor
public class StoreItemController {
    private final StoreItemService storeItemService;

    @Operation(summary = "본사 상품 동기화", description = "본사의 상품 목록을 직영점으로 동기화합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "동기화 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.SYNC_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
    })
    @PostMapping("/create/all")
    public ResponseEntity<BaseResponse<String>> saveStoreItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "동기화할 상품 목록",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaveStoreItemListReq.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.SAVE_STORE_ITEM_LIST_REQ_EXAMPLE)
                    )
            )
            @RequestBody SaveStoreItemListReq dto) {
        storeItemService.saveAll(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("본사 아이템 리스트와 동기화가 완료되었습니다."));
    }

    @Operation(summary = "발주용 상품 목록 조회", description = "직영점의 발주를 위한 상품 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageRes.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.STORE_ITEM_DETAILS_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
    })
    @GetMapping("/purchase/list")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailsInfoRes>>> getItemDetailsList(
            @Parameter(description = "직영점 ID", required = true) @RequestParam Integer storeId,
            Pageable pageReq,
            @Parameter(description = "검색 키워드") @RequestParam String keyWord,
            @Parameter(description = "카테고리") @RequestParam String category) {
        PageRes<StoreItemDetailsInfoRes> result = storeItemService.findPurchaseOrderList(storeId, pageReq, keyWord, category);
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "상품 상세 정보 조회", description = "상품 코드와 상세 코드로 특정 상품의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.STORE_ITEM_DETAIL_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/detail/code")
    public ResponseEntity<BaseResponse<StoreItemDetailInfoRes>> getItemDetail(
            @Parameter(description = "상품 상세 코드", required = true) @RequestParam String itemDetailCode,
            @Parameter(description = "직영점 ID", required = true) @RequestParam Integer storeId,
            @Parameter(description = "상품 코드", required = true) @RequestParam String itemCode) {

        StoreItemDetailInfoRes result = storeItemService.findItemDetailByItemDetailCode(itemCode, itemDetailCode, storeId);
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "상품 재고 업데이트", description = "직영점 상품의 재고를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업데이트 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
    })
    @PatchMapping("/detail/update")
    public ResponseEntity<BaseResponse<String>> updateItemDetail(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "업데이트할 재고 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateStoreItemDetailReq.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.UPDATE_STORE_ITEM_DETAIL_REQ_EXAMPLE)
                    )
            )
            @RequestBody UpdateStoreItemDetailReq dto) {
        storeItemService.updateItemDetail(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("재고 업데이트가 성공했습니다."));
    }

    @Operation(summary = "직영점 상품 목록 조회", description = "로그인한 직영점의 전체 상품 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageRes.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.STORE_ITEM_DETAIL_PAGE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> getItemList(
            @Parameter(hidden = true) @GetMemberEmail String email,
            @Parameter(description = "페이지 번호", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "50") @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreId(email, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "상품 검색", description = "키워드로 직영점의 상품을 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "검색 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageRes.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.STORE_ITEM_DETAIL_PAGE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> searchItems(
            @Parameter(hidden = true) @GetMemberEmail String email,
            @Parameter(description = "검색 키워드", required = true) @RequestParam String keyword,
            @Parameter(description = "페이지 번호", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "50") @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreIdAndSearch(email, keyword, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "카테고리별 상품 조회", description = "특정 카테고리의 상품을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageRes.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.STORE_ITEM_DETAIL_PAGE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/category")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> getItemsByCategory(
            @Parameter(hidden = true) @GetMemberEmail String email,
            @Parameter(description = "카테고리명", required = true) @RequestParam String category,
            @Parameter(description = "페이지 번호", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "50") @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreIdAndCategory(email, category, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "재고 부족 상품 조회", description = "설정한 최소 재고 수량보다 적은 상품을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageRes.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.STORE_ITEM_DETAIL_PAGE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/low-stock")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> getLowStockItems(
            @Parameter(hidden = true) @GetMemberEmail String email,
            @Parameter(description = "최소 재고 수량", example = "10") @RequestParam(defaultValue = "10") Integer minStock,
            @Parameter(description = "페이지 번호", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "50") @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreIdAndLowStock(email, minStock, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "바코드로 상품 조회", description = "바코드를 스캔하여 상품 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.STORE_ITEM_DETAIL_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/barcode")
    public ResponseEntity<BaseResponse<StoreItemDetailRes>> getItemByBarcode(
            @Parameter(description = "바코드 값", required = true) @RequestParam String code) {
        StoreItemDetailRes result = storeItemService.findItemDetailByBarcode(code);
        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
