package MeshX.HypeLink.auth.repository;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.POS;
import MeshX.HypeLink.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.auth.exception.AuthExceptionMessage.USER_NAME_NOT_FOUND;

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
    public void deleteById(Integer storeId) {
        repository.deleteById(storeId);
    }

    public POS findByMember(Member member) {
        Optional<POS> optional = repository.findByMember(member);
        if(optional.isEmpty()) {
            // Using a generic exception here as a specific one for POS not found by member doesn't exist yet.
            // This can be refined by adding a new specific exception type.
            throw new BaseException(USER_NAME_NOT_FOUND);
        }
        return optional.get();
    }
}
