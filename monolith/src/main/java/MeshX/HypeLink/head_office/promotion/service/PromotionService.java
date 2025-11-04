package MeshX.HypeLink.head_office.promotion.service;


import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.common.s3.S3UrlBuilder;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import MeshX.HypeLink.head_office.coupon.repository.CouponJpaRepositoryVerify;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseDetailsStatusInfoListRes;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseDetailsStatusInfoRes;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseDetailStatus;
import MeshX.HypeLink.head_office.promotion.model.dto.request.PromotionCreateReq;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoListRes;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoRes;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionStatusInfoRes;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionStatusListRes;
import MeshX.HypeLink.head_office.promotion.model.entity.*;
import MeshX.HypeLink.head_office.promotion.repository.PromotionJpaRepositoryVerify;
import MeshX.HypeLink.image.model.entity.Image;
import MeshX.HypeLink.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionJpaRepositoryVerify repository;
    private final CouponJpaRepositoryVerify couponRepository;
    private final ImageService imageService;
    private final S3UrlBuilder s3UrlBuilder;


    @Transactional
    public void createPromotion(PromotionCreateReq dto) {
        Coupon coupon = couponRepository.findById(dto.getCouponId());
        Promotion promotion = dto.toEntity(coupon);

        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            List<Image> images = imageService.createImagesFromRequest(dto.getImages());
            for (Image image : images) {
                PromotionImages promotionImage = PromotionImages.builder()
                        .promotion(promotion)
                        .image(image)
                        .build();
                promotion.addPromotionImage(promotionImage);
            }
        }

        promotion.autoUpdateStatus();
        repository.createPromotion(promotion);
    }

    public PromotionInfoListRes readList() {//비페이징용
        List<Promotion> promotions = repository.findAll();
        return PromotionInfoListRes.toDto(promotions, this::exportS3Url);
    }

    public PageRes<PromotionInfoRes> readList(Pageable pageReq) {//페이징용
        Page<Promotion> entityPage = repository.findAll(pageReq);
        Page<PromotionInfoRes> dtoPage = PromotionInfoRes.toDtoPage(entityPage, this::exportS3Url);
        return PageRes.toDto(dtoPage);
    }


    public PromotionInfoRes readDetails(Integer id) {
        Promotion promotion = repository.findById(id);
        return PromotionInfoRes.toDto(promotion, this::exportS3Url);
    }

    @Transactional
    public void delete(Integer id) {
        Promotion promotion = repository.findById(id);
        repository.delete(promotion);
    }

    @Transactional
    public PromotionInfoRes update(Integer id, String title, String contents, LocalDate startDate, LocalDate endDate, PromotionStatus status, Integer couponId, List<MeshX.HypeLink.image.model.dto.request.ImageCreateRequest> images) {
        Promotion promotion = repository.findById(id);


        if(!title.isEmpty()){
            promotion.updateTitle(title);
        }
        if(!contents.isEmpty()){
            promotion.updateContents(contents);
        }

        if (!startDate.equals(promotion.getStartDate())) {
            promotion.updateStartDate(startDate);
        }

        if (!endDate.equals(promotion.getEndDate())) {
            promotion.updateEndDate(endDate);
        }

        if (status.equals(PromotionStatus.ENDED)) {
            promotion.updateStatus(PromotionStatus.ENDED);
        } else {
            // ✅ 자동 상태 재평가
            promotion.autoUpdateStatus();
        }

        if(!couponId.equals(promotion.getCoupon().getId())) {
            Coupon coupon = couponRepository.findById(couponId);
            promotion.updateCoupon(coupon);
        }

        if (images != null) {
            promotion.clearImages();
            List<Image> newImages = imageService.createImagesFromRequest(images);
            for (Image image : newImages) {
                PromotionImages promotionImage = PromotionImages.builder()
                        .promotion(promotion)
                        .image(image)
                        .build();
                promotion.addPromotionImage(promotionImage);
            }
        }


        Promotion update = repository.update(promotion);
        return PromotionInfoRes.toDto(update, this::exportS3Url);
    }

    public PageRes<PromotionInfoRes> search(String keyword, String status, Pageable pageReq) {
        PromotionStatus promotionStatus = PromotionStatus.fromDescription(status);
        Page<Promotion> entityPage = repository.search(keyword, promotionStatus, pageReq);
        Page<PromotionInfoRes> dtoPage = PromotionInfoRes.toDtoPage(entityPage, this::exportS3Url);
        return PageRes.toDto(dtoPage);
    }

    public String exportS3Url(Image image) {
        return s3UrlBuilder.buildPublicUrl(image.getSavedPath());
    }

    public PromotionStatusListRes readStatus() {
        List<PromotionStatusInfoRes> states = Arrays.stream(PromotionStatus.values())
                .map(state -> PromotionStatusInfoRes.builder()
                        .description(state.getDescription())
                        .build())
                .collect(Collectors.toList());

        return PromotionStatusListRes.builder()
                .promotionStatusInfos(states)
                .build();
    }


}
