package MeshX.HypeLink.head_office.item.clothes.top.repository;



import MeshX.HypeLink.head_office.item.clothes.top.exception.HeadTopException;
import MeshX.HypeLink.head_office.item.clothes.top.model.entity.TopClothes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.item.clothes.top.exception.HeadTopExceptionMessage.NOT_FOUND;


@Repository("headOfficeTopJpaRepositoryVerify")
@RequiredArgsConstructor
public class HeadTopJpaRepositoryVerify {
    private final HeadTopRepository repository;

    public void createTop(TopClothes entity){
        repository.save(entity);
    }

    public List<TopClothes> findAll() {
        List<TopClothes> tops = repository.findAll();
        if(!tops.isEmpty()){
            return tops;
        }
        throw new HeadTopException(NOT_FOUND);
    }

    public TopClothes findById(Integer id) {
        Optional<TopClothes> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new HeadTopException(NOT_FOUND);
    }

    public void delete(TopClothes entity) {
        repository.delete(entity);
    }

    public TopClothes update(TopClothes entity) {
        return repository.save(entity);
    }

}
