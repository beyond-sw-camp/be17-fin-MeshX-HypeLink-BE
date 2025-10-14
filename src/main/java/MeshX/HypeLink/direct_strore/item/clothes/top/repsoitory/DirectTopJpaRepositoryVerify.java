package MeshX.HypeLink.direct_strore.item.clothes.top.repsoitory;


import MeshX.HypeLink.direct_strore.item.clothes.top.exception.DirectTopException;
import MeshX.HypeLink.direct_strore.item.clothes.top.model.entity.DirectTopClothes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.direct_strore.item.clothes.top.exception.DirectTopExceptionMessage.NOT_FOUND;


@Repository("directStoreTopJpaRepositoryVerify")
@RequiredArgsConstructor
public class DirectTopJpaRepositoryVerify {
    private final DirectTopRepository repository;

    public void createTop(DirectTopClothes entity){
        repository.save(entity);
    }

    public List<DirectTopClothes> findAll() {
        List<DirectTopClothes> tops = repository.findAll();
        if(!tops.isEmpty()){
            return tops;
        }
        throw new DirectTopException(NOT_FOUND);
    }

    public DirectTopClothes findById(Integer id) {
        Optional<DirectTopClothes> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new DirectTopException(NOT_FOUND);
    }

    public void delete(DirectTopClothes entity) {
        repository.delete(entity);
    }

    public DirectTopClothes update(DirectTopClothes entity) {
        return repository.save(entity);
    }

}
