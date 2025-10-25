package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
@RequiredArgsConstructor
public class StoreItemJpaRepositoryVerify {
    private final StoreItemRepository repository;

    public StoreItem save(StoreItem entity, Store store) {
        Optional<StoreItem> optional = repository.findByItemCodeAndStore(entity.getItemCode(), store);

        if (optional.isPresent()) {
            StoreItem existing = optional.get();
            boolean isModified = filtering(entity, existing);

            if (isModified) {
                return repository.save(existing);
            }
            return existing;
        }

        return repository.save(entity);
    }

    public StoreItem findById(Integer id) {
        Optional<StoreItem> optional = repository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }

    private boolean filtering(StoreItem entity, StoreItem existing) {
        boolean isModified = false;

        // 공통 헬퍼로 간결하게 정리
        isModified |= verifyField(existing.getCategory(), entity.getCategory(), existing::updateCategory);
        isModified |= verifyField(existing.getEnName(), entity.getEnName(), existing::updateEnName);
        isModified |= verifyField(existing.getKoName(), entity.getKoName(), existing::updateKoName);
        isModified |= verifyField(existing.getUnitPrice(), entity.getUnitPrice(), existing::updateUnitPrice);
        isModified |= verifyField(existing.getAmount(), entity.getAmount(), existing::updateAmount);
        isModified |= verifyField(existing.getContent(), entity.getContent(), existing::updateContent);
        isModified |= verifyField(existing.getCompany(), entity.getCompany(), existing::updateCompany);

        return isModified;
    }

    /**
     * 공통 필드 검증 및 수정 헬퍼
     */
    private <T> boolean verifyField(T oldVal, T newVal, Consumer<T> updater) {
        if (!Objects.equals(oldVal, newVal)) {
            updater.accept(newVal);
            return true;
        }
        return false;
    }
}
