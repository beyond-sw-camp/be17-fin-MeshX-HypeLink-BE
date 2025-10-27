package MeshX.HypeLink.head_office.promotion.model.dto.request;

import MeshX.HypeLink.head_office.promotion.model.entity.PromotionStatus;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class PromotionUpdateReq {
    private PromotionStatus status;

    private Integer id;
    private String title;
    private String contents;
    private LocalDate startDate;    // 할인 시작 시정
    private LocalDate endDate;

    private Integer couponId;

}
