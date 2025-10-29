package MeshX.HypeLink.head_office.shipment.repository;

import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ParcelJpaRepositoryVerify {
    private final ParcelRepository repository;

    public Parcel save(Parcel entity) {
        return repository.save(entity);
    }
}
