package com.example.apiitem.item.adaptor.in;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiitem.constants.ItemSwaggerConstants;
import com.example.apiitem.item.usecase.port.in.ColorWebPort;
import com.example.apiitem.item.usecase.port.out.response.ColorInfoListDto;
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
@RequestMapping("/api/color")
@RequiredArgsConstructor
@Tag(name = "색상 조회 (본사)", description = "의류 색상 목록 조회 API")
public class ColorWebAdaptor {
    private final ColorWebPort colorWebPort;

    @Operation(summary = "전체 색상 목록 조회", description = "의류 상품에 사용 가능한 모든 색상 목록을 조회합니다. (블랙, 화이트, 네이비, 그레이, 베이지 등)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "색상 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.COLOR_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/all")
    public ResponseEntity<BaseResponse<ColorInfoListDto>> getCategories() {
        ColorInfoListDto dto = colorWebPort.findAll();

        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }
}
