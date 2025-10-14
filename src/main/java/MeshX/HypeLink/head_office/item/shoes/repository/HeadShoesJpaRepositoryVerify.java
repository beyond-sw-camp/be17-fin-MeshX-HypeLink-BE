package MeshX.HypeLink.head_office.item.shoes.repository;


import MeshX.HypeLink.head_office.item.shoes.exception.HeadShoesException;
import MeshX.HypeLink.head_office.item.shoes.model.entity.Shoes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.item.shoes.exception.HeadShoesExceptionMessage.NOT_FOUND;


@Repository("headOfficeShoesJpaRepositoryVerify")
@RequiredArgsConstructor
public class HeadShoesJpaRepositoryVerify {
    private final HeadShoesRepository repository;

    public void createShoes(Shoes entity){
        repository.save(entity);
    }

    public List<Shoes> findAll() {
        List<Shoes> shoes = repository.findAll();
        if(!shoes.isEmpty()){
            return shoes;
        }
        throw new HeadShoesException(NOT_FOUND);
    }

    public Shoes findById(Integer id) {
        Optional<Shoes> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new HeadShoesException(NOT_FOUND);
    }

    public void delete(Shoes entity) {
        repository.delete(entity);
    }

    public Shoes update(Shoes entity) {
        return repository.save(entity);
    }

}
