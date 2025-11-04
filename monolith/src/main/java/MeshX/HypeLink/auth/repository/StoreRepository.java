package MeshX.HypeLink.auth.repository;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    @Query("select s from Store s left join fetch s.member")
    List<Store> findAllWithMember();
    Optional<Store> findByMember(Member member);
}
