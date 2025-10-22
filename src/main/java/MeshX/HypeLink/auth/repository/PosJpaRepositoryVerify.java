package MeshX.HypeLink.auth.repository;

import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.auth.model.entity.POS;
import MeshX.HypeLink.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PosJpaRepositoryVerify {
    private final PosRepository repository;

    public void save(POS entity) {
        repository.save(entity);
    }

    public POS findById(Integer id) {
        Optional<POS> optional = repository.findById(id);
        if(optional.isEmpty()) {
            throw new BaseException(null);
        }
        return optional.get();
    }
    public List<POS> findByStoreIdIn(List<Integer> storeIds) {
        return repository.findByStoreIdIn(storeIds);
    }
}
