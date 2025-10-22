package MeshX.HypeLink.head_office.order.repository;

import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.head_office.order.exception.PurchaseOrderException;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.order.exception.PurchaseOrderExceptionMessage.NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class PurchaseOrderJpaRepositoryVerify {
    private final PurchaseOrderRepository repository;

    public void createOrder(PurchaseOrder entity) {
        repository.save(entity);
    }

    public PurchaseOrder findById(Integer id) {
        Optional<PurchaseOrder> optional = repository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new PurchaseOrderException(NOT_FOUND);
    }

    public List<PurchaseOrder> findAll() {
        List<PurchaseOrder> purchaseOrders = repository.findAll();
        if(!purchaseOrders.isEmpty()) {
            return purchaseOrders;
        }
        throw new PurchaseOrderException(NOT_FOUND);
    }

    public Page<PurchaseOrder> findAll(Pageable pageReq) {
        Page<PurchaseOrder> page = repository.findAll(pageReq);
        if (page.hasContent()) {
            return page;
        }
        throw new PurchaseOrderException(NOT_FOUND);
    }


    public PurchaseOrder update(PurchaseOrder entity) {
        return repository.save(entity);
    }
}
