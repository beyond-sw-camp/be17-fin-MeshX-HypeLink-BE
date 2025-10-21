package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.head_office.item.model.entity.Category;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static MeshX.HypeLink.head_office.item.exception.ItemExceptionMessage.*;

@Repository
@RequiredArgsConstructor
public class ItemJpaRepositoryVerify {
    private final ItemRepository repository;

    public void save(Item entity) {
        repository.save(entity);
    }

    public void saveList(List<Item> items) {
        repository.saveAll(items);
    }

    public void merge(Item entity) {
        repository.save(entity);
    }

    public void mergeList(List<Item> items) {
        repository.saveAll(items);
    }

    public List<Item> isExist(String itemCode) {
        List<Item> items = repository.findByItemCode(itemCode);

        if(items.isEmpty()){
            return items;
        }

        throw new BaseException(NOTFOUND_ITEM_CODE);
    }

    public Item findById(Integer id) {
        Optional<Item> optional = repository.findByIdAndIsDeletedIsFalse(id);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_ID);
        }

        return optional.get();
    }

    public Item findByIdWithLock(Integer id) {
        Optional<Item> optional = repository.findByIdForUpdateWithLock(id);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_ID);
        }

        return optional.get();
    }

    public List<Item> findItems() {
        return repository.findAll();
    }

    public Page<PurchaseOrderInfoRes> findItemsAndPurchaseOrdersWithPaging(Pageable pageable) {
        return repository.findItemWithRequestedTotalQuantity(pageable);
    }

    public Page<Item> findItemsWithPaging(Pageable pageable) {
        return repository.findAllByIsDeletedIsFalse(pageable);
    }

    public List<Item> findItemsByCategory(Category category) {
        return repository.findByCategoryAndIsDeletedIsFalse(category);
    }

    public Page<Item> findItemsByCategoryWithPaging(Category category, Pageable pageable) {
        return repository.findByCategoryContainingAndIsDeletedIsFalse(category, pageable);
    }

    public List<Item> findItemsByName(String name) {
        return Stream.of(
                        repository.findByEnNameAndIsDeletedIsFalse(name),
                        repository.findByKoNameAndIsDeletedIsFalse(name),
                        repository.findByCompanyAndIsDeletedIsFalse(name)
                )
                .flatMap(List::stream)
                .distinct() // 중복 제거
                .toList();
    }

    public Page<Item> findItemsByNameWithPaging(String name, Pageable pageable) {
        return Stream.of(
                        repository.findByEnNameContainingAndIsDeletedIsFalse(name, pageable),
                        repository.findByKoNameContainingAndIsDeletedIsFalse(name, pageable),
                        repository.findByCompanyContainingAndIsDeletedIsFalse(name, pageable)
                )
                .flatMap(page -> page.getContent().stream())
                .distinct()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
    }

    public List<Item> findByItemCode(String itemCode) {
        return repository.findByItemCodeAndIsDeletedIsFalse(itemCode);
    }

    public Item findByItemDetailCode(String itemCode) {
        Optional<Item> optional = repository.findByItemDetailCodeAndIsDeletedIsFalse(itemCode);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_NAME);
        }

        return optional.get();
    }
}
