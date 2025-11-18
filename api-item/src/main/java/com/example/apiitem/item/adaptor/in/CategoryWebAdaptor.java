package com.example.apiitem.item.adaptor.in;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiitem.constants.ItemSwaggerConstants;
import com.example.apiitem.item.usecase.port.in.CategoryWebPort;
import com.example.apiitem.item.usecase.port.out.response.CategoryInfoListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "카테고리 조회 (본사)", description = "의류 카테고리 목록 조회 API")
public class CategoryWebAdaptor {
    private final CategoryWebPort categoryWebPort;

    @Operation(summary = "전체 카테고리 목록 조회", description = "의류 상품의 모든 카테고리 목록을 조회합니다. (상의, 하의, 아우터, 원피스, 악세서리 등)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.CATEGORY_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/all")
    public ResponseEntity<BaseResponse<CategoryInfoListDto>> getCategories() {
        CategoryInfoListDto dto = categoryWebPort.findAll();

        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }
}
