package MeshX.HypeLink.head_office.promotion.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.promotion.constants.PromotionSwaggerConstants;
import MeshX.HypeLink.head_office.promotion.model.dto.request.PromotionCreateReq;
import MeshX.HypeLink.head_office.promotion.model.dto.request.PromotionUpdateReq;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoListRes;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoRes;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionStatusListRes;
import MeshX.HypeLink.head_office.promotion.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "본부 프로모션 관리", description = "본부에서 프로모션을 관리하는 API")
@RestController
@RequestMapping("/api/promotion")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @Operation(summary = "프로모션 생성", description = "새로운 프로모션을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "생성 성공")
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createPromotion(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = PromotionSwaggerConstants.PROMOTION_CREATE_REQ_EXAMPLE)))
            @RequestBody PromotionCreateReq dto) {
        promotionService.createPromotion(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("프로모션이 생성되었습니다."));
    }

    @Operation(summary = "프로모션 전체 목록 조회", description = "모든 프로모션을 조회합니다.")
    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<PromotionInfoListRes>> readPromotions() {
        PromotionInfoListRes promotionInfoListRes = promotionService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(promotionInfoListRes));
    }

    @Operation(summary = "프로모션 목록 조회 (페이징)", description = "프로모션 목록을 페이징하여 조회합니다.")
    @GetMapping("/read/page/all")
    public ResponseEntity<BaseResponse<PageRes<PromotionInfoRes>>> readPromotions(Pageable pageReq) {
        PageRes<PromotionInfoRes> pageRes = promotionService.readList(pageReq);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @Operation(summary = "프로모션 상세 조회", description = "특정 프로모션을 상세 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(examples = @ExampleObject(value = PromotionSwaggerConstants.PROMOTION_INFO_RES_EXAMPLE)))
    })
    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<PromotionInfoRes>> readPromotion(
            @Parameter(description = "프로모션 ID") @PathVariable Integer id) {
        PromotionInfoRes promotionInfoRes = promotionService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(promotionInfoRes));
    }

    @Operation(summary = "프로모션 삭제", description = "프로모션을 삭제합니다.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deletePromotion(
            @Parameter(description = "프로모션 ID") @PathVariable Integer id) {
        promotionService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("프로모션이 성공적으로 삭제 되었습니다."));
    }

    @Operation(summary = "프로모션 수정", description = "프로모션 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(examples = @ExampleObject(value = PromotionSwaggerConstants.PROMOTION_INFO_RES_EXAMPLE)))
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<PromotionInfoRes>> updatePromotion(@PathVariable Integer id,
                                                                          @RequestBody PromotionUpdateReq dto) {
        PromotionInfoRes promotionInfoRes = promotionService.update(id, dto.getTitle(), dto.getContents(), dto.getStartDate(), dto.getEndDate(), dto.getStatus(), dto.getCouponId(), dto.getImages());
        return ResponseEntity.status(200).body(BaseResponse.of(promotionInfoRes));
    }

    @Operation(summary = "프로모션 검색", description = "키워드와 상태로 프로모션을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<PageRes<PromotionInfoRes>>> searchPromotion(
            @Parameter(description = "검색 키워드") String keyword,
            @Parameter(description = "프로모션 상태") String status,
            Pageable pageReq) {
        PageRes<PromotionInfoRes> pageRes = promotionService.search(keyword, status, pageReq);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @Operation(summary = "프로모션 상태 목록 조회", description = "프로모션 상태 목록을 조회합니다.")
    @GetMapping("/status")
    public ResponseEntity<BaseResponse<PromotionStatusListRes>> readStatus(){
        PromotionStatusListRes result = promotionService.readStatus();
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

}
