package com.example.apidirect.item.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apidirect.constants.DirectSwaggerConstants;
import com.example.apidirect.item.adapter.in.web.dto.request.SaveStoreCategoriesRequest;
import com.example.apidirect.item.usecase.port.in.ItemCommandPort;
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

@Tag(name = "카테고리 관리", description = "매장 상품 카테고리 관리 API")
@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/direct/category")
public class CategoryWebAdapter {

    private final ItemCommandPort itemCommandPort;

    @Operation(summary = "본사 카테고리 동기화", description = "본사의 카테고리 리스트를 매장으로 동기화합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 저장 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.CATEGORY_SAVE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content)
    })
    @PostMapping("/save/all")
    public ResponseEntity<BaseResponse<String>> saveCategoriesFromHeadOffice(@RequestBody SaveStoreCategoriesRequest request) {
        itemCommandPort.saveAllCategoriesFromHeadOffice(request);
        return ResponseEntity.ok(BaseResponse.of("카테고리를 저장하였습니다."));
    }
}
