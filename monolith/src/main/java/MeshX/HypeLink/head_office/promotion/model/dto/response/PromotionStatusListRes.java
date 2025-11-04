package MeshX.HypeLink.head_office.promotion.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PromotionStatusListRes {
    private List<PromotionStatusInfoRes> promotionStatusInfos;
}
