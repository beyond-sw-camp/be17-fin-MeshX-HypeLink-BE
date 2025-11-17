package com.example.apidirect.item.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiclients.annotation.GetMemberId;
import com.example.apidirect.item.adapter.in.web.dto.request.SaveStoreCategoriesRequest;
import com.example.apidirect.item.adapter.in.web.dto.request.SaveStoreItemListRequest;
import com.example.apidirect.item.adapter.in.web.dto.request.UpdateStoreItemDetailRequest;
import com.example.apidirect.item.adapter.in.web.dto.response.StoreItemDetailResponse;
import com.example.apidirect.item.usecase.port.in.ItemCommandPort;
import com.example.apidirect.item.usecase.port.in.ItemQueryPort;
import lombok.RequiredArgsConstructor;
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


    @PostMapping("/create/all")
    public ResponseEntity<BaseResponse<String>> saveStoreItemsFromHeadOffice(@RequestBody SaveStoreItemListRequest request) {
        itemCommandPort.saveAllItemsFromHeadOffice(request);
        return ResponseEntity.ok(BaseResponse.of("본사 아이템 리스트와 동기화가 완료되었습니다."));
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<Page<StoreItemDetailResponse>>> getItemList(
            @GetMemberId Integer memberId,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryPort.findAllItems(memberId, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<Page<StoreItemDetailResponse>>> searchItems(
            @GetMemberId Integer memberId,
            @RequestParam(required = false) String keyword,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryPort.searchItems(memberId, keyword, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }


    @GetMapping("/category")
    public ResponseEntity<BaseResponse<Page<StoreItemDetailResponse>>> getItemsByCategory(
            @GetMemberId Integer memberId,
            @RequestParam String category,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryPort.findItemsByCategory(memberId, category, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }


    @GetMapping("/low-stock")
    public ResponseEntity<BaseResponse<Page<StoreItemDetailResponse>>> getLowStockItems(
            @GetMemberId Integer memberId,
            @RequestParam(defaultValue = "10") Integer minStock,
            Pageable pageable) {
        Page<StoreItemDetailResponse> result = itemQueryPort.findLowStockItems(memberId, minStock, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }


    @GetMapping("/barcode")
    public ResponseEntity<BaseResponse<StoreItemDetailResponse>> getItemByBarcode(@RequestParam String code) {
        StoreItemDetailResponse result = itemQueryPort.findItemByBarcode(code);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/detail/code")
    public ResponseEntity<BaseResponse<StoreItemDetailResponse>> getItemDetail(
            @RequestParam String itemDetailCode,
            @RequestParam Integer storeId,
            @RequestParam String itemCode) {
        StoreItemDetailResponse result = itemQueryPort.findItemDetail(itemCode, itemDetailCode, storeId);
        return ResponseEntity.ok(BaseResponse.of(result));
    }


    @PatchMapping("/detail/update")
    public ResponseEntity<BaseResponse<String>> updateItemStock(@RequestBody UpdateStoreItemDetailRequest request) {
        itemCommandPort.updateStock(request.getItemDetailCode(), request.getUpdateStock());
        return ResponseEntity.ok(BaseResponse.of("성공"));
    }
}
