package MeshX.HypeLink.head_office.item.clothes.bottom.repository;


import MeshX.HypeLink.head_office.item.clothes.bottom.exception.HeadBottomException;
import MeshX.HypeLink.head_office.item.clothes.bottom.model.entity.BottomClothes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.item.clothes.bottom.exception.HeadBottomExceptionMessage.NOT_FOUND;


@Repository("headOfficeBottomJpaRepositoryVerify")
@RequiredArgsConstructor
public class HeadBottomJpaRepositoryVerify {
    private final HeadBottomRepository repository;

    public void createBottom(BottomClothes entity){
        repository.save(entity);
    }

    public List<BottomClothes> findAll() {
        List<BottomClothes> bottoms = repository.findAll();
        if(!bottoms.isEmpty()){
            return bottoms;
        }
        throw new HeadBottomException(NOT_FOUND);
    }

    public BottomClothes findById(Integer id) {
        Optional<BottomClothes> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new HeadBottomException(NOT_FOUND);
    }

    public void delete(BottomClothes entity) {
        repository.delete(entity);
    }

    public BottomClothes update(BottomClothes entity) {
        return repository.save(entity);
    }

}
