package MeshX.HypeLink.head_office.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.item.constansts.ItemSwaggerConstants;
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

@Tag(name = "본사 상품 관리", description = "본사에서 상품 정보를 관리하는 API (조회, 생성, 수정)")
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "상품 ID로 조회", description = "특정 상품 ID를 사용하여 상품 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 상품을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ItemInfoRes>> getItemInfo(
            @Parameter(description = "상품 ID", required = true)
            @PathVariable Integer id) {
        ItemInfoRes dto = itemService.findItemById(id);
        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }

    @Operation(summary = "상품 코드로 조회", description = "상품 코드를 사용하여 상품 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 코드의 상품을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/code/{code}")
    public ResponseEntity<BaseResponse<ItemInfoRes>> getItemInfoByCode(
            @Parameter(description = "상품 코드", required = true)
            @PathVariable String code) {
        ItemInfoRes dto = itemService.findItemsByItemCode(code);
        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }

    @Operation(summary = "상품 목록 조회 (페이징)", description = "키워드와 카테고리로 필터링하여 상품 목록을 페이징 형태로 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_PAGE_RES_EXAMPLE)))
    })
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<PageRes<ItemInfoRes>>> getItems(
            @Parameter(description = "페이징 정보 (page, size, sort)") Pageable pageable,
            @Parameter(description = "검색 키워드", required = true) @RequestParam String keyWord,
            @Parameter(description = "카테고리", required = true) @RequestParam String category) {
        PageRes<ItemInfoRes> items = itemService.findItemsWithPaging(pageable, keyWord, category);
        return ResponseEntity.ok(BaseResponse.of(items));
    }

    @Operation(summary = "카테고리별 상품 조회", description = "특정 카테고리의 상품 목록을 페이징 형태로 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_PAGE_RES_EXAMPLE)))
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<BaseResponse<PageRes<ItemInfoRes>>> getItemsByCategory(
            @Parameter(description = "카테고리명", required = true) @PathVariable String category,
            @Parameter(description = "페이징 정보") Pageable pageable) {
        PageRes<ItemInfoRes> items = itemService.findItemsByCategoryWithPaging(category, pageable);
        return ResponseEntity.status(200).body(BaseResponse.of(items));
    }

    @Operation(summary = "상품명으로 조회", description = "상품명으로 검색하여 상품 목록을 페이징 형태로 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_PAGE_RES_EXAMPLE)))
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<BaseResponse<PageRes<ItemInfoRes>>> getItemsByName(
            @Parameter(description = "상품명", required = true) @PathVariable String name,
            @Parameter(description = "페이징 정보") Pageable pageable) {
        PageRes<ItemInfoRes> items = itemService.findItemsByNameWithPaging(name, pageable);
        return ResponseEntity.status(200).body(BaseResponse.of(items));
    }

    @Operation(summary = "상품 코드 유효성 검사", description = "상품 코드가 시스템에 존재하는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품이 존재합니다",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.VALIDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/validate")
    public ResponseEntity<BaseResponse<String>> getItemsByName(
            @Parameter(description = "상품 코드", required = true) @RequestParam String itemCode) {
        itemService.validateItem(itemCode);
        return ResponseEntity.status(200).body(BaseResponse.of("아이템이 존재합니다."));
    }

    @Operation(summary = "상품 생성", description = "새로운 상품을 시스템에 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 생성 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.CREATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 생성 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateItemReq.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.CREATE_ITEM_REQ_EXAMPLE)
                    )
            )
            @RequestBody CreateItemReq dto) {
        itemService.saveItem(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("result"));
    }

    @Operation(summary = "상품 저장 (동기화)", description = "상품 정보를 저장하거나 동기화합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "동기화 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.SYNC_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping("/save")
    public ResponseEntity<BaseResponse<String>> saveItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 저장 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaveItemReq.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.SAVE_ITEM_REQ_EXAMPLE)
                    )
            )
            @RequestBody SaveItemReq dto) {
        itemService.saveItem(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("동기화가 완료되었습니다."));
    }

    @Operation(summary = "상품 설명 수정", description = "상품의 설명(contents)을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/content")
    public ResponseEntity<BaseResponse<String>> updateContents(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 설명 수정 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateItemContentReq.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_ITEM_CONTENT_REQ_EXAMPLE)
                    )
            )
            @RequestBody UpdateItemContentReq dto) {
        itemService.updateContents(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 영문명 수정", description = "상품의 영문명을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/en_name")
    public ResponseEntity<BaseResponse<String>> updateEnName(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 영문명 수정 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateItemEnNameReq.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_ITEM_EN_NAME_REQ_EXAMPLE)
                    )
            )
            @RequestBody UpdateItemEnNameReq dto) {
        itemService.updateEnName(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 한글명 수정", description = "상품의 한글명을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/ko_name")
    public ResponseEntity<BaseResponse<String>> updateKoName(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 한글명 수정 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateItemKoNameReq.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_ITEM_KO_NAME_REQ_EXAMPLE)
                    )
            )
            @RequestBody UpdateItemKoNameReq dto) {
        itemService.updateKoName(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 수량 수정", description = "상품의 재고 수량을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/amount")
    public ResponseEntity<BaseResponse<String>> updateAmount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 수량 수정 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateItemAmountReq.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_ITEM_AMOUNT_REQ_EXAMPLE)
                    )
            )
            @RequestBody UpdateItemAmountReq dto) {
        itemService.updateAmount(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 단가 수정", description = "상품의 단가(가격)를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/unit_price")
    public ResponseEntity<BaseResponse<String>> updateUnitPrice(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 단가 수정 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateItemUnitPriceReq.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_ITEM_UNIT_PRICE_REQ_EXAMPLE)
                    )
            )
            @RequestBody UpdateItemUnitPriceReq dto) {
        itemService.updateUnitPrice(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 제조사 수정", description = "상품의 제조사 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/company")
    public ResponseEntity<BaseResponse<String>> updateCompany(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 제조사 수정 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateItemCompanyReq.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_ITEM_COMPANY_REQ_EXAMPLE)
                    )
            )
            @RequestBody UpdateItemCompanyReq dto) {
        itemService.updateCompany(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 카테고리 수정", description = "상품의 카테고리를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/category")
    public ResponseEntity<BaseResponse<String>> updateCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 카테고리 수정 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateItemCategoryReq.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_ITEM_CATEGORY_REQ_EXAMPLE)
                    )
            )
            @RequestBody UpdateItemCategoryReq dto) {
        itemService.updateCategory(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 이미지 수정", description = "상품의 이미지 목록을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/images")
    public ResponseEntity<BaseResponse<String>> updateImages(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 이미지 수정 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateItemImagesReq.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.UPDATE_ITEM_IMAGES_REQ_EXAMPLE)
                    )
            )
            @RequestBody UpdateItemImagesReq dto) {
        itemService.updateImages(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }
}
