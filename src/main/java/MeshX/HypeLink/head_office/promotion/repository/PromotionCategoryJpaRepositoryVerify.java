package MeshX.HypeLink.head_office.promotion.repository;

import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import MeshX.HypeLink.head_office.promotion.model.entity.PromotionCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PromotionCategoryJpaRepositoryVerify {
    private final PromotionCategoryRepository repository;

    public void save(PromotionCategory entity) {
        repository.save(entity);
    }

    public void deleteAllByPromotion(Promotion promotion) {
        List<PromotionCategory> existing = repository.findByPromotion(promotion);
        repository.deleteAll(existing);
        repository.flush();
    }

}
