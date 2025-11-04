package MeshX.HypeLink.head_office.shipment.repository;

import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<Parcel, Integer> {
}
