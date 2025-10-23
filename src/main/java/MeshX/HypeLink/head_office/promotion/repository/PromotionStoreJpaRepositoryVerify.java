package MeshX.HypeLink.head_office.promotion.repository;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PromotionStoreJpaRepositoryVerify {
    private final PromotionStoreRepository repository;


    // ✅ 여러 PromotionStore 저장
    public void saveAll(List<PromotionStore> promotionStores) {
        repository.saveAll(promotionStores);
    }

    // ✅ 특정 프로모션에 연결된 매장 관계 모두 삭제
//    public void deleteAllByPromotion(Promotion promotion) {
//        repository.deleteAllByPromotion(promotion);
//    }

    // ✅ (선택) 특정 프로모션 ID로 조회
    public List<PromotionStore> findByPromotion(Promotion promotion) {
        return repository.findByPromotion(promotion);
    }

    //삭제 + 생성 = 업데이트 메소드
    public void updateStores(Promotion promotion, List<Integer> storeIds) {
        repository.deleteAllByPromotionId(promotion.getId());

        List<PromotionStore> newLinks = storeIds.stream()
                .map(storeId -> PromotionStore.builder()
                        .promotion(promotion)
                        .store(Store.builder().id(storeId).build())
                        .build())
                .toList();

        repository.saveAll(newLinks);
    }
    
}

