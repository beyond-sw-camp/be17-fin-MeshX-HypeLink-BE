package MeshX.HypeLink.head_office.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.item.constants.ItemSwaggerConstants;
import MeshX.HypeLink.head_office.item.model.dto.response.SizeInfoListRes;
import MeshX.HypeLink.head_office.item.service.SizeService;
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

@Tag(name = "사이즈 관리", description = "상품 사이즈 조회 API")
@RestController
@RequestMapping("/api/size")
@RequiredArgsConstructor
public class SizeController {
    private final SizeService sizeService;

    @Operation(summary = "전체 사이즈 조회", description = "모든 사이즈 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.SIZE_INFO_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/all")
    public ResponseEntity<BaseResponse<SizeInfoListRes>> getSizes() {
        SizeInfoListRes dto = sizeService.findAll();
        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }
}
