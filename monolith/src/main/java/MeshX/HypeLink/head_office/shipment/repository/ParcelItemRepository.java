package MeshX.HypeLink.head_office.shipment.repository;

import MeshX.HypeLink.head_office.shipment.model.entity.ParcelItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelItemRepository extends JpaRepository<ParcelItem, Integer> {
}
