package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.head_office.item.model.entity.Color;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.item.exception.ColorExceptionMessage.NOTFOUND_ID;
import static MeshX.HypeLink.head_office.item.exception.ColorExceptionMessage.NOTFOUND_NAME;

@Repository
@RequiredArgsConstructor
public class ColorJpaRepositoryVerify {
    private final ColorRepository repository;

    public void save(Color entity) {
        repository.save(entity);
    }

    public Color isExist(String name) {
        Optional<Color> optional = repository.findByColorName(name);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_NAME);
        }

        return optional.get();
    }

    public Color findById(Integer id) {
        Optional<Color> optional = repository.findByIdAndIsDeletedIsFalse(id);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_ID);
        }

        return optional.get();
    }

    public Color findByName(String name) {
        Optional<Color> optional = repository.findByColorNameAndIsDeletedIsFalse(name);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_NAME);
        }

        return optional.get();
    }

    public List<Color> findAll() {
        List<Color> all = repository.findAll();
        if(all.isEmpty()) {
            throw new BaseException(null);
        }
        return all;
    }
}
