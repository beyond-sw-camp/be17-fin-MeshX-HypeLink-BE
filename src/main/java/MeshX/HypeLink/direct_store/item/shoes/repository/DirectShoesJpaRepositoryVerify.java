package MeshX.HypeLink.direct_store.item.shoes.repository;

import MeshX.HypeLink.direct_store.item.shoes.exception.DirectShoesException;
import MeshX.HypeLink.direct_store.item.shoes.model.entity.DirectShoes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.direct_store.item.shoes.exception.DirectShoesExceptionMessage.NOT_FOUND;

@Repository("directStoreShoesJpaRepositoryVerify")
@RequiredArgsConstructor
public class DirectShoesJpaRepositoryVerify {
    private final DirectShoesRepository repository;

    public void createShoes(DirectShoes entity){
        repository.save(entity);
    }

    public List<DirectShoes> findAll() {
        List<DirectShoes> shoes = repository.findAll();
        if(!shoes.isEmpty()){
            return shoes;
        }
        throw new DirectShoesException(NOT_FOUND);
    }

    public DirectShoes findById(Integer id) {
        Optional<DirectShoes> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new DirectShoesException(NOT_FOUND);
    }

    public void delete(DirectShoes entity) {
        repository.delete(entity);
    }

    public DirectShoes update(DirectShoes entity) {
        return repository.save(entity);
    }

}
