package MeshX.HypeLink.head_office.promotion.service;


import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import MeshX.HypeLink.head_office.coupon.repository.CouponJpaRepositoryVerify;
import MeshX.HypeLink.head_office.promotion.model.dto.request.PromotionCreateReq;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoListRes;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoRes;
import MeshX.HypeLink.head_office.promotion.model.entity.*;
import MeshX.HypeLink.head_office.promotion.repository.PromotionJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionJpaRepositoryVerify repository;
    private final CouponJpaRepositoryVerify couponRepository;


    @Transactional
    public void createPromotion(PromotionCreateReq dto) {
        Coupon coupon = couponRepository.findById(dto.getCouponId());
        Promotion promotion = dto.toEntity(coupon);
        promotion.autoUpdateStatus();
        repository.createPromotion(promotion);
    }

    public PromotionInfoListRes readList() {//비페이징용
        List<Promotion> promotions = repository.findAll();
        return PromotionInfoListRes.toDto(promotions);
    }

    public PageRes<PromotionInfoRes> readList(Pageable pageReq) {//페이징용
        Page<Promotion> entityPage = repository.findAll(pageReq);
        Page<PromotionInfoRes> dtoPage =PromotionInfoRes.toDtoPage(entityPage);
        return PageRes.toDto(dtoPage);
    }


    public PromotionInfoRes readDetails(Integer id) {
        Promotion promotion = repository.findById(id);
        return PromotionInfoRes.toDto(promotion);
    }

    @Transactional
    public void delete(Integer id) {
        Promotion promotion = repository.findById(id);
        repository.delete(promotion);
    }

    @Transactional
    public PromotionInfoRes update(Integer id, String title, String contents, LocalDate startDate, LocalDate endDate, PromotionStatus status, Integer couponId) {
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


        Promotion update = repository.update(promotion);
        return PromotionInfoRes.toDto(update);
    }

    public PageRes<PromotionInfoRes> search(String keyword, String status, Pageable pageReq) {
        Page<Promotion> entityPage = repository.search(keyword, status, pageReq);
        Page<PromotionInfoRes> dtoPage =PromotionInfoRes.toDtoPage(entityPage);
        return PageRes.toDto(dtoPage);
    }
}
