package MeshX.HypeLink.head_office.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.item.constants.ItemSwaggerConstants;
import MeshX.HypeLink.head_office.item.model.dto.request.CreateItemDetailsReq;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemDetailsInfoListRes;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemAndItemDetailInfoRes;
import MeshX.HypeLink.head_office.item.service.ItemDetailService;
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

@Tag(name = "상품 상세 관리", description = "상품 상세 정보(사이즈/색상별) 관리 API")
@RestController
@RequestMapping("/api/item/detail")
@RequiredArgsConstructor
public class ItemDetailController {
    private final ItemDetailService itemDetailService;

    @Operation(summary = "상품 상세 조회 (ID)", description = "상품 상세 ID로 상품 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_DETAIL_INFO_RES_EXAMPLE)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ItemAndItemDetailInfoRes>> getItemDetailInfoById(
            @Parameter(description = "상품 상세 ID") @PathVariable Integer id) {
        ItemAndItemDetailInfoRes result = itemDetailService.findItemDetailById(id);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "상품 상세 조회 (코드)", description = "상품 상세 코드로 상품 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_DETAIL_INFO_RES_EXAMPLE)))
    })
    @GetMapping("/detailcode/{detailCode}")
    public ResponseEntity<BaseResponse<ItemAndItemDetailInfoRes>> getItemDetailInfoByDetailCode(
            @Parameter(description = "상품 상세 코드") @PathVariable String detailCode) {
        ItemAndItemDetailInfoRes result = itemDetailService.findItemDetailByItemDetailCode(detailCode);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "전체 상품 상세 조회 (페이징)", description = "모든 상품 상세 정보를 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/all")
    public ResponseEntity<BaseResponse<PageRes<ItemAndItemDetailInfoRes>>> getItemDetails(Pageable pageable) {
        PageRes<ItemAndItemDetailInfoRes> result = itemDetailService.findItemDetails(pageable);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "상품별 상세 목록 조회", description = "특정 상품의 모든 상세 정보(사이즈/색상별)를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_DETAILS_INFO_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/item/{id}")
    public ResponseEntity<BaseResponse<ItemDetailsInfoListRes>> getItemDetailsByItem(
            @Parameter(description = "상품 ID") @PathVariable Integer id) {
        ItemDetailsInfoListRes result = itemDetailService.findItemDetailsByItem(id);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "상품 상세 생성", description = "상품의 상세 정보(사이즈/색상별)를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "생성 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_UPDATE_SUCCESS_EXAMPLE)))
    })
    @PostMapping("/creates")
    public ResponseEntity<BaseResponse<String>> saveDetails(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 상세 생성 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateItemDetailsReq.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.CREATE_ITEM_DETAILS_REQ_EXAMPLE)
                    )
            )
            @RequestBody CreateItemDetailsReq dto) {
        itemDetailService.saveItemDetails(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }
}
