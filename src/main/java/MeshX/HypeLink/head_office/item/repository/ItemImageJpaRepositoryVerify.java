package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.head_office.item.exception.ItemImageException;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.model.entity.ItemImage;
import MeshX.HypeLink.image.model.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.item.exception.ItemImageExceptionMessage.*;

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
        throw new ItemImageException(NOTFOUND_ID);
    }

    public ItemImage findByItemAndImage(Item item, Image image) {
        Optional<ItemImage> optional = repository.findByItemAndImageAndIsDeletedIsFalse(item, image);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new ItemImageException(NOTFOUND_ITEM_AND_IMAGE);
    }

    public List<ItemImage> findByItem(Item item) {
        return repository.findByItemAndIsDeletedIsFalse(item);
    }

    public void merge(ItemImage entities) {
        repository.save(entities);
    }

    public void delete(ItemImage item) {
        repository.delete(item);
    }
}
