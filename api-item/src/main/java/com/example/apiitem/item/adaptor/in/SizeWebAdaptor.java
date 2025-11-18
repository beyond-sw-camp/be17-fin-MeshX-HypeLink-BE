package com.example.apiitem.item.adaptor.in;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiitem.constants.ItemSwaggerConstants;
import com.example.apiitem.item.usecase.port.in.SizeWebPort;
import com.example.apiitem.item.usecase.port.out.response.SizeInfoListDto;
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
@RequestMapping("/api/size")
@RequiredArgsConstructor
@Tag(name = "사이즈 조회 (본사)", description = "의류 사이즈 목록 조회 API")
public class SizeWebAdaptor {
    private final SizeWebPort sizeWebPort;

    @Operation(summary = "전체 사이즈 목록 조회", description = "의류 상품에 사용 가능한 모든 사이즈 목록을 조회합니다. (S, M, L, XL, XXL, FREE 등)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사이즈 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.SIZE_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/all")
    public ResponseEntity<BaseResponse<SizeInfoListDto>> getSizes() {
        SizeInfoListDto dto = sizeWebPort.findAll();
        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }
}
