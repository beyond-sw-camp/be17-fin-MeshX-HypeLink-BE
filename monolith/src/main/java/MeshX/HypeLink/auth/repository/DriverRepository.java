package MeshX.HypeLink.auth.repository;

import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Optional<Driver> findByMember(Member member);
}
