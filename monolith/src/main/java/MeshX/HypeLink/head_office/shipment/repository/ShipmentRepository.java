package MeshX.HypeLink.head_office.shipment.repository;

import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import MeshX.HypeLink.head_office.shipment.model.entity.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    List<Shipment> findByDriverIsNull();

    List<Shipment> findByShipmentStatusIn(List<ShipmentStatus> statuses);

    List<Shipment> findByDriverAndShipmentStatusIn(Driver driver, List<ShipmentStatus> statuses);
}
