package MeshX.HypeLink.head_office.promotion.repository;

import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionItem;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PromotionItemRepository extends JpaRepository<PromotionItem,Integer> {
    List<PromotionItem> findByPromotion(Promotion promotion);
}
