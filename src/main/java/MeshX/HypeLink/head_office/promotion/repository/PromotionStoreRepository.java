package MeshX.HypeLink.head_office.promotion.repository;


import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionStore;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PromotionStoreRepository extends JpaRepository<PromotionStore, Integer> {
    @Modifying
    @Query("DELETE FROM PromotionStore ps WHERE ps.promotion.id = :promotionId")
    void deleteAllByPromotionId(@Param("promotionId") Integer promotionId);

    List<PromotionStore> findByPromotion(Promotion promotion);

}
