package MeshX.HypeLink.head_office.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.item.constansts.SwaggerConstants;
import MeshX.HypeLink.head_office.item.model.dto.request.CreateItemReq;
import MeshX.HypeLink.head_office.item.model.dto.request.UpdateItemContentReq;
import MeshX.HypeLink.head_office.item.model.dto.request.UpdateItemStockReq;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemDetailInfoRes;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemInfoListRes;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemUpdateInfoRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "본사 상품 마스터", description = "본사에서 관리하는 전체 상품의 마스터 데이터를 관리하기 위한 API")
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    @Operation(summary = "상품 상세 정보 조회 (ID)", description = "상품의 고유 ID를 사용하여 특정 상품의 모든 상세 정보를 조회합니다. 재고, 가격, 설명 등 전체 데이터를 포함합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 상세 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.ITEM_DETAIL_INFO_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 상품을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ItemDetailInfoRes>> getItemInfo(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemDetailInfoRes.builder().build()));
    }

    @Operation(summary = "상품 상세 정보 조회 (상품 코드)", description = "상품의 고유 코드를 사용하여 특정 상품의 모든 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 상세 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.ITEM_DETAIL_INFO_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "해당 코드의 상품을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/code/{code}")
    public ResponseEntity<BaseResponse<ItemDetailInfoRes>> getItemInfoFromCode(@PathVariable String code) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemDetailInfoRes.builder().build()));
    }

    @Operation(summary = "카테고리별 상품 목록 조회", description = "특정 카테고리에 속한 모든 상품의 기본 정보 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.ITEM_INFO_LIST_RESPONSE)))
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<BaseResponse<ItemInfoListRes>> getItemsfromCategory(@PathVariable String category) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemInfoListRes.builder().build()));
    }

    @Operation(summary = "제조사별 상품 목록 조회", description = "특정 제조사가 생산한 모든 상품의 기본 정보 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.ITEM_INFO_LIST_RESPONSE)))
    })
    @GetMapping("/company/{company}")
    public ResponseEntity<BaseResponse<ItemInfoListRes>> getItemsfromCompany(@PathVariable String company) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemInfoListRes.builder().build()));
    }

    @Operation(summary = "상품명 검색", description = "상품명에 특정 키워드가 포함된 모든 상품의 기본 정보 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.ITEM_INFO_LIST_RESPONSE)))
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<BaseResponse<ItemInfoListRes>> getItemsfromName(@PathVariable String name) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemInfoListRes.builder().build()));
    }

    @Operation(summary = "신규 상품 마스터 등록", description = "새로운 상품 마스터를 시스템에 등록합니다. 상품의 기본 정보와 함께 사이즈, 색상별 재고 정보를 포함합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 등록 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.ITEM_DETAIL_INFO_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<ItemDetailInfoRes>> createItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "등록할 상품의 상세 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateItemReq.class),
                            examples = @ExampleObject(value = SwaggerConstants.CREATE_ITEM_REQUEST)
                    )
            )
            @RequestBody CreateItemReq dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.of(ItemDetailInfoRes.builder().build()));
    }

    @Operation(summary = "상품 마스터 설명 수정", description = "기존 상품 마스터의 설명(content)을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 정보 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.ITEM_UPDATE_INFO_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/content")
    public ResponseEntity<BaseResponse<ItemUpdateInfoRes>> updateContents(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정할 상품의 코드와 새로운 설명",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateItemContentReq.class),
                            examples = @ExampleObject(value = SwaggerConstants.UPDATE_ITEM_CONTENT_REQUEST)
                    )
            )
            @RequestBody UpdateItemContentReq dto) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemUpdateInfoRes.builder().build()));
    }

    @Operation(summary = "상품 마스터 재고 수정", description = "특정 상품 코드에 해당하는 상품의 재고 수량을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재고 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.ITEM_UPDATE_INFO_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/stock")
    public ResponseEntity<BaseResponse<ItemUpdateInfoRes>> updateStock(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정할 상품의 코드와 새로운 재고 수량",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateItemStockReq.class),
                            examples = @ExampleObject(value = SwaggerConstants.UPDATE_ITEM_STOCK_REQUEST)
                    )
            )
            @RequestBody UpdateItemStockReq dto) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemUpdateInfoRes.builder().build()));
    }

    @Operation(summary = "상품 마스터 삭제", description = "상품 ID를 사용하여 시스템에서 상품 마스터 정보를 완전히 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 ID의 상품을 찾을 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteItem(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(BaseResponse.of(""));
    }
}
