package MeshX.HypeLink.direct_store.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreItemListReq;
import MeshX.HypeLink.direct_store.item.model.dto.request.UpdateStoreItemDetailReq;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreItemDetailInfoRes;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreItemDetailsInfoRes;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreItemDetailRes;
import MeshX.HypeLink.direct_store.item.service.StoreItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store/item")
@RequiredArgsConstructor
public class StoreItemController {
    private final StoreItemService storeItemService;

    @PostMapping("/create/all")
    public ResponseEntity<BaseResponse<String>> saveStoreItem(@RequestBody SaveStoreItemListReq dto) {
        storeItemService.saveAll(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("본사 아이템 리스트와 동기화가 완료되었습니다."));
    }

    @GetMapping("/purchase/list")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailsInfoRes>>> getItemDetailsList(@RequestParam Integer storeId,
                                                                                             Pageable pageReq,
                                                                                             @RequestParam String keyWord,
                                                                                             @RequestParam String category) {
        PageRes<StoreItemDetailsInfoRes> result = storeItemService.findPurchaseOrderList(storeId, pageReq, keyWord, category);
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @GetMapping("/detail/code")
    public ResponseEntity<BaseResponse<StoreItemDetailInfoRes>> getItemDetail(@RequestParam String itemDetailCode,
                                                                              @RequestParam Integer storeId,
                                                                              @RequestParam String itemCode) {

        StoreItemDetailInfoRes result = storeItemService.findItemDetailByItemDetailCode(itemCode, itemDetailCode, storeId);
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @PatchMapping("/detail/update")
    public ResponseEntity<BaseResponse<String>> updateItemDetail(@RequestBody UpdateStoreItemDetailReq dto) {
        storeItemService.updateItemDetail(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("재고 업데이트가 성공했습니다."));
    }

    // 특정 매장의 전체 상품 조회 (페이징)
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> getItemList(
            @RequestParam Integer storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreId(storeId, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    // 상품 검색 (페이징) - 한글명, 영문명, 바코드 검색
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> searchItems(
            @RequestParam Integer storeId,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreIdAndSearch(storeId, keyword, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    // 카테고리별 조회 (페이징)
    @GetMapping("/category")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> getItemsByCategory(
            @RequestParam Integer storeId,
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreIdAndCategory(storeId, category, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    // 재고 부족 상품 조회 (페이징)
    @GetMapping("/low-stock")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> getLowStockItems(
            @RequestParam Integer storeId,
            @RequestParam(defaultValue = "10") Integer minStock,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreIdAndLowStock(storeId, minStock, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    // 바코드로 상품 조회
    @GetMapping("/barcode")
    public ResponseEntity<BaseResponse<StoreItemDetailRes>> getItemByBarcode(@RequestParam String code) {
        StoreItemDetailRes result = storeItemService.findItemDetailByBarcode(code);
        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
