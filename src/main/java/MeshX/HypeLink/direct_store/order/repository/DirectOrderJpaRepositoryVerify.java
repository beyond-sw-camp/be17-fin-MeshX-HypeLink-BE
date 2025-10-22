package MeshX.HypeLink.direct_store.order.repository;

import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.direct_store.order.exception.DirectOrderException;
import MeshX.HypeLink.direct_store.order.model.entity.DirectOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.order.exception.PurchaseOrderExceptionMessage.NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class DirectOrderJpaRepositoryVerify {
    private final DirectOrderRepository repository;

    public void createOrder(DirectOrder entity) {
        repository.save(entity);
    }

    public void delete(DirectOrder entity) {
        repository.delete(entity);
    }

    public DirectOrder findById(Integer id) {
        Optional<DirectOrder> optional = repository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new DirectOrderException(NOT_FOUND);
    }

    public List<DirectOrder> findAll() {
        List<DirectOrder> directOrders = repository.findAll();
        if(!directOrders.isEmpty()) {
            return directOrders;
        }
        throw new DirectOrderException(NOT_FOUND);
    }
    public Page<DirectOrder> findAll(PageReq pageReq) {
        Page<DirectOrder> page = repository.findAll(pageReq.toPageRequest());
        if (page.hasContent()) {
            return page;
        }
        throw new DirectOrderException(NOT_FOUND);
    }

    public DirectOrder update(DirectOrder entity) {
        return repository.save(entity);
    }
}
