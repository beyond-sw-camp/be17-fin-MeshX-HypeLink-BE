package MeshX.HypeLink.direct_store.item.service;

import MeshX.HypeLink.direct_store.item.repository.StoreCategoryJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.item.repository.StoreItemJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreItemService {
    private final StoreItemJpaRepositoryVerify storeItemRepository;
    private final StoreCategoryJpaRepositoryVerify storeCategoryRepository;


}
