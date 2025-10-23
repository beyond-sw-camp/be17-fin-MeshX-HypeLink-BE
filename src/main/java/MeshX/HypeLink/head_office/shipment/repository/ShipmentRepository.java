package MeshX.HypeLink.head_office.shipment.repository;

import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    List<Shipment> findByDriverIsNull();
}
