package MeshX.HypeLink.head_office.promotion.model.dto.response;

import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionStatus;
import MeshX.HypeLink.image.model.dto.response.ImageUploadResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PromotionInfoRes {
    private Integer id;
    private String title;
    private String contents;
    private LocalDate startDate;    // 할인 시작 시정
    private LocalDate endDate;      // 할인 종료 시점
    private PromotionStatus status;

    private String couponType;
    private String couponName;
    private Integer couponId;
    private List<ImageUploadResponse> images;

    public static PromotionInfoRes toDto(Promotion entity, java.util.function.Function<MeshX.HypeLink.image.model.entity.Image, String> urlGenerator) {
        Coupon coupon = entity.getCoupon();

        List<ImageUploadResponse> imageDtos = entity.getImages().stream()
                .map(image -> ImageUploadResponse.builder()
                        .id(image.getId())
                        .originalName(image.getOriginalFilename())
                        .imageUrl(urlGenerator.apply(image))
                        .imageSize(image.getFileSize())
                        .build())
                .collect(Collectors.toList());

        return PromotionInfoRes.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .couponType(coupon.getCouponType().name())
                .couponName(coupon.getName())
                .couponId(coupon.getId())
                .images(imageDtos)
                .build();
    }

     @Builder
    private PromotionInfoRes(
                Integer id,
                String title,
                String contents,
                LocalDate startDate,
                LocalDate endDate,
                PromotionStatus status,
                String couponType,
                String couponName,
                Integer couponId,
                List<ImageUploadResponse> images
                ) {
            this.id = id;
            this.title = title;
            this.contents = contents;
            this.startDate = startDate;
            this.endDate = endDate;
            this.status = status;
            this.couponType = couponType;
            this.couponName = couponName;
            this.couponId = couponId;
            this.images = images;
        }

    public static Page<PromotionInfoRes> toDtoPage(Page<Promotion> page, java.util.function.Function<MeshX.HypeLink.image.model.entity.Image, String> urlGenerator) {
        return page.map(promotion -> PromotionInfoRes.toDto(promotion, urlGenerator));
    }

}
