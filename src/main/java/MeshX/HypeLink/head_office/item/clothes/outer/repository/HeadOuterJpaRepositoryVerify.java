package MeshX.HypeLink.head_office.item.clothes.outer.repository;




import MeshX.HypeLink.head_office.item.clothes.outer.exception.HeadOuterException;
import MeshX.HypeLink.head_office.item.clothes.outer.model.entity.OuterClothes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.item.clothes.outer.exception.HeadOuterExceptionMessage.NOT_FOUND;


@Repository("headOfficeOuterJpaRepositoryVerify")
@RequiredArgsConstructor
public class HeadOuterJpaRepositoryVerify {
    private final HeadOuterRepository repository;

    public void createOuter(OuterClothes entity){
        repository.save(entity);
    }

    public List<OuterClothes> findAll() {
        List<OuterClothes> outers = repository.findAll();
        if(!outers.isEmpty()){
            return outers;
        }
        throw new HeadOuterException(NOT_FOUND);
    }

    public OuterClothes findById(Integer id) {
        Optional<OuterClothes> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new HeadOuterException(NOT_FOUND);
    }

    public void delete(OuterClothes entity) {
        repository.delete(entity);
    }

    public OuterClothes update(OuterClothes entity) {
        return repository.save(entity);
    }

}
