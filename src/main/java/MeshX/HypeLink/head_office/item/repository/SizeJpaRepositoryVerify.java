package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.head_office.item.model.entity.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static MeshX.HypeLink.head_office.item.exception.SizeExceptionMessage.NOTFOUND_ID;
import static MeshX.HypeLink.head_office.item.exception.SizeExceptionMessage.NOTFOUND_NAME;

@Repository
@RequiredArgsConstructor
public class SizeJpaRepositoryVerify {
    private final SizeRepository repository;

    public void save(Size entity) {
        repository.save(entity);
    }

    public Size isExist(String name) {
        Optional<Size> optional = repository.findBySize(name);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_NAME);
        }

        return optional.get();
    }

    public Size findById(Integer id) {
        Optional<Size> optional = repository.findByIdAndIsDeletedIsFalse(id);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_ID);
        }

        return optional.get();
    }

    public Size findByName(String name) {
        Optional<Size> optional = repository.findBySizeAndIsDeletedIsFalse(name);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_NAME);
        }

        return optional.get();
    }
}
