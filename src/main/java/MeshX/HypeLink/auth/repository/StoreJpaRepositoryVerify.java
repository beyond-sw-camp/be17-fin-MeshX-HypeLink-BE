package MeshX.HypeLink.auth.repository;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StoreJpaRepositoryVerify {
    private final StoreRepository repository;

    public void save(Store entity) {
        repository.save(entity);
    }

    public Store findById(Integer id) {
        Optional<Store> optional = repository.findById(id);
        if(optional.isEmpty()) {
            throw new BaseException(null);
        }
        return optional.get();
    }

    public List<Store> findAll() {
        return repository.findAll();
    }

    public Store findByMember(Member member) {
        Optional<Store> optional = repository.findByMember(member);
        if(optional.isEmpty()) {
            throw new BaseException(null);
        }
        return optional.get();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
