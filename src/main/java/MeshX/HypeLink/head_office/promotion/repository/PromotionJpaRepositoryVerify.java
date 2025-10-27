package MeshX.HypeLink.head_office.promotion.repository;


import MeshX.HypeLink.head_office.promotion.exception.PromotionException;
import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.promotion.exception.PromotionExceptionMessage.NOT_FOUND;


@Repository
@RequiredArgsConstructor
public class PromotionJpaRepositoryVerify {
    private final PromotionRepository repository;

    public void createPromotion(Promotion entity) {
        repository.save(entity);
    }

    public List<Promotion> findAll() {
        List<Promotion> promotions = repository.findAll();
        if (!promotions.isEmpty()) {
            return promotions;
        }
        throw new PromotionException(NOT_FOUND);
    }

    public Page<Promotion> findAll(Pageable pageReq) {
        Page<Promotion> page = repository.findAll(pageReq);
        if (page.hasContent()) {
            return page;
        }
        throw new PromotionException(NOT_FOUND);
    }

    public Promotion findById(Integer id) {
        Optional<Promotion> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new PromotionException(NOT_FOUND);
    }


    public void delete(Promotion entity) {
        repository.delete(entity);
    }

    public Promotion update(Promotion entity) {
        return repository.save(entity);
    }
}
