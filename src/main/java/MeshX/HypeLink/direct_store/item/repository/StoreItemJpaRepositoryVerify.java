package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.direct_store.item.exception.StoreItemException;
import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static MeshX.HypeLink.direct_store.item.exception.StoreItemExceptionMessage.NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class StoreItemJpaRepositoryVerify {
    private final StoreItemRepository repository;

    public void save(StoreItem entity) {
        repository.save(entity);
    }

    public StoreItem findById(Integer id) {
        Optional<StoreItem> optional = repository.findById(id);

        if(optional.isPresent()) {
            return optional.get();
        }

        throw new StoreItemException(NOT_FOUND);
    }

    public List<StoreItem> findByItemCode(String itemCode) {
        List<StoreItem> list = repository.findByItemCode(itemCode);

        if(list.isEmpty()) {
            throw new StoreItemException(NOT_FOUND);
        }

        return list;
    }

    public StoreItem findByItemDetailCode(String itemDetailCode) {
        Optional<StoreItem> optional = repository.findByItemDetailCode(itemDetailCode);

        if(optional.isPresent()) {
            return optional.get();
        }

        throw new StoreItemException(NOT_FOUND);
    }

    public PageRes<StoreItem> findByItemName(String name, Pageable pageable) {
        PageImpl<StoreItem> itemPage = Stream.of(
                        repository.findByEnName(name, pageable),
                        repository.findByKoName(name, pageable)
                ).flatMap(page -> page.getContent().stream())
                .distinct()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));

        return PageRes.toDto(itemPage);
    }

    public PageRes<StoreItem> findByItemCategory(StoreCategory category, Pageable pageable) {
        Page<StoreItem> itemPage = repository.findByCategory(category, pageable);

        return PageRes.toDto(itemPage);
    }

    public void merge(StoreItem entity) {
        repository.save(entity);
    }
}
