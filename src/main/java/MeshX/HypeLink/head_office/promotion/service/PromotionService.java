package MeshX.HypeLink.head_office.promotion.service;


import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.direct_store.item.repository.StoreItemJpaRepositoryVerify;
import MeshX.HypeLink.head_office.promotion.model.dto.request.PromotionCreateReq;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoListRes;
import MeshX.HypeLink.head_office.promotion.model.dto.response.PromotionInfoRes;
import MeshX.HypeLink.head_office.promotion.model.entity.*;
import MeshX.HypeLink.head_office.promotion.repository.PromotionCategoryJpaRepositoryVerify;
import MeshX.HypeLink.head_office.promotion.repository.PromotionItemJpaRepositoryVerify;
import MeshX.HypeLink.head_office.promotion.repository.PromotionJpaRepositoryVerify;
import MeshX.HypeLink.head_office.promotion.repository.PromotionStoreJpaRepositoryVerify;
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
    private final StoreItemJpaRepositoryVerify storeItemRepository;
    private final StoreJpaRepositoryVerify storeRepository;
    private final PromotionJpaRepositoryVerify repository;
    private final PromotionStoreJpaRepositoryVerify promotionStoreRepository;
    private final PromotionItemJpaRepositoryVerify promotionItemRepository;
    private final PromotionCategoryJpaRepositoryVerify promotionCategoryRepository;


    @Transactional
    public void createPromotion(PromotionCreateReq dto) {
        // 1️⃣ 프로모션 기본 생성
        Promotion promotion = dto.toEntity();
        promotion.autoUpdateStatus();
        repository.createPromotion(promotion);

        if (dto.getPromotionType() == PromotionType.PRODUCT && dto.getItemId() != null) {
            StoreItem item = storeItemRepository.findById(dto.getItemId()); // ✅ 이미 검증된 조회
            PromotionItem link = PromotionItem.builder()
                    .promotion(promotion)
                    .item(item)
                    .build();
            promotionItemRepository.save(link);
        }

        // 2️⃣ 매장 이벤트일 때 PromotionStore 저장
        if (dto.getPromotionType() == PromotionType.STORE && dto.getStoreIds() != null) {
            List<PromotionStore> promotionStores = dto.getStoreIds().stream()
//                    .map(storeId -> {
//                        Store store = Store.builder().id(storeId).build(); //JPA가 완전한 Store 엔티티 객체로 관계를 인식
//                        return PromotionStore.builder()
//                                .promotion(promotion)
//                                .store(store)
//                                .build();
//                    })
                    .map(storeRepository::findById) // ✅ 영속 엔티티 확보
                    .map(store -> PromotionStore.builder()
                            .promotion(promotion)
                            .store(store)
                            .build())
                    .toList();

            promotionStoreRepository.saveAll(promotionStores);
        }

        if (dto.getPromotionType() == PromotionType.CATEGORY && dto.getCategory() != null) {
            PromotionCategory link = PromotionCategory.builder()
                    .promotion(promotion)
                    .category(dto.getCategory())
                    .build();
            promotionCategoryRepository.save(link);
        }

        if (dto.getPromotionType() == PromotionType.ALL) {
            Store hq = storeRepository.findById(1); // ✅ 본사
            PromotionStore link = PromotionStore.builder()
                    .promotion(promotion)
                    .store(hq)
                    .build();
            promotionStoreRepository.saveAll(List.of(link));
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

    @Transactional
    public void delete(Integer id) {
        Promotion promotion = repository.findById(id);
        repository.delete(promotion);
    }

    @Transactional
    public PromotionInfoRes update(Integer id, PromotionType promotionType, ItemCategory category, String title, String contents, Double discountRate, LocalDate startDate, LocalDate endDate, PromotionStatus status,  List<Integer> storeIds,Integer itemId) {
        Promotion promotion = repository.findById(id);

        //기본 필드 업데이트
        if (promotion.getPromotionType() != promotionType) {
            promotion.updatePromotionType(promotionType);
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

        if (!status.equals(promotion.getStatus())) {
            promotion.updateStatus(status);
        }

        if (!Objects.equals(promotion.getStatus(), status)) {
            promotion.updateStatus(status);
        }

//        // ✅ 자동 상태 갱신 (단, 관리자가 직접 ENDED로 바꾼 경우는 제외)
//        if (status != PromotionStatus.ENDED) {
//            promotion.autoUpdateStatus();
//        }
//
//        if (promotionType == PromotionType.STORE) {
//            List<Store> list = storeIds.stream().map(storeRepository::findById).toList();
//            promotionStoreRepository.updateStores(promotion, list);
//        }

        // 타입별 링크 정리/재생성 이부분 고쳐야됨
        // 타입별 연관 데이터 관리
        switch (promotionType) {
            case STORE -> {
                // 다른 타입 링크 삭제
                promotionItemRepository.deleteAllByPromotion(promotion);
                promotionCategoryRepository.deleteAllByPromotion(promotion);

                // 매장 재구성
                List<Store> stores = (storeIds == null ? List.<Store>of()
                        : storeIds.stream().map(storeRepository::findById).toList());
                promotionStoreRepository.updateStores(promotion, stores);
            }

            case PRODUCT -> {
                // 다른 타입 삭제
                promotionStoreRepository.updateStores(promotion, List.of());
                promotionCategoryRepository.deleteAllByPromotion(promotion);

                // 상품 연결 재생성
                promotionItemRepository.deleteAllByPromotion(promotion);
                if (itemId != null) {
                    StoreItem item = storeItemRepository.findById(itemId);
                    promotionItemRepository.save(
                            PromotionItem.builder().promotion(promotion).item(item).build()
                    );
                }
            }

            case CATEGORY -> {
                // 다른 타입 삭제
                promotionStoreRepository.updateStores(promotion, List.of());
                promotionItemRepository.deleteAllByPromotion(promotion);

                // 카테고리 재생성
                promotionCategoryRepository.deleteAllByPromotion(promotion);
                if (category != null) {
                    promotionCategoryRepository.save(
                            PromotionCategory.builder().promotion(promotion).category(category).build()
                    );
                }
            }

            case ALL -> {
                // 모든 링크 제거
                promotionStoreRepository.updateStores(promotion, List.of());
                promotionItemRepository.deleteAllByPromotion(promotion);
                promotionCategoryRepository.deleteAllByPromotion(promotion);
            }
        }

        Promotion update = repository.update(promotion);
        return PromotionInfoRes.toDto(update);
    }}
