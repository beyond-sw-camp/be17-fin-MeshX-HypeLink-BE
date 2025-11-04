package MeshX.HypeLink.head_office.promotion.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.promotion.model.dto.request.PromotionCreateReq;
import MeshX.HypeLink.head_office.promotion.model.dto.request.PromotionUpdateReq;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoListRes;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoRes;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionStatusListRes;
import MeshX.HypeLink.head_office.promotion.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promotion")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createPromotion(@RequestBody PromotionCreateReq dto) {
        promotionService.createPromotion(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("프로모션이 생성되었습니다."));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<PromotionInfoListRes>> readPromotions() {
        PromotionInfoListRes promotionInfoListRes = promotionService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(promotionInfoListRes));
    }

    @GetMapping("/read/page/all")
    public ResponseEntity<BaseResponse<PageRes<PromotionInfoRes>>> readPromotions(Pageable pageReq) {
        PageRes<PromotionInfoRes> pageRes = promotionService.readList(pageReq);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<PromotionInfoRes>> readPromotion(@PathVariable Integer id) {
        PromotionInfoRes promotionInfoRes = promotionService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(promotionInfoRes));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deletePromotion(@PathVariable Integer id) {
        promotionService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("프로모션이 성공적으로 삭제 되었습니다."));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<PromotionInfoRes>> updatePromotion(@PathVariable Integer id,
                                                                          @RequestBody PromotionUpdateReq dto) {
        PromotionInfoRes promotionInfoRes = promotionService.update(id, dto.getTitle(), dto.getContents(), dto.getStartDate(), dto.getEndDate(), dto.getStatus(), dto.getCouponId(), dto.getImages());
        return ResponseEntity.status(200).body(BaseResponse.of(promotionInfoRes));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<PageRes<PromotionInfoRes>>> searchPromotion(
            String keyword,
            String status,
            Pageable pageReq) {
        PageRes<PromotionInfoRes> pageRes = promotionService.search(keyword, status, pageReq);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @GetMapping("/status")
    public ResponseEntity<BaseResponse<PromotionStatusListRes>> readStatus(){
        PromotionStatusListRes result = promotionService.readStatus();
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

}
