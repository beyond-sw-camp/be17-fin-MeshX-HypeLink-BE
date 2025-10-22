package MeshX.HypeLink.head_office.promotion.model.dto.response;

import MeshX.HypeLink.head_office.promotion.model.entity.ItemCategory;
import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

@Getter
public class PromotionInfoRes {
    private PromotionType promotionType;
    private ItemCategory category;
    private Integer id;
    private String title;
    private String contents;
    private Double discountRate;    // 할인율

    private LocalDate startDate;    // 할인 시작 시정
    private LocalDate endDate;      // 할인 종료 시점

    public static PromotionInfoRes toDto(Promotion entity){
        return PromotionInfoRes.builder()
                .id(entity.getId())
                .promotionType(entity.getPromotionType())
                .category(entity.getCategory())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .discountRate(entity.getDiscountRate())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }
    @Builder
    private PromotionInfoRes(PromotionType promotionType, ItemCategory category,  String title, String contents, Double discountRate, LocalDate startDate, LocalDate endDate, Integer id){
        this.promotionType = promotionType;
        this.category = category;
        this.title = title;
        this.contents = contents;
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
    }
    public static Page<PromotionInfoRes> toDtoPage(Page<Promotion>  page) {
        return page.map(PromotionInfoRes::toDto);
    }

}
