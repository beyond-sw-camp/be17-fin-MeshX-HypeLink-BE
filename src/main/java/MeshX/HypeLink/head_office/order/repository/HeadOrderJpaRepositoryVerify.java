package MeshX.HypeLink.head_office.order.repository;

import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.head_office.order.exception.HeadOrderException;
import MeshX.HypeLink.head_office.order.model.entity.HeadOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.order.exception.HeadOrderExceptionMessage.NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class HeadOrderJpaRepositoryVerify {
    private final HeadOrderRepository repository;

    public void createOrder(HeadOrder entity) {
        repository.save(entity);
    }

    public void delete(HeadOrder entity) {
        repository.delete(entity);
    }

    public HeadOrder findById(Integer id) {
        Optional<HeadOrder> optional = repository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new HeadOrderException(NOT_FOUND);
    }

    public List<HeadOrder> findAll() {
        List<HeadOrder> headOrders = repository.findAll();
        if(!headOrders.isEmpty()) {
            return headOrders;
        }
        throw new HeadOrderException(NOT_FOUND);
    }

    public Page<HeadOrder> findAll(PageReq pageReq) {
        Page<HeadOrder> page = repository.findAll(pageReq.toPageRequest());
        if (page.hasContent()) {
            return page;
        }
        throw new HeadOrderException(NOT_FOUND);
    }


    public HeadOrder update(HeadOrder entity) {
        return repository.save(entity);
    }
}
