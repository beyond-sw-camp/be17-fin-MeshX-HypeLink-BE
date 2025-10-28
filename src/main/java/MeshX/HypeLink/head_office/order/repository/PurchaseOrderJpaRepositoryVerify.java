package MeshX.HypeLink.head_office.order.repository;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.head_office.order.exception.PurchaseOrderException;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState;
import MeshX.HypeLink.head_office.order.model.entity.QPurchaseOrder;
import com.querydsl.core.BooleanBuilder;
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

    public Page<PurchaseOrder> findAllSearchOrderWithPriority(String keyword, String status, Pageable pageable) {
        QPurchaseOrder p = QPurchaseOrder.purchaseOrder;

        // 상태 우선순위: REQUESTED → 0, 그 외 → 1
        NumberExpression<Integer> requestedPriority =
                new CaseBuilder()
                        .when(p.purchaseOrderState.eq(PurchaseOrderState.REQUESTED))
                        .then(0)
                        .otherwise(1);

        // 동적 조건 빌더
        BooleanBuilder builder = new BooleanBuilder();

        // 상태 필터
        if (status != null && !"all".equalsIgnoreCase(status)) {
            PurchaseOrderState state = PurchaseOrderState.fromDescription(status);
            if (state != null) {
                builder.and(p.purchaseOrderState.eq(state));
            }
        }

        // 키워드 필터 (null 또는 빈 문자열이면 무시)
        if (keyword != null && !keyword.trim().isEmpty()) {
            builder.andAnyOf(
                    p.requester.name.containsIgnoreCase(keyword),
                    p.itemDetail.item.koName.containsIgnoreCase(keyword),
                    p.itemDetail.item.enName.containsIgnoreCase(keyword),
                    p.itemDetail.itemDetailCode.containsIgnoreCase(keyword)
            );
        }

        // 데이터 조회
        List<PurchaseOrder> content = jpaQueryFactory
                .selectFrom(p)
                .where(builder)
                .orderBy(
                        requestedPriority.asc(), // REQUESTED 우선
                        p.id.desc()              // id 내림차순
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수
        Long total = jpaQueryFactory
                .select(p.count())
                .from(p)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    public Page<PurchaseOrder> findSearchByRequester(String keyword, String status, Member requester, Pageable pageable) {
        QPurchaseOrder p = QPurchaseOrder.purchaseOrder;

        // 상태 우선순위: REQUESTED → 0, 그 외 → 1
        NumberExpression<Integer> requestedPriority =
                new CaseBuilder()
                        .when(p.purchaseOrderState.eq(PurchaseOrderState.REQUESTED))
                        .then(0)
                        .otherwise(1);

        // 동적 조건 빌더
        BooleanBuilder builder = new BooleanBuilder();

        // 요청자 필터 (항상 포함)
        builder.and(p.requester.eq(requester));

        // 상태 필터
        if (status != null && !"all".equalsIgnoreCase(status)) {
            PurchaseOrderState state = PurchaseOrderState.fromDescription(status);
            if (state != null) {
                builder.and(p.purchaseOrderState.eq(state));
            }
        }

        // 키워드 필터 (null 또는 빈 문자열이면 무시)
        if (keyword != null && !keyword.trim().isEmpty()) {
            builder.andAnyOf(
                    p.requester.name.containsIgnoreCase(keyword),
                    p.itemDetail.item.koName.containsIgnoreCase(keyword),
                    p.itemDetail.item.enName.containsIgnoreCase(keyword),
                    p.itemDetail.itemDetailCode.containsIgnoreCase(keyword)
            );
        }

        // 데이터 조회
        List<PurchaseOrder> content = jpaQueryFactory
                .selectFrom(p)
                .where(builder)
                .orderBy(
                        requestedPriority.asc(), // REQUESTED 우선
                        p.id.desc()              // id 내림차순
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수
        Long total = jpaQueryFactory
                .select(p.count())
                .from(p)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }


    public PurchaseOrder update(PurchaseOrder entity) {
        return repository.save(entity);
    }
}
