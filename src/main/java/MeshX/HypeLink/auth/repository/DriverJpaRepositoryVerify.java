package MeshX.HypeLink.auth.repository;

import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DriverJpaRepositoryVerify {
    private final DriverRepository repository;

    public void save(Driver entity) {
        repository.save(entity);
    }

    public Driver findById(Integer id) {
        Optional<Driver> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new BaseException(null);
        }
        return optional.get();
    }

    public Driver findByMember(Member member) {
        Optional<Driver> optional = repository.findByMember(member);
        if(optional.isEmpty()) {
            throw new BaseException(null);
        }
        return optional.get();
    }


    public List<Driver> findAll() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
