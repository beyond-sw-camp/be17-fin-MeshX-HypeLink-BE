package MeshX.HypeLink.head_office.promotion.service;


import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.promotion.model.dto.request.PromotionCreateReq;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoListRes;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoRes;
import MeshX.HypeLink.head_office.promotion.model.entity.ItemCategory;
import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionType;
import MeshX.HypeLink.head_office.promotion.repository.PromotionJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionJpaRepositoryVerify repository;


    @Transactional
    public void createPromotion(PromotionCreateReq dto) {
        repository.createPromotion(dto.toEntity());
    }

    public PromotionInfoListRes readList() {//비페이징용
        List<Promotion> promotions = repository.findAll();
        return PromotionInfoListRes.toDto(promotions);
    }

    public PageRes<PromotionInfoRes> readList(PageReq pageReq) {//페이징용
        pageReq.validate();
        Page<Promotion> entityPage = repository.findAll(pageReq);
        Page<PromotionInfoRes> dtoPage =PromotionInfoRes.toDtoPage(entityPage);
        return PageRes.toDto(dtoPage);
    }


    public PromotionInfoRes readDetails(Integer id) {
        Promotion promotion = repository.findById(id);
        return PromotionInfoRes.toDto(promotion);
    }

    public void delete(Integer id) {
        Promotion promotion = repository.findById(id);
        repository.delete(promotion);
    }

    @Transactional
    public PromotionInfoRes update(Integer id, PromotionType promotionType, ItemCategory category, String title, String contents, Double discountRate, LocalDate startDate, LocalDate endDate) {
        Promotion promotion = repository.findById(id);

        if(!promotionType.equals(promotion.getPromotionType())){
            promotion.updatePromotionType(promotionType);
        }
        if(!category.equals(promotion.getCategory())){
            promotion.updateCategory(category);
        }
        if(!title.isEmpty()){
            promotion.updateTitle(title);
        }
        if(!contents.isEmpty()){
            promotion.updateContents(contents);
        }
        if (!discountRate.equals(promotion.getDiscountRate())) {
            promotion.updateDiscountRate(discountRate);
        }

        if (!startDate.equals(promotion.getStartDate())) {
            promotion.updateStartDate(startDate);
        }

        if (!endDate.equals(promotion.getEndDate())) {
            promotion.updateEndDate(endDate);
        }


        Promotion update = repository.update(promotion);
        return PromotionInfoRes.toDto(update);
    }}
