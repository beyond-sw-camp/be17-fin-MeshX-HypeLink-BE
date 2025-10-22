package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.model.entity.ItemImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemImageJpaRepositoryVerify {
    private final ItemImageRepository repository;

    public void save(ItemImage entity) {
        repository.save(entity);
    }

    public ItemImage findById(Integer id) {
        Optional<ItemImage> optional = repository.findById(id);
        if(optional.isPresent()) {
            optional.get();
        }
        throw new BaseException(null);
    }

    public List<ItemImage> findByItem(Item item) {
        List<ItemImage> itemImages = repository.findByItem(item);
        if(itemImages.isEmpty()) {
            throw new BaseException(null);
        }
        return itemImages;
    }

    public void merge(ItemImage entities) {
        repository.save(entities);
    }
}
