package MeshX.HypeLink.head_office.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.item.model.dto.request.*;
import MeshX.HypeLink.head_office.item.model.dto.response.*;
import MeshX.HypeLink.head_office.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ItemInfoRes>> getItemInfo(@PathVariable Integer id) {
        ItemInfoRes dto = itemService.findItemById(id);

        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }

    @GetMapping("/{code}")
    public ResponseEntity<BaseResponse<ItemDetailInfoRes>> getItemInfoByCode(@PathVariable String code) {
        ItemDetailInfoRes dto = itemService.findItemsByItemCode(code);

        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }

    @GetMapping("/detail/{detailCode}")
    public ResponseEntity<BaseResponse<ItemInfoRes>> getItemInfoByDetailCode(@PathVariable String detailCode) {
        ItemInfoRes result = itemService.findItemByItemDetailCode(detailCode);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    // paging 처리
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<PageRes<ItemSearchRes>>> getItems(Pageable pageable) {
        PageRes<ItemSearchRes> items = itemService.findItemsWithPaging(pageable);
        return ResponseEntity.ok(BaseResponse.of(items));
    }

    // paging 처리
    @GetMapping("/{category}")
    public ResponseEntity<BaseResponse<PageRes<ItemSearchRes>>> getItemsByCategory(@PathVariable String category, Pageable pageable) {
//        ItemSearchListRes items = itemService.findItemsByCategory(category);
        PageRes<ItemSearchRes> items = itemService.findItemsByCategoryWithPaging(category, pageable);
        return ResponseEntity.status(200).body(BaseResponse.of(items));
    }

    // paging 처리
    @GetMapping("/{name}")
    public ResponseEntity<BaseResponse<PageRes<ItemSearchRes>>> getItemsByName(@PathVariable String name, Pageable pageable) {
//        ItemSearchListRes dto = itemService.findItemsByName(name);
        PageRes<ItemSearchRes> items = itemService.findItemsByNameWithPaging(name, pageable);

        return ResponseEntity.status(200).body(BaseResponse.of(items));
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<ItemDetailInfoRes>> createItem(@RequestBody CreateItemReq dto) {
        itemService.saveItem(dto);

        ItemDetailInfoRes result = itemService.findItemsByItemCode(dto.getItemCode());

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @PatchMapping("/content")
    public ResponseEntity<BaseResponse<ItemDetailInfoRes>> updateContents(@RequestBody UpdateItemContentReq dto) {
        itemService.updateContents(dto);

        ItemDetailInfoRes result = itemService.findItemsByItemCode(dto.getItemDetailCode());

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @PatchMapping("/stock")
    public ResponseEntity<BaseResponse<ItemInfoRes>> updateStock(@RequestBody UpdateItemStockReq dto) {
        itemService.updateStock(dto);

        ItemInfoRes result = itemService.findItemByItemDetailCode(dto.getItemDetailCode());

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @PatchMapping("/en_name")
    public ResponseEntity<BaseResponse<ItemInfoRes>> updateEnName(@RequestBody UpdateItemEnNameReq dto) {
        itemService.updateEnName(dto);

        ItemInfoRes result = itemService.findItemByItemDetailCode(dto.getItemDetailCode());

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @PatchMapping("/ko_name")
    public ResponseEntity<BaseResponse<ItemInfoRes>> updateKoName(@RequestBody UpdateItemKoNameReq dto) {
        itemService.updateKoName(dto);

        ItemInfoRes result = itemService.findItemByItemDetailCode(dto.getItemDetailCode());

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @PatchMapping("/amount")
    public ResponseEntity<BaseResponse<ItemInfoRes>> updateAmount(@RequestBody UpdateItemAmountReq dto) {
        itemService.updateAmount(dto);

        ItemInfoRes result = itemService.findItemByItemDetailCode(dto.getItemDetailCode());

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }
}
