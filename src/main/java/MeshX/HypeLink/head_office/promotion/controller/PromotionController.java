package MeshX.HypeLink.head_office.promotion.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.promotion.constansts.SwaggerConstants;
import MeshX.HypeLink.head_office.promotion.model.dto.request.PromotionCreateReq;
import MeshX.HypeLink.head_office.promotion.model.dto.request.PromotionUpdateReq;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoListRes;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoRes;
import MeshX.HypeLink.head_office.promotion.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "본사 프로모션 관리", description = "전사, 특정 매장, 카테고리, 상품에 대한 프로모션(할인)을 생성, 조회, 수정, 삭제하는 API")
@RestController
@RequestMapping("/api/promotion")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @Operation(summary = "프로모션 생성", description = "새로운 프로모션을 시스템에 등록합니다. 프로모션 유형(전체, 매장, 카테고리, 상품)에 따라 적용 대상이 달라집니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "프로모션 생성 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createPromotion(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "생성할 프로모션의 상세 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PromotionCreateReq.class),
                            examples = @ExampleObject(value = SwaggerConstants.PROMOTION_CREATE_REQUEST)
                    )
            )
            @RequestBody PromotionCreateReq dto) {
        promotionService.createPromotion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.of("프로모션이 생성되었습니다."));
    }

    @Operation(summary = "모든 프로모션 목록 조회", description = "현재 시스템에 등록된 모든 프로모션 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로모션 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.PROMOTION_INFO_LIST_RESPONSE)))
    })
    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<PromotionInfoListRes>> readPromotions(){
        PromotionInfoListRes promotionInfoListRes = promotionService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(promotionInfoListRes));
    }

    @Operation(summary = "프로모션 페이징 목록 조회", description = "프로모션 목록을 지정된 페이지 크기와 정렬 순서에 따라 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로모션 페이징 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.PROMOTION_PAGE_RESPONSE)))
    })
    @GetMapping("/read/page/all")
    public ResponseEntity<BaseResponse<PageRes<PromotionInfoRes>>> readPromotionsAsPage(@ParameterObject PageReq pageReq) {
        PageRes<PromotionInfoRes> pageRes = promotionService.readList(pageReq);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @Operation(summary = "프로모션 삭제", description = "프로모션 ID를 사용하여 특정 프로모션을 시스템에서 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로모션 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 프로모션을 찾을 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deletePromotion(@Parameter(description = "삭제할 프로모션의 ID") @PathVariable Integer id){
        promotionService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("프로모션이 성공적으로 삭제 되었습니다."));
    }

    @Operation(summary = "프로모션 수정", description = "기존 프로모션의 정보를 수정합니다. 프로모션 ID를 통해 대상을 지정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로모션 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.PROMOTION_INFO_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 ID의 프로모션을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<PromotionInfoRes>> updatePromotion(
            @Parameter(description = "수정할 프로모션의 ID") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정할 프로모션의 상세 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PromotionUpdateReq.class),
                            examples = @ExampleObject(value = SwaggerConstants.PROMOTION_UPDATE_REQUEST)
                    )
            )
            @RequestBody PromotionUpdateReq dto){
        PromotionInfoRes promotionInfoRes = promotionService.update(id, dto.getPromotionType(), dto.getCategory(), dto.getTitle(), dto.getContents(), dto.getDiscountRate(), dto.getStartDate(), dto.getEndDate());
        return ResponseEntity.status(200).body(BaseResponse.of(promotionInfoRes));
    }
}
