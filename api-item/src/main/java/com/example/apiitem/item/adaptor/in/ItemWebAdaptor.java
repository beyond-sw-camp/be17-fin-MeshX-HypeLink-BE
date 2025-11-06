package com.example.apiitem.item.adaptor.in;

import MeshX.common.BaseResponse;
import MeshX.common.Page.PageRes;
import MeshX.common.WebAdapter;
import com.example.apiitem.item.usecase.port.in.WebPort;
import com.example.apiitem.item.usecase.port.in.request.*;
import com.example.apiitem.item.usecase.port.out.response.ItemInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@WebAdapter // = @RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemWebAdaptor {
    private final WebPort webPort;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ItemInfoDto>> getItemInfo(@PathVariable Integer id) {
        ItemInfoDto dto = webPort.findItemById(id);
        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<BaseResponse<ItemInfoDto>> getItemInfoByCode(@PathVariable String code) {
        ItemInfoDto dto = webPort.findItemsByItemCode(code);
        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<PageRes<ItemInfoDto>>> getItems(Pageable pageable,
                                                                       @RequestParam String keyWord,
                                                                       @RequestParam String category) {
        PageRes<ItemInfoDto> items = webPort.findItemsWithPaging(pageable, keyWord, category);
        return ResponseEntity.ok(BaseResponse.of(items));
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createItem(@RequestBody CreateItemCommand dto) {
        webPort.saveItem(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("result"));
    }

    @PatchMapping("/content")
    public ResponseEntity<BaseResponse<String>> updateContents(@RequestBody UpdateItemContentCommand dto) {
        webPort.updateContents(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/en_name")
    public ResponseEntity<BaseResponse<String>> updateEnName(@RequestBody UpdateItemEnNameCommand dto) {
        webPort.updateEnName(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/ko_name")
    public ResponseEntity<BaseResponse<String>> updateKoName(@RequestBody UpdateItemKoNameCommand dto) {
        webPort.updateKoName(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/amount")
    public ResponseEntity<BaseResponse<String>> updateAmount(@RequestBody UpdateItemAmountCommand dto) {
        webPort.updateAmount(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/unit_price")
    public ResponseEntity<BaseResponse<String>> updateUnitPrice(@RequestBody UpdateItemUnitPriceCommand dto) {
        webPort.updateUnitPrice(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/company")
    public ResponseEntity<BaseResponse<String>> updateCompany(@RequestBody UpdateItemCompanyCommand dto) {
        webPort.updateCompany(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/category")
    public ResponseEntity<BaseResponse<String>> updateCategory(@RequestBody UpdateItemCategoryCommand dto) {
        webPort.updateCategory(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @PatchMapping("/images")
    public ResponseEntity<BaseResponse<String>> updateImages(@RequestBody UpdateItemImagesCommand dto) {
        webPort.updateImages(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }
}
