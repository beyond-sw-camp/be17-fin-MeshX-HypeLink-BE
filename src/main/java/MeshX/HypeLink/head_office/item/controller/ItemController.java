package MeshX.HypeLink.head_office.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.item.constants.ItemSwaggerConstants;
import MeshX.HypeLink.head_office.item.model.dto.request.*;
import MeshX.HypeLink.head_office.item.model.dto.response.*;
import MeshX.HypeLink.head_office.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "본부 상품 관리", description = "본부에서 상품을 관리하는 API")
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "상품 조회 (ID)", description = "상품 ID로 상품 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_INFO_RES_EXAMPLE)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ItemInfoRes>> getItemInfo(@Parameter(description = "상품 ID") @PathVariable Integer id) {
        ItemInfoRes dto = itemService.findItemById(id);
        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }

    @Operation(summary = "상품 조회 (코드)", description = "상품 코드로 상품 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_INFO_RES_EXAMPLE)))
    })
    @GetMapping("/code/{code}")
    public ResponseEntity<BaseResponse<ItemInfoRes>> getItemInfoByCode(@Parameter(description = "상품 코드") @PathVariable String code) {
        ItemInfoRes dto = itemService.findItemsByItemCode(code);
        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }

    @Operation(summary = "상품 목록 조회 (페이징)", description = "키워드와 카테고리로 상품 목록을 검색합니다.")
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<PageRes<ItemInfoRes>>> getItems(Pageable pageable,
                                                                       @Parameter(description = "검색 키워드") @RequestParam String keyWord,
                                                                       @Parameter(description = "카테고리") @RequestParam String category) {
        PageRes<ItemInfoRes> items = itemService.findItemsWithPaging(pageable, keyWord, category);
        return ResponseEntity.ok(BaseResponse.of(items));
    }

    @Operation(summary = "카테고리별 상품 조회", description = "카테고리별로 상품 목록을 조회합니다.")
    @GetMapping("/category/{category}")
    public ResponseEntity<BaseResponse<PageRes<ItemInfoRes>>> getItemsByCategory(@Parameter(description = "카테고리명") @PathVariable String category, Pageable pageable) {
        PageRes<ItemInfoRes> items = itemService.findItemsByCategoryWithPaging(category, pageable);
        return ResponseEntity.status(200).body(BaseResponse.of(items));
    }

    @Operation(summary = "상품명 검색", description = "상품명으로 상품을 검색합니다.")
    @GetMapping("/name/{name}")
    public ResponseEntity<BaseResponse<PageRes<ItemInfoRes>>> getItemsByName(@Parameter(description = "상품명") @PathVariable String name, Pageable pageable) {
        PageRes<ItemInfoRes> items = itemService.findItemsByNameWithPaging(name, pageable);
        return ResponseEntity.status(200).body(BaseResponse.of(items));
    }

    @Operation(summary = "상품 생성", description = "새로운 상품을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "생성 성공")
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_CREATE_REQ_EXAMPLE)))
            @RequestBody CreateItemReq dto) {
        itemService.saveItem(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("result"));
    }

    @Operation(summary = "상품 설명 수정", description = "상품 설명을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_SUCCESS_EXAMPLE)))
    })
    @PatchMapping("/content")
    public ResponseEntity<BaseResponse<String>> updateContents(@RequestBody UpdateItemContentReq dto) {
        itemService.updateContents(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 영문명 수정", description = "상품 영문명을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_SUCCESS_EXAMPLE)))
    })
    @PatchMapping("/en_name")
    public ResponseEntity<BaseResponse<String>> updateEnName(@RequestBody UpdateItemEnNameReq dto) {
        itemService.updateEnName(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 한글명 수정", description = "상품 한글명을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_SUCCESS_EXAMPLE)))
    })
    @PatchMapping("/ko_name")
    public ResponseEntity<BaseResponse<String>> updateKoName(@RequestBody UpdateItemKoNameReq dto) {
        itemService.updateKoName(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 수량 수정", description = "상품 재고 수량을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_SUCCESS_EXAMPLE)))
    })
    @PatchMapping("/amount")
    public ResponseEntity<BaseResponse<String>> updateAmount(@RequestBody UpdateItemAmountReq dto) {
        itemService.updateAmount(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 가격 수정", description = "상품 단가를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_SUCCESS_EXAMPLE)))
    })
    @PatchMapping("/unit_price")
    public ResponseEntity<BaseResponse<String>> updateUnitPrice(@RequestBody UpdateItemUnitPriceReq dto) {
        itemService.updateUnitPrice(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 제조사 수정", description = "상품 제조사를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_SUCCESS_EXAMPLE)))
    })
    @PatchMapping("/company")
    public ResponseEntity<BaseResponse<String>> updateCompany(@RequestBody UpdateItemCompanyReq dto) {
        itemService.updateCompany(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 카테고리 수정", description = "상품 카테고리를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_SUCCESS_EXAMPLE)))
    })
    @PatchMapping("/category")
    public ResponseEntity<BaseResponse<String>> updateCategory(@RequestBody UpdateItemCategoryReq dto) {
        itemService.updateCategory(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 이미지 수정", description = "상품 이미지를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_SUCCESS_EXAMPLE)))
    })
    @PatchMapping("/images")
    public ResponseEntity<BaseResponse<String>> updateImages(@RequestBody UpdateItemImagesReq dto) {
        itemService.updateImages(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }
}
