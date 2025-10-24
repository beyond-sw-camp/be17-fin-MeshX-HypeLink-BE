package MeshX.HypeLink.head_office.promotion.repository;

import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionCategoryRepository extends JpaRepository<PromotionCategory,Integer> {
    List<PromotionCategory> findByPromotion(Promotion promotion);
}
