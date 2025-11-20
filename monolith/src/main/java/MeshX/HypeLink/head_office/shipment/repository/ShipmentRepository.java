package MeshX.HypeLink.head_office.shipment.repository;

import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import MeshX.HypeLink.head_office.shipment.model.entity.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    List<Shipment> findByDriverIsNull();

    List<Shipment> findByShipmentStatusIn(List<ShipmentStatus> statuses);

    @Query("""
    SELECT DISTINCT s
    FROM Shipment s
    JOIN FETCH s.parcel p
    JOIN FETCH p.parcelItems pi
    JOIN FETCH pi.purchaseOrder po
    JOIN FETCH po.itemDetail id
    WHERE s.driver = :driver
      AND s.shipmentStatus = :shipmentStatus""")
    List<Shipment> findByDriverAndShipmentStatus(Driver driver, ShipmentStatus shipmentStatus);

    List<Shipment> findByDriverAndShipmentStatusIn(Driver driver, List<ShipmentStatus> statuses);
}
