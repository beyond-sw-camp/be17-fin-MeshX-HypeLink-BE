package MeshX.HypeLink.head_office.shipment.repository;

import MeshX.HypeLink.head_office.shipment.model.entity.ParcelItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ParcelItemJpaRepositoryVerify {
    private final ParcelItemRepository repository;

    public ParcelItem save(ParcelItem entity) {
        return repository.save(entity);
    }
}
