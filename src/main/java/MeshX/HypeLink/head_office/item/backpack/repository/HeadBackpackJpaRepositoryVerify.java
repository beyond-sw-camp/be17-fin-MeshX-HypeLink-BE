package MeshX.HypeLink.head_office.item.backpack.repository;


import MeshX.HypeLink.head_office.item.backpack.exception.HeadBackpackException;
import MeshX.HypeLink.head_office.item.backpack.model.entity.BackPack;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.item.backpack.exception.HeadBackpackExceptionMessage.NOT_FOUND;


@Repository("headOfficeBackpackJpaRepositoryVerify")
@RequiredArgsConstructor
public class HeadBackpackJpaRepositoryVerify {
    private final HeadBackpackRepository repository;

    public void createBackpack(BackPack entity){
        repository.save(entity);
    }

    public List<BackPack> findAll() {
        List<BackPack> backpacks = repository.findAll();
        if(!backpacks.isEmpty()){
            return backpacks;
        }
        throw new HeadBackpackException(NOT_FOUND);
    }

    public BackPack findById(Integer id) {
        Optional<BackPack> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new HeadBackpackException(NOT_FOUND);
    }

    public void delete(BackPack entity) {
        repository.delete(entity);
    }

    public BackPack update(BackPack entity) {
        return repository.save(entity);
    }

}
