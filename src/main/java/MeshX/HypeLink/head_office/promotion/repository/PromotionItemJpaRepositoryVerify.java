package MeshX.HypeLink.head_office.promotion.repository;

import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PromotionItemJpaRepositoryVerify {
    private final PromotionItemRepository repository;

    public void save(PromotionItem entity) {
        repository.save(entity);
    }



    public void deleteAllByPromotion(Promotion promotion) {
        List<PromotionItem> existing = repository.findByPromotion(promotion);
        repository.deleteAll(existing);
        repository.flush(); // 즉시 반영
    }
}
