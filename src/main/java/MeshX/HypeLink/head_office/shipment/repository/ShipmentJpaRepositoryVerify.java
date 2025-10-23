package MeshX.HypeLink.head_office.shipment.repository;

import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShipmentJpaRepositoryVerify {
    private final ShipmentRepository repository;

    public void save(Shipment entity) {
        repository.save(entity);
    }

    public List<Shipment> findByDriverIsNull() {
        return repository.findByDriverIsNull();
    }

}
