package MeshX.HypeLink.direct_strore.item.clothes.outer.repository;



import MeshX.HypeLink.direct_strore.item.clothes.outer.exception.DirectOuterException;
import MeshX.HypeLink.direct_strore.item.clothes.outer.model.entity.DirectOuterClothes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.direct_strore.item.clothes.outer.exception.DirectOuterExceptionMessage.NOT_FOUND;


@Repository("directStoreOuterJpaRepositoryVerify")
@RequiredArgsConstructor
public class DirectOuterJpaRepositoryVerify {
    private final DirectOuterRepository repository;

    public void createOuter(DirectOuterClothes entity){
        repository.save(entity);
    }

    public List<DirectOuterClothes> findAll() {
        List<DirectOuterClothes> outers = repository.findAll();
        if(!outers.isEmpty()){
            return outers;
        }
        throw new DirectOuterException(NOT_FOUND);
    }

    public DirectOuterClothes findById(Integer id) {
        Optional<DirectOuterClothes> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new DirectOuterException(NOT_FOUND);
    }

    public void delete(DirectOuterClothes entity) {
        repository.delete(entity);
    }

    public DirectOuterClothes update(DirectOuterClothes entity) {
        return repository.save(entity);
    }

}
