package MeshX.HypeLink.head_office.promotion.model.dto.response;

import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PromotionInfoListRes {
    private List<PromotionInfoRes> promotions;

    public static PromotionInfoListRes toDto(List<Promotion> entity, java.util.function.Function<MeshX.HypeLink.image.model.entity.Image, String> urlGenerator){
        return PromotionInfoListRes.builder()
                .promotions(entity.stream()
                        .map(promotion -> PromotionInfoRes.toDto(promotion, urlGenerator))
                        .toList()
                )
                .build();
    }
    @Builder
    private PromotionInfoListRes(List<PromotionInfoRes> promotions){
        this.promotions = promotions;
    }
}
