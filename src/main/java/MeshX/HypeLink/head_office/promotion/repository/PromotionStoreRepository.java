package MeshX.HypeLink.head_office.promotion.repository;


import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionStoreRepository extends JpaRepository<PromotionStore, Integer> {
    List<PromotionStore> findByPromotion(Promotion promotion); //특정 프로모션의 매장 관계를 조회용으로 쓰는 메서드

}
