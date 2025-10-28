package MeshX.HypeLink.head_office.as.repository;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.head_office.as.exception.AsException;
import MeshX.HypeLink.head_office.as.model.entity.As;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.as.exception.AsExceptionMessage.AS_NOT_FOUNT;

@Repository
@RequiredArgsConstructor
public class AsJpaRepositoryVerify {
    private final AsRepository repository;

    public As findById(Integer id) {
        Optional<As> as = repository.findById(id);
        if (as.isPresent()) {
            return as.get();
        }
        throw new AsException(AS_NOT_FOUNT);
    }

    public void save(As as) {
        repository.save(as);
    }

    public void delete(As as) {
        repository.delete(as);
    }

    public List<As> findByStore(Store store) {
        return repository.findByStore(store);
    }

    public Page<As> findAll(Pageable pageable) {
        Page<As> asPage = repository.findAll(pageable);
        if (asPage.isEmpty()) {
            throw new AsException(AS_NOT_FOUNT);
        }
        return asPage;
    }
}
