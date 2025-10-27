package MeshX.HypeLink.head_office.order.repository;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.head_office.order.exception.PurchaseOrderException;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState;
import MeshX.HypeLink.head_office.order.model.entity.QPurchaseOrder;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.order.exception.PurchaseOrderExceptionMessage.NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class PurchaseOrderJpaRepositoryVerify {
    private final PurchaseOrderRepository repository;
    private final JPAQueryFactory jpaQueryFactory;

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

    public List<PurchaseOrder> findAllByPurchaseOrderState(PurchaseOrderState state) {
        return repository.findAllByPurchaseOrderState(state);
    }

    public Page<PurchaseOrder> findAllOrderWithPriority(Pageable pageable) {
        QPurchaseOrder p = QPurchaseOrder.purchaseOrder;

        // CaseBuilder: 상태가 REQUESTED면 0, 나머지는 1 (→ 0이 우선 정렬됨)
        NumberExpression<Integer> requestedPriority =
                new CaseBuilder()
                        .when(p.purchaseOrderState.eq(PurchaseOrderState.REQUESTED))
                        .then(0)
                        .otherwise(1);

        // 전체 조회 (페이징 + 정렬)
        List<PurchaseOrder> content = jpaQueryFactory
                .selectFrom(p)
                .orderBy(
                        requestedPriority.asc(), // REQUESTED 우선
                        p.id.desc()                // id 오름차순
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // total count
        Long total = jpaQueryFactory
                .select(p.count())
                .from(p)
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    public Page<PurchaseOrder> findByRequester(Member requester, Pageable pageable) {
        QPurchaseOrder p = QPurchaseOrder.purchaseOrder;

        NumberExpression<Integer> requestedPriority =
                new CaseBuilder()
                        .when(p.purchaseOrderState.eq(PurchaseOrderState.REQUESTED))
                        .then(0)
                        .otherwise(1);

        List<PurchaseOrder> content = jpaQueryFactory
                .selectFrom(p)
                .where(p.requester.eq(requester))
                .orderBy(
                        requestedPriority.asc(),
                        p.id.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(p.count())
                .from(p)
                .where(p.requester.eq(requester))
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    public PurchaseOrder update(PurchaseOrder entity) {
        return repository.save(entity);
    }
}
