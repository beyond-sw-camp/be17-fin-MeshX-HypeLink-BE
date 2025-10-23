package MeshX.HypeLink.direct_store.item.service;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreItemListReq;
import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemImage;
import MeshX.HypeLink.direct_store.item.repository.StoreCategoryJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.item.repository.StoreItemDetailRepositoryVerify;
import MeshX.HypeLink.direct_store.item.repository.StoreItemImageRepositoryVerify;
import MeshX.HypeLink.direct_store.item.repository.StoreItemJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreItemService {
    private final StoreJpaRepositoryVerify storeRepository;
    private final StoreItemJpaRepositoryVerify storeItemRepository;
    private final StoreItemDetailRepositoryVerify storeItemDetailRepository;
    private final StoreItemImageRepositoryVerify storeItemImageRepository;
    private final StoreCategoryJpaRepositoryVerify storeCategoryRepository;

    @Transactional
    public void saveAll(SaveStoreItemListReq dto) {
        Store store = storeRepository.findById(dto.getStoreId());

        dto.getItems().forEach(one -> {
            StoreCategory category = storeCategoryRepository.findByCategory(one.getCategory());

            StoreItem item = one.toEntity(store, category);
            StoreItem storeItem = storeItemRepository.save(item);

            List<StoreItemDetail> itemDetails = one.getItemDetails().stream().map(detail -> detail.toEntity(storeItem)).toList();
            storeItemDetailRepository.saveAllSkipDuplicate(itemDetails);

            List<StoreItemImage> images = one.getImages().stream().map(image -> image.toEntity(storeItem)).toList();
            storeItemImageRepository.saveAllSkipDuplicate(images);
        });
    }
}
