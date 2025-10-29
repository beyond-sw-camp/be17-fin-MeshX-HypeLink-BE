package MeshX.HypeLink.head_office.as.repository;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.head_office.as.model.entity.As;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsRepository extends JpaRepository<As, Integer> {

    List<As> findByStore(Store store);
}
