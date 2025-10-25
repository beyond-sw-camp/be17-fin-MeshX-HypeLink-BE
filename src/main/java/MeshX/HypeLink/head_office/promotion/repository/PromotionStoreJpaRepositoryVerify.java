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


    // ✅ (선택) 특정 프로모션 ID로 조회
    public List<PromotionStore> findByPromotion(Promotion promotion) {
        return repository.findByPromotion(promotion);
    }

    public void deleteAllByPromotion(Promotion promotion) {
        List<PromotionStore> existing = repository.findByPromotion(promotion);
        repository.deleteAll(existing);
        repository.flush();
    }

    //삭제 + 생성 = 업데이트 메소드
    public void updateStores(Promotion promotion, List<Store> storeIds) {
        // ① 현재 연결된 매장 관계 전부 조회
        List<PromotionStore> stores = repository.findByPromotion(promotion);
        // ② 기존 매장 연결 제거
        repository.deleteAll(stores);
        repository.flush(); // 삭제 즉시반영

        // ③ 새로운 매장 관계 생성
        List<PromotionStore> newLinks = storeIds.stream()
                .map(storeId -> PromotionStore.builder()
                        .promotion(promotion)
                        .store(storeId)
                        .build())
                .toList();
        // 새 링크 저장
        repository.saveAll(newLinks);
    }

}

