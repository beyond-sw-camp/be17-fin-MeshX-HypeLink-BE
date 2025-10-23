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

    public void merge(Item entity) {
        repository.save(entity);
    }

    public Boolean isExist(String itemCode) {
        Optional<Item> optional = repository.findByItemCode(itemCode);

        if(optional.isPresent()){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public Item findById(Integer id) {
        Optional<Item> optional = repository.findById(id);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_ID);
        }

        return optional.get();
    }

    public Item findByItemCode(String itemCode) {
        Optional<Item> optional = repository.findByItemCode(itemCode);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_ID);
        }

        return optional.get();
    }

    public List<Item> findItems() {
        return repository.findAll();
    }

    public Page<Item> findItemsWithPaging(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Item> findItemsByCategory(Category category) {
        return repository.findByCategory(category);
    }

    public Page<Item> findItemsByCategoryWithPaging(Category category, Pageable pageable) {
        return repository.findByCategory(category, pageable);
    }

    public List<Item> findItemsByName(String name) {
        return Stream.of(
                        repository.findByEnName(name),
                        repository.findByKoName(name),
                        repository.findByCompany(name)
                )
                .flatMap(List::stream)
                .distinct() // 중복 제거
                .toList();
    }

    public Page<Item> findItemsByNameWithPaging(String name, Pageable pageable) {
        return Stream.of(
                        repository.findByEnName(name, pageable),
                        repository.findByKoName(name, pageable),
                        repository.findByCompany(name, pageable)
                )
                .flatMap(page -> page.getContent().stream())
                .distinct()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
    }
}
