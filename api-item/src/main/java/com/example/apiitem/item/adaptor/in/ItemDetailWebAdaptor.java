package com.example.apiitem.item.adaptor.in;

import MeshX.common.BaseResponse;
import MeshX.common.Page.PageRes;
import MeshX.common.WebAdapter;
import com.example.apiitem.constants.ItemSwaggerConstants;
import com.example.apiitem.item.usecase.port.in.ItemDetailWebPort;
import com.example.apiitem.item.usecase.port.in.request.CreateItemDetailsCommand;
import com.example.apiitem.item.usecase.port.out.response.ItemAndItemDetailInfoDto;
import com.example.apiitem.item.usecase.port.out.response.ItemDetailsInfoListDto;
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

@WebAdapter
@RequestMapping("/api/item/detail")
@RequiredArgsConstructor
@Tag(name = "상품 상세 관리 (본사)", description = "본사의 상품 상세 (색상/사이즈 조합) 관리 API")
public class ItemDetailWebAdaptor {
    private final ItemDetailWebPort itemDetailWebPort;

    @Operation(summary = "상품 상세 ID로 조회", description = "상품 상세 ID를 사용하여 색상/사이즈 조합 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_DETAIL_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품 상세를 찾을 수 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ItemAndItemDetailInfoDto>> getItemDetailInfoById(
            @Parameter(description = "조회할 상품 상세의 ID", required = true, example = "1")
            @PathVariable Integer id) {
        ItemAndItemDetailInfoDto result = itemDetailWebPort.findItemDetailById(id);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "상품 상세 코드로 조회", description = "상품 상세 코드를 사용하여 색상/사이즈 조합 정보를 조회합니다. (예: TS2025001-BK-M)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_DETAIL_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품 상세를 찾을 수 없음")
    })
    @GetMapping("/detailcode/{detailCode}")
    public ResponseEntity<BaseResponse<ItemAndItemDetailInfoDto>> getItemDetailInfoByDetailCode(
            @Parameter(description = "조회할 상품 상세 코드 (상품코드-색상-사이즈)", required = true, example = "TS2025001-BK-M")
            @PathVariable String detailCode) {
        ItemAndItemDetailInfoDto result = itemDetailWebPort.findItemDetailByItemDetailCode(detailCode);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "상품별 상세 목록 조회", description = "특정 상품의 모든 색상/사이즈 조합 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 상세 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_DETAIL_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @GetMapping("/item/{id}")
    public ResponseEntity<BaseResponse<ItemDetailsInfoListDto>> getItemDetailsByItem(
            @Parameter(description = "조회할 상품의 ID", required = true, example = "1")
            @PathVariable Integer id) {
        ItemDetailsInfoListDto result = itemDetailWebPort.findItemDetailsByItem(id);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "상품 상세 일괄 등록", description = "하나의 상품에 대한 여러 색상/사이즈 조합을 일괄 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 상세 등록 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_DETAIL_CREATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @PostMapping("/creates")
    public ResponseEntity<BaseResponse<String>> saveDetails(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "상품 상세 일괄 등록 정보",
                    content = @Content(examples = @ExampleObject(value = ItemSwaggerConstants.ITEM_DETAIL_CREATE_REQ_EXAMPLE)))
            @RequestBody CreateItemDetailsCommand dto) {
        itemDetailWebPort.saveItemDetails(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }
}
