package MeshX.HypeLink.direct_store.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreItemListReq;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreItemDetailRes;
import MeshX.HypeLink.direct_store.item.service.StoreItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    // 특정 매장의 전체 상품 조회 (페이징)
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> getItemList(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreId(userDetails, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    // 상품 검색 (페이징) - 한글명, 영문명, 바코드 검색
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> searchItems(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreIdAndSearch(userDetails, keyword, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    // 카테고리별 조회 (페이징)
    @GetMapping("/category")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> getItemsByCategory(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreIdAndCategory(userDetails, category, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    // 재고 부족 상품 조회 (페이징)
    @GetMapping("/low-stock")
    public ResponseEntity<BaseResponse<PageRes<StoreItemDetailRes>>> getLowStockItems(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "10") Integer minStock,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageRes<StoreItemDetailRes> result = storeItemService.findItemDetailsByStoreIdAndLowStock(userDetails, minStock, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    // 바코드로 상품 조회
    @GetMapping("/barcode")
    public ResponseEntity<BaseResponse<StoreItemDetailRes>> getItemByBarcode(@RequestParam String code) {
        StoreItemDetailRes result = storeItemService.findItemDetailByBarcode(code);
        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
