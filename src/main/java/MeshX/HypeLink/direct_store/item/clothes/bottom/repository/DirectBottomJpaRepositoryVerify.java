package MeshX.HypeLink.direct_store.item.clothes.bottom.repository;

import MeshX.HypeLink.direct_store.item.clothes.bottom.exception.DirectBottomException;
import MeshX.HypeLink.direct_store.item.clothes.bottom.model.entity.DirectBottomClothes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.direct_store.item.clothes.bottom.exception.DirectBottomExceptionMessage.NOT_FOUND;

@Repository("directStoreBottomJpaRepositoryVerify")
@RequiredArgsConstructor
public class DirectBottomJpaRepositoryVerify {
    private final DirectBottomRepository repository;

    public void createBottom(DirectBottomClothes entity){
        repository.save(entity);
    }

    public List<DirectBottomClothes> findAll() {
        List<DirectBottomClothes> bottoms = repository.findAll();
        if(!bottoms.isEmpty()){
            return bottoms;
        }
        throw new DirectBottomException(NOT_FOUND);
    }

    public DirectBottomClothes findById(Integer id) {
        Optional<DirectBottomClothes> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new DirectBottomException(NOT_FOUND);
    }

    public void delete(DirectBottomClothes entity) {
        repository.delete(entity);
    }

    public DirectBottomClothes update(DirectBottomClothes entity) {
        return repository.save(entity);
    }

}
