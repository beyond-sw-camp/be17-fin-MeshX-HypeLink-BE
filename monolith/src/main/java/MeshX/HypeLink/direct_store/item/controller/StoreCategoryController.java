package MeshX.HypeLink.direct_store.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.constansts.DirectStoreSwaggerConstants;
import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreCategoriesReq;
import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreCategoryReq;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreCategoryInfoListRes;
import MeshX.HypeLink.direct_store.item.service.StoreCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "직영점 카테고리 관리", description = "직영점의 상품 카테고리 관리 API")
@RestController
@RequestMapping("/api/direct/category")
@RequiredArgsConstructor
public class StoreCategoryController {
    private final StoreCategoryService storeCategoryService;

    @Operation(summary = "카테고리 일괄 저장", description = "직영점의 카테고리 목록을 일괄 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "저장 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.CATEGORY_SAVE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
    })
    @PostMapping("/save/all")
    public ResponseEntity<BaseResponse<String>> saveCategoryList(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "저장할 카테고리 목록",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaveStoreCategoriesReq.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.SAVE_STORE_CATEGORIES_REQ_EXAMPLE)
                    )
            )
            @RequestBody SaveStoreCategoriesReq dto) {
        storeCategoryService.saveAll(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("카테고리를 저장하였습니다."));
    }

    @Operation(summary = "카테고리 목록 조회", description = "직영점의 전체 카테고리 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.STORE_CATEGORY_INFO_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<StoreCategoryInfoListRes>> getCategoryList() {
        StoreCategoryInfoListRes result = storeCategoryService.getList();
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }
}
