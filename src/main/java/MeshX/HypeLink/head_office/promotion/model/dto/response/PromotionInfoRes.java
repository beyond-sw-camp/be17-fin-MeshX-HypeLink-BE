package MeshX.HypeLink.head_office.promotion.model.dto.response;

import MeshX.HypeLink.head_office.promotion.model.entity.ItemCategory;
import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionStatus;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PromotionInfoRes {
    private PromotionType promotionType;
    private Integer id;
    private String title;
    private String contents;
    private Double discountRate;    // 할인율
    private LocalDate startDate;    // 할인 시작 시정
    private LocalDate endDate;      // 할인 종료 시점
    private PromotionStatus status;

    private ItemCategory category; // CATEGORY 전용
    private List<Integer> storeIds;     // STORE 전용
    private Integer itemId;             // PRODUCT 전용-

    public static PromotionInfoRes toDto(Promotion entity) {
        PromotionInfoResBuilder builder = PromotionInfoRes.builder()
                .id(entity.getId())
                .promotionType(entity.getPromotionType())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .discountRate(entity.getDiscountRate())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus());

        // 타입별 매핑 분기
        switch (entity.getPromotionType()) {
            case STORE -> {
                List<Integer> storeIds = entity.getPromotionStore().stream()
                        .map(link -> link.getStore().getId())
                        .toList();
                builder.storeIds(storeIds);
            }

            case PRODUCT -> {
                entity.getPromotionItems().stream().findFirst().ifPresent(link -> {
                    builder.itemId(link.getItem().getId());
                }); //하나만 뽑음
            }

            case CATEGORY -> {
                entity.getPromotionCategories().stream().findFirst().ifPresent(link -> {
                    builder.category(link.getCategory());
                }); //하나만 뽑음
            }

            case ALL -> {
//                // ✅ ALL도 본사(store_id = 1)가 PromotionStore에 연결되어 있으므로 동일하게 처리
//                List<Integer> storeIds = entity.getPromotionStore().stream()
//                        .map(link -> link.getStore().getId())
//                        .toList();
//                builder.storeIds(storeIds);
                builder.storeIds(List.of(1));
            }
        }

        return builder.build();
    }

     @Builder
    private PromotionInfoRes(PromotionType promotionType,
                Integer id,
                String title,
                String contents,
                Double discountRate,
                LocalDate startDate,
                LocalDate endDate,
                PromotionStatus status,
                ItemCategory category,
                List<Integer> storeIds,
                Integer itemId) {
            this.promotionType = promotionType;
            this.id = id;
            this.title = title;
            this.contents = contents;
            this.discountRate = discountRate;
            this.startDate = startDate;
            this.endDate = endDate;
            this.status = status;
            this.category = category;
            this.storeIds = storeIds;
            this.itemId = itemId;
        }

    public static Page<PromotionInfoRes> toDtoPage(Page<Promotion>  page) {
        return page.map(PromotionInfoRes::toDto);
    }

}
