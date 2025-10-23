package MeshX.HypeLink.head_office.promotion.service;


import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.promotion.model.dto.request.PromotionCreateReq;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoListRes;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoRes;
import MeshX.HypeLink.head_office.promotion.model.entity.*;
import MeshX.HypeLink.head_office.promotion.repository.PromotionJpaRepositoryVerify;
import MeshX.HypeLink.head_office.promotion.repository.PromotionStoreJpaRepositoryVerify;
import MeshX.HypeLink.head_office.promotion.repository.PromotionStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionJpaRepositoryVerify repository;
    private final PromotionStoreJpaRepositoryVerify promotionStoreRepository;


    @Transactional
    public void createPromotion(PromotionCreateReq dto) {
        // 1️⃣ 프로모션 기본 생성
        Promotion promotion = dto.toEntity();
        promotion.autoUpdateStatus();
        repository.createPromotion(promotion);

        // 2️⃣ 매장 이벤트일 때 PromotionStore 저장
        if (dto.getPromotionType() == PromotionType.STORE && dto.getStoreIds() != null) {
            List<PromotionStore> promotionStores = dto.getStoreIds().stream()
                    .map(storeId -> {
                        Store store = Store.builder().id(storeId).build();
                        return PromotionStore.builder()
                                .promotion(promotion)
                                .store(store)
                                .build();
                    })
                    .toList();

            promotionStoreRepository.saveAll(promotionStores);
        }
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

    public void delete(Integer id) {
        Promotion promotion = repository.findById(id);
        repository.delete(promotion);
    }

    @Transactional
    public PromotionInfoRes update(Integer id, PromotionType promotionType, ItemCategory category, String title, String contents, Double discountRate, LocalDate startDate, LocalDate endDate, PromotionStatus status, List<Integer> storeIds ) {
        Promotion promotion = repository.findById(id);

        if (!Objects.equals(promotion.getPromotionType(), promotionType)) {
            promotion.updatePromotionType(promotionType);
        }

        // ✅ category 변경 (CATEGORY일 때만 유지, 그 외 타입이면 null)
        if (promotionType == PromotionType.CATEGORY) {
            if (!Objects.equals(promotion.getCategory(), category)) {
                promotion.updateCategory(category);
            }
        } else {
            promotion.updateCategory(null);
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

        if (!Objects.equals(promotion.getStatus(), status)) {
            promotion.updateStatus(status);
        }

        // ✅ 자동 상태 갱신 (단, 관리자가 직접 ENDED로 바꾼 경우는 제외)
        if (status != PromotionStatus.ENDED) {
            promotion.autoUpdateStatus();
        }

        if (promotionType == PromotionType.STORE && storeIds != null) {
            promotionStoreRepository.updateStores(promotion, storeIds);
        }


        Promotion update = repository.update(promotion);
        return PromotionInfoRes.toDto(update);
    }}
