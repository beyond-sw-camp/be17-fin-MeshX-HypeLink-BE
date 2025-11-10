package org.example.apidirect.item.adapter.in.web;

import MeshX.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.item.adapter.in.web.dto.request.UpdateStoreItemDetailRequest;
import org.example.apidirect.item.adapter.in.web.dto.response.StoreItemDetailResponse;
import org.example.apidirect.item.usecase.port.in.ItemCommandUseCase;
import org.example.apidirect.item.usecase.port.in.ItemQueryUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemWebAdapter {

    private final ItemQueryUseCase itemQueryUseCase;
    private final ItemCommandUseCase itemCommandUseCase;


    @GetMapping("/list")
    public ResponseEntity<Page<StoreItemDetailResponse>> getItemList(
            @RequestParam Integer storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<StoreItemDetailResponse> result = itemQueryUseCase.findItemsByStoreId(storeId, pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/search")
    public ResponseEntity<Page<StoreItemDetailResponse>> searchItems(
            @RequestParam Integer storeId,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<StoreItemDetailResponse> result = itemQueryUseCase.searchItems(storeId, keyword, pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/category")
    public ResponseEntity<Page<StoreItemDetailResponse>> getItemsByCategory(
            @RequestParam Integer storeId,
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<StoreItemDetailResponse> result = itemQueryUseCase.findItemsByCategory(storeId, category, pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/low-stock")
    public ResponseEntity<Page<StoreItemDetailResponse>> getLowStockItems(
            @RequestParam Integer storeId,
            @RequestParam(defaultValue = "10") Integer minStock,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<StoreItemDetailResponse> result = itemQueryUseCase.findLowStockItems(storeId, minStock, pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/barcode")
    public ResponseEntity<StoreItemDetailResponse> getItemByBarcode(@RequestParam String code) {
        StoreItemDetailResponse result = itemQueryUseCase.findItemByBarcode(code);
        return ResponseEntity.ok(result);
    }


    @PatchMapping("/detail/update")
    public ResponseEntity<String> updateItemStock(@RequestBody UpdateStoreItemDetailRequest request) {
        itemCommandUseCase.updateStock(request.getItemDetailCode(), request.getUpdateStock());
        return ResponseEntity.ok("재고 업데이트가 성공했습니다.");
    }

}
