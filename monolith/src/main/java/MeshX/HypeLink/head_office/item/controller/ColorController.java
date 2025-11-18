package MeshX.HypeLink.head_office.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.item.constansts.ItemSwaggerConstants;
import MeshX.HypeLink.head_office.item.model.dto.response.ColorInfoListRes;
import MeshX.HypeLink.head_office.item.service.ColorService;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품 색상 관리", description = "상품 색상 정보를 조회하는 API")
@RestController
@RequestMapping("/api/color")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;

    @Operation(summary = "전체 색상 조회", description = "시스템에 등록된 모든 색상 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "색상 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.COLOR_INFO_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/all")
    public ResponseEntity<BaseResponse<ColorInfoListRes>> getCategories() {
        ColorInfoListRes dto = colorService.findAll();

        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }
}
