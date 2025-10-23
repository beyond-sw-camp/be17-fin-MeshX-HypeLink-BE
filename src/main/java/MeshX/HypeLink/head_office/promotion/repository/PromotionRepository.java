package MeshX.HypeLink.head_office.promotion.repository;

import MeshX.HypeLink.head_office.promotion.model.entity.Promotion;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion,Integer> {
    @Query("""
        SELECT DISTINCT m.name
        FROM Promotion p
        JOIN p.promotionStores ps
        JOIN ps.store s
        JOIN s.member m
        WHERE p.id = :promotionId
    """)
    List<String> findBranchNamesByPromotionId(@Param("promotionId") Integer promotionId);
}
