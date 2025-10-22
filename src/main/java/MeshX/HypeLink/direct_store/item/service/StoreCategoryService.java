package MeshX.HypeLink.direct_store.item.service;

import MeshX.HypeLink.direct_store.item.repository.StoreCategoryJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreCategoryService {
    private final StoreCategoryJpaRepositoryVerify storeCategoryRepository;

    public void save() {

    }
}
