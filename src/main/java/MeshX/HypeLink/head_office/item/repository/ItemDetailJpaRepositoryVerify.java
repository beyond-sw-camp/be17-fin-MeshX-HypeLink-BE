package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.item.exception.ItemExceptionMessage.NOTFOUND_ID;

@Repository
@RequiredArgsConstructor
public class ItemDetailJpaRepositoryVerify {
    private final ItemDetailRepository repository;

    public void save(ItemDetail entity) {
        repository.save(entity);
    }

    public void saveAll(List<ItemDetail> entities) {
        repository.saveAll(entities);
    }

    public ItemDetail findById(Integer id) {
        Optional<ItemDetail> optional = repository.findById(id);
        if(optional.isPresent()) {
            optional.get();
        }
        throw new BaseException(null);
    }

    public ItemDetail findByItemDetailCode(String itemDetailCode) {
        Optional<ItemDetail> optional = repository.findByItemDetailCode(itemDetailCode);
        if(optional.isPresent()) {
            optional.get();
        }
        throw new BaseException(null);
    }

    public Page<ItemDetail> findByAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<ItemDetail> findByItem(Item item) {
        List<ItemDetail> itemDetails = repository.findByItem(item);
        if(itemDetails.isEmpty()) {
            throw new BaseException(null);
        }
        return itemDetails;
    }

    public List<ItemDetail> findByItemId(Integer itemId) {
        List<ItemDetail> itemDetails = repository.findByItemId(itemId);
        if(itemDetails.isEmpty()) {
            throw new BaseException(null);
        }
        return itemDetails;
    }

    public ItemDetail findByIdWithLock(Integer id) {
        Optional<ItemDetail> optional = repository.findByIdForUpdateWithLock(id);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_ID);
        }

        return optional.get();
    }

    public Page<PurchaseOrderInfoRes> findItemsAndPurchaseOrdersWithPaging(Pageable pageable) {
        return repository.findItemDetailWithRequestedTotalQuantity(pageable);
    }

    public void merge(ItemDetail entity) {
        repository.save(entity);
    }
}
