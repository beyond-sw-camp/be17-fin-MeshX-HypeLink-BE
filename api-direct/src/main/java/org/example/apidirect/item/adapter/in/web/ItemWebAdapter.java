package org.example.apidirect.item.adapter.in.web;

import MeshX.common.WebAdapter;
import com.example.apiclients.annotation.GetMemberEmail;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.item.adapter.in.web.dto.request.UpdateStoreItemDetailRequest;
import org.example.apidirect.item.adapter.in.web.dto.response.StoreItemDetailResponse;
import org.example.apidirect.item.usecase.port.in.ItemCommandPort;
import org.example.apidirect.item.usecase.port.in.ItemQueryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/store/item")
public class ItemWebAdapter {

    private final ItemQueryPort itemQueryPort;
    private final ItemCommandPort itemCommandPort;


    @GetMapping("/list")
    public ResponseEntity<Page<StoreItemDetailResponse>> getItemList(Pageable pageable,
                                                                     @GetMemberEmail String email) {
        for (int i = 0; i < 10; i++) {

            System.out.println(email);
        }
        Page<StoreItemDetailResponse> result = itemQueryPort.findAllItems(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StoreItemDetailResponse>> searchItems(
            @RequestParam(required = false) String keyword,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryPort.searchItems(keyword, pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/category")
    public ResponseEntity<Page<StoreItemDetailResponse>> getItemsByCategory(
            @RequestParam String category,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryPort.findItemsByCategory(category, pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/low-stock")
    public ResponseEntity<Page<StoreItemDetailResponse>> getLowStockItems(
            @RequestParam(defaultValue = "10") Integer minStock,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryPort.findLowStockItems(minStock, pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/barcode")
    public ResponseEntity<StoreItemDetailResponse> getItemByBarcode(@RequestParam String code) {
        StoreItemDetailResponse result = itemQueryPort.findItemByBarcode(code);
        return ResponseEntity.ok(result);
    }


    @PatchMapping("/detail/update")
    public ResponseEntity<String> updateItemStock(@RequestBody UpdateStoreItemDetailRequest request) {
        itemCommandPort.updateStock(request.getItemDetailCode(), request.getUpdateStock());
        return ResponseEntity.ok("재고 업데이트가 성공했습니다.");
    }
}
