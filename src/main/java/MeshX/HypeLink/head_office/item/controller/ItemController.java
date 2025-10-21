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

    @GetMapping("/code/{code}")
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
    @GetMapping("/category/{category}")
    public ResponseEntity<BaseResponse<PageRes<ItemSearchRes>>> getItemsByCategory(@PathVariable String category, Pageable pageable) {
//        ItemSearchListRes items = itemService.findItemsByCategory(category);
        PageRes<ItemSearchRes> items = itemService.findItemsByCategoryWithPaging(category, pageable);
        return ResponseEntity.status(200).body(BaseResponse.of(items));
    }

    // paging 처리
    @GetMapping("/name/{name}")
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
    public ResponseEntity<BaseResponse<String>> updateContents(@RequestBody UpdateItemContentReq dto) {
        itemService.updateContents(dto);

        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/stock")
    public ResponseEntity<BaseResponse<String>> updateStock(@RequestBody UpdateItemStockReq dto) {
        itemService.updateStock(dto);

        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/en_name")
    public ResponseEntity<BaseResponse<String>> updateEnName(@RequestBody UpdateItemEnNameReq dto) {
        itemService.updateEnName(dto);

        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/ko_name")
    public ResponseEntity<BaseResponse<String>> updateKoName(@RequestBody UpdateItemKoNameReq dto) {
        itemService.updateKoName(dto);

        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/amount")
    public ResponseEntity<BaseResponse<String>> updateAmount(@RequestBody UpdateItemAmountReq dto) {
        itemService.updateAmount(dto);

        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/unit_price")
    public ResponseEntity<BaseResponse<String>> updateUnitPrice(@RequestBody UpdateItemUnitPriceReq dto) {
        itemService.updateUnitPrice(dto);

        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/company")
    public ResponseEntity<BaseResponse<String>> updateCompany(@RequestBody UpdateItemCompanyReq dto) {
        itemService.updateCompany(dto);

        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/category")
    public ResponseEntity<BaseResponse<String>> updateCategory(@RequestBody UpdateItemCategoryReq dto) {
        itemService.updateCategory(dto);

        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/images")
    public ResponseEntity<BaseResponse<String>> updateImages(@RequestBody UpdateItemImagesReq dto) {
        itemService.updateImages(dto);

        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }
}
