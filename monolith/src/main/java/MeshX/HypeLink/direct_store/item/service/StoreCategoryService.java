package MeshX.HypeLink.direct_store.item.service;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreCategoriesReq;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreCategoryInfoListRes;
import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import MeshX.HypeLink.direct_store.item.repository.StoreCategoryJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreCategoryService {
    private final StoreJpaRepositoryVerify storeRepository;
    private final StoreCategoryJpaRepositoryVerify storeCategoryRepository;

    @Transactional
    public void saveAll(SaveStoreCategoriesReq dto) {
        Store store = storeRepository.findById(dto.getStoreId());
        storeCategoryRepository.saveAllSkipDuplicate(dto.toEntity(store));
    }

    public StoreCategoryInfoListRes getList() {
        List<StoreCategory> list = storeCategoryRepository.findAll();
        return StoreCategoryInfoListRes.toDto(list);
    }
}
