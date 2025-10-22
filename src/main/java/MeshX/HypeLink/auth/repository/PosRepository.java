package MeshX.HypeLink.auth.repository;

import MeshX.HypeLink.auth.model.entity.POS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PosRepository extends JpaRepository<POS, Integer> {
    @Query("SELECT p FROM POS p " +
            "LEFT JOIN FETCH p.member " +
            "WHERE p.store.id IN :storeIds")
    List<POS> findByStoreIdIn(@Param("storeIds") List<Integer> storeIds);
}
