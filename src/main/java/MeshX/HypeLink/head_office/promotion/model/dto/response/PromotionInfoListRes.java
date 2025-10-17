package MeshX.HypeLink.head_office.promotion.model.dto.response;

import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import lombok.Builder;

import java.util.List;

public class PromotionInfoListRes {
    private List<PromotionInfoRes> promotions;

    public static PromotionInfoListRes toDto(List<Promotion> entity){
        return PromotionInfoListRes.builder()
                .promotions(entity.stream()
                        .map(PromotionInfoRes::toDto)
                        .toList()
                )
                .build();
    }
    @Builder
    private PromotionInfoListRes(List<PromotionInfoRes> promotions){
        this.promotions = promotions;
    }
}
