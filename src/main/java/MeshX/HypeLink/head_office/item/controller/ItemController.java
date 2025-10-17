package MeshX.HypeLink.head_office.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.item.model.dto.request.CreateItemReq;
import MeshX.HypeLink.head_office.item.model.dto.request.UpdateItemContentReq;
import MeshX.HypeLink.head_office.item.model.dto.request.UpdateItemStockReq;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemDetailInfoRes;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemInfoListRes;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemUpdateInfoRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ItemDetailInfoRes>> getItemInfo(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemDetailInfoRes.builder().build()));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<BaseResponse<ItemDetailInfoRes>> getItemInfoFromCode(@PathVariable String code) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemDetailInfoRes.builder().build()));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<BaseResponse<ItemInfoListRes>> getItemsfromCategory(@PathVariable String category) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemInfoListRes.builder().build()));
    }

    @GetMapping("/company/{company}")
    public ResponseEntity<BaseResponse<ItemInfoListRes>> getItemsfromCompany(@PathVariable String company) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemInfoListRes.builder().build()));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<BaseResponse<ItemInfoListRes>> getItemsfromName(@PathVariable String name) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemInfoListRes.builder().build()));
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<ItemDetailInfoRes>> createItem(@RequestBody CreateItemReq dto) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemDetailInfoRes.builder().build()));
    }

    @PatchMapping("/content")
    public ResponseEntity<BaseResponse<ItemUpdateInfoRes>> updateContents(@RequestBody UpdateItemContentReq dto) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemUpdateInfoRes.builder().build()));
    }

    @PatchMapping("/stock")
    public ResponseEntity<BaseResponse<ItemUpdateInfoRes>> updateStock(@RequestBody UpdateItemStockReq dto) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemUpdateInfoRes.builder().build()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteItem(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(BaseResponse.of(""));
    }
}
