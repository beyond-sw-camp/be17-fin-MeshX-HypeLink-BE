package com.example.apiitem.item.adaptor.in;

import MeshX.common.BaseResponse;
import MeshX.common.Page.PageRes;
import MeshX.common.WebAdapter;
import com.example.apiitem.constants.ItemSwaggerConstants;
import com.example.apiitem.item.usecase.port.in.ItemWebPort;
import com.example.apiitem.item.usecase.port.in.request.*;
import com.example.apiitem.item.usecase.port.out.response.ItemInfoDto;
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

@WebAdapter // = @RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
@Tag(name = "상품 관리", description = "본사의 의류 상품 등록, 조회, 수정 등 상품 관리 API")
public class ItemWebAdaptor {
    private final ItemWebPort itemWebPort;

    @Operation(summary = "상품 ID로 조회", description = "상품 ID를 사용하여 의류 상품의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 조회 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ItemInfoDto>> getItemInfo(
            @Parameter(description = "조회할 상품의 ID", required = true, example = "1")
            @PathVariable Integer id) {
        ItemInfoDto dto = itemWebPort.findItemById(id);
        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }

    @Operation(summary = "상품 코드로 조회", description = "상품 코드를 사용하여 의류 상품의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 조회 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @GetMapping("/code/{code}")
    public ResponseEntity<BaseResponse<ItemInfoDto>> getItemInfoByCode(
            @Parameter(description = "조회할 상품의 코드", required = true, example = "TS2025001")
            @PathVariable String code) {
        ItemInfoDto dto = itemWebPort.findItemsByItemCode(code);
        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }

    @Operation(summary = "상품 목록 조회", description = "검색어와 카테고리로 의류 상품 목록을 페이징 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<PageRes<ItemInfoDto>>> getItems(
            Pageable pageable,
            @Parameter(description = "검색 키워드 (상품명, 상품코드 등)", required = true, example = "티셔츠")
            @RequestParam String keyWord,
            @Parameter(description = "카테고리 필터 (전체: ALL, 상의: TOP, 하의: BOTTOM, 아우터: OUTER)", required = true, example = "ALL")
            @RequestParam String category) {
        PageRes<ItemInfoDto> items = itemWebPort.findItemsWithPaging(pageable, keyWord, category);
        return ResponseEntity.ok(BaseResponse.of(items));
    }

    @Operation(summary = "상품 코드 유효성 검증", description = "상품 코드의 중복 여부를 검증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유효성 검증 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_VALIDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "중복된 상품 코드")
    })
    @GetMapping("/validate")
    public ResponseEntity<BaseResponse<String>> validateItem(
            @Parameter(description = "검증할 상품 코드", required = true, example = "TS2025001")
            @RequestParam String itemCode) {
        itemWebPort.validate(itemCode);
        return ResponseEntity.ok(BaseResponse.of("저장이 완료되었습니다."));
    }

    @Operation(summary = "상품 등록", description = "새로운 의류 상품을 등록합니다. (상의, 하의, 아우터, 원피스, 악세서리 등)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 등록 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_CREATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "상품 등록 정보",
                    content = @Content(examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_CREATE_REQ_EXAMPLE)))
            @RequestBody CreateItemCommand dto) {
        itemWebPort.saveItem(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("result"));
    }

    @Operation(summary = "상품 설명 수정", description = "의류 상품의 상세 설명을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @PatchMapping("/content")
    public ResponseEntity<BaseResponse<String>> updateContents(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "상품 설명 수정 정보",
                    content = @Content(examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_CONTENT_REQ_EXAMPLE)))
            @RequestBody UpdateItemContentCommand dto) {
        itemWebPort.updateContents(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 영문명 수정", description = "의류 상품의 영문 이름을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @PatchMapping("/en_name")
    public ResponseEntity<BaseResponse<String>> updateEnName(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "상품 영문명 수정 정보")
            @RequestBody UpdateItemEnNameCommand dto) {
        itemWebPort.updateEnName(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 한글명 수정", description = "의류 상품의 한글 이름을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @PatchMapping("/ko_name")
    public ResponseEntity<BaseResponse<String>> updateKoName(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "상품 한글명 수정 정보",
                    content = @Content(examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_NAME_REQ_EXAMPLE)))
            @RequestBody UpdateItemKoNameCommand dto) {
        itemWebPort.updateKoName(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 재고수량 수정", description = "의류 상품의 재고 수량을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @PatchMapping("/amount")
    public ResponseEntity<BaseResponse<String>> updateAmount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "상품 재고수량 수정 정보")
            @RequestBody UpdateItemAmountCommand dto) {
        itemWebPort.updateAmount(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 단가 수정", description = "의류 상품의 단가를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @PatchMapping("/unit_price")
    public ResponseEntity<BaseResponse<String>> updateUnitPrice(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "상품 단가 수정 정보")
            @RequestBody UpdateItemUnitPriceCommand dto) {
        itemWebPort.updateUnitPrice(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 제조사 수정", description = "의류 상품의 제조사 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @PatchMapping("/company")
    public ResponseEntity<BaseResponse<String>> updateCompany(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "상품 제조사 수정 정보")
            @RequestBody UpdateItemCompanyCommand dto) {
        itemWebPort.updateCompany(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 카테고리 수정", description = "의류 상품의 카테고리를 수정합니다. (상의, 하의, 아우터, 원피스, 악세서리)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @PatchMapping("/category")
    public ResponseEntity<BaseResponse<String>> updateCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "상품 카테고리 수정 정보")
            @RequestBody UpdateItemCategoryCommand dto) {
        itemWebPort.updateCategory(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 이미지 수정", description = "의류 상품의 이미지 목록을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @PatchMapping("/images")
    public ResponseEntity<BaseResponse<String>> updateImages(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "상품 이미지 수정 정보")
            @RequestBody UpdateItemImagesCommand dto) {
        itemWebPort.updateImages(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }
}
