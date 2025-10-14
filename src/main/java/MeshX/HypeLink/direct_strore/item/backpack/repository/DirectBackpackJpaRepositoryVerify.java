package MeshX.HypeLink.direct_strore.item.backpack.repository;

import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.direct_strore.item.backpack.exception.DirectBackpackException;
import MeshX.HypeLink.direct_strore.item.backpack.model.entity.DirectBackPack;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.direct_strore.item.backpack.exception.DirectBackpackExceptionMessage.*;

@Repository("directStoreBackpackJpaRepositoryVerify")
@RequiredArgsConstructor
public class DirectBackpackJpaRepositoryVerify {
    private final DirectBackpackRepository repository;

    public void createBackpack(DirectBackPack entity){
        repository.save(entity);
    }

    public List<DirectBackPack> findAll() {
        List<DirectBackPack> backpacks = repository.findAll();
        if(!backpacks.isEmpty()){
            return backpacks;
        }
        throw new DirectBackpackException(NOT_FOUND);
    }

    public Page<DirectBackPack> findAll(PageReq pageReq) {
        if (!pageReq.isValid()) {//null 값 체크 + O 이상
            if (pageReq.getPage() == null || pageReq.getPage() < 0) {
                throw new DirectBackpackException(INVALID_PAGE);
            }
            if (pageReq.getPageSize() == null || pageReq.getPageSize() <= 0) {
                throw new DirectBackpackException(INVALID_PAGE_SIZE);
            }
        }
        Page<DirectBackPack> page = repository.findAll(pageReq.toPageRequest());
        if (page.hasContent()) {
            return page;
        }
        throw new DirectBackpackException(NOT_FOUND);
    }

    public DirectBackPack findById(Integer id) {
        Optional<DirectBackPack> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new DirectBackpackException(NOT_FOUND);
    }

    public void delete(DirectBackPack entity) {
        repository.delete(entity);
    }

    public DirectBackPack update(DirectBackPack entity) {
        return repository.save(entity);
    }

}
