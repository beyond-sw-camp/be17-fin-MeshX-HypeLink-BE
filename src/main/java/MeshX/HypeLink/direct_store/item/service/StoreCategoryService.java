package MeshX.HypeLink.direct_store.item.service;

import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreCategoriesReq;
import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreCategoryReq;
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
    private final StoreCategoryJpaRepositoryVerify storeCategoryRepository;

    @Transactional
    public void saveAll(SaveStoreCategoriesReq dto) {
        storeCategoryRepository.saveAllSkipDuplicate(dto.toEntity());
    }

    public StoreCategoryInfoListRes getList() {
        List<StoreCategory> list = storeCategoryRepository.findAll();
        return StoreCategoryInfoListRes.toDto(list);
    }
}
