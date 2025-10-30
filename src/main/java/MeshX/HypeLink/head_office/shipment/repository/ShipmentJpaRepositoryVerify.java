package MeshX.HypeLink.head_office.shipment.repository;

import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.head_office.shipment.exception.ShipmentException;
import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import MeshX.HypeLink.head_office.shipment.model.entity.ShipmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.shipment.exception.ShipmentExceptionMessage.NOT_FOUND_SHIPMENT;

@Repository
@RequiredArgsConstructor
public class ShipmentJpaRepositoryVerify {
    private final ShipmentRepository repository;

    public void save(Shipment entity) {
        repository.save(entity);
    }

    public Shipment findById(Integer id) {
        Optional<Shipment> shipment =repository.findById(id);
        if(shipment.isPresent()) {
            return shipment.get();
        }
        throw new ShipmentException(NOT_FOUND_SHIPMENT);
    }

    public List<Shipment> findByDriverIsNull() {
        return repository.findByDriverIsNull();
    }

    public List<Shipment> findByShipmentStatusIn(List<ShipmentStatus> shipmentStatuses) {
        return repository.findByShipmentStatusIn(shipmentStatuses);
    }

    public List<Shipment> findByShipmentStatusIn(Driver driver, List<ShipmentStatus> statuses) {
        return repository.findByDriverAndShipmentStatusIn(driver, statuses);
    }

}
