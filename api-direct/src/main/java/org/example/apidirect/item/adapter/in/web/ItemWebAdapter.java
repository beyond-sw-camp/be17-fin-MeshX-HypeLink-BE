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
    public ResponseEntity<Page<StoreItemDetailResponse>> getItemList(Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryUseCase.findAllItems(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StoreItemDetailResponse>> searchItems(
            @RequestParam(required = false) String keyword,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryUseCase.searchItems(keyword, pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/category")
    public ResponseEntity<Page<StoreItemDetailResponse>> getItemsByCategory(
            @RequestParam String category,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryUseCase.findItemsByCategory(category, pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/low-stock")
    public ResponseEntity<Page<StoreItemDetailResponse>> getLowStockItems(
            @RequestParam(defaultValue = "10") Integer minStock,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryUseCase.findLowStockItems(minStock, pageable);
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
