package MeshX.HypeLink.direct_store.posOrder.repository;

import MeshX.HypeLink.direct_store.posOrder.exception.PosOrderException;
import MeshX.HypeLink.direct_store.posOrder.model.entity.PosOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.direct_store.posOrder.exception.PosOrderExceptionMessage.*;

@Repository
@RequiredArgsConstructor
public class PosOrderJpaRepositoryVerify {
    private final PosOrderRepository repository;

    public PosOrder save(PosOrder entity) {
        return repository.save(entity);
    }

    public PosOrder findById(Integer id) {
        Optional<PosOrder> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new PosOrderException(ORDER_NOT_FOUND);
    }

    public PosOrder findByOrderNumber(String orderNumber) {
        Optional<PosOrder> optional = repository.findByOrderNumber(orderNumber);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new PosOrderException(ORDER_NOT_FOUND);
    }

    public List<PosOrder> findAll() {
        List<PosOrder> orders = repository.findAll();
        if (!orders.isEmpty()) {
            return orders;
        }
        throw new PosOrderException(ORDER_LIST_EMPTY);
    }

    public List<PosOrder> findByDateRange(LocalDateTime start, LocalDateTime end) {
        List<PosOrder> orders = repository.findByCreatedAtBetween(start, end);
        if (!orders.isEmpty()) {
            return orders;
        }
        throw new PosOrderException(ORDER_LIST_EMPTY);
    }

    public List<PosOrder> findByMemberId(Integer memberId) {
        List<PosOrder> orders = repository.findByMemberId(memberId);
        if (!orders.isEmpty()) {
            return orders;
        }
        throw new PosOrderException(ORDER_LIST_EMPTY);
    }

    public void delete(PosOrder entity) {
        repository.delete(entity);
    }
}