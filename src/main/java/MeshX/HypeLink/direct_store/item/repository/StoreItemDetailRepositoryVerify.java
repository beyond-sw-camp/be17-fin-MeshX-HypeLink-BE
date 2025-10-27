package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.direct_store.item.model.dto.response.ItemDetailsInfoRes;
import MeshX.HypeLink.direct_store.item.model.dto.response.QItemDetailsInfoRes;
import MeshX.HypeLink.direct_store.item.model.entity.QStoreCategory;
import MeshX.HypeLink.direct_store.item.model.entity.QStoreItem;
import MeshX.HypeLink.direct_store.item.model.entity.QStoreItemDetail;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
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

@Repository
@RequiredArgsConstructor
public class StoreItemDetailRepositoryVerify extends AbstractBatchSaveRepository<StoreItemDetail, String> {
    private final StoreItemDetailRepository repository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    protected String extractKey(StoreItemDetail entity) {
        return entity.getCompositeKey();
    }

    @Override
    protected List<String> findExistingKeys(List<String> keys) {
        return repository.findAllByCompositeKeyIn(keys)
                .stream()
                .map(StoreItemDetail::getCompositeKey)
                .toList();
    }

    @Override
    protected void saveAllToDb(List<StoreItemDetail> entities) {
        repository.saveAll(entities);
    }

    public Page<ItemDetailsInfoRes> findItemDetailWithRequestedTotalQuantity(
            Pageable pageable, String keyword, String category) {
        // 검색 조건 생성
        BooleanBuilder where = buildSearchCondition(keyword, category);

        // 요청 수량 합계 식
        NumberExpression<Integer> requestedQtySum = buildRequestedQuantitySum();

        // 메인 리스트 조회
        List<ItemDetailsInfoRes> content = fetchPurchaseOrderInfoList(pageable, where, requestedQtySum);

        // 총 개수 조회
        Long total = fetchTotalCount(where);

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    private BooleanBuilder buildSearchCondition(String keyword, String category) {
        QStoreItemDetail d = QStoreItemDetail.storeItemDetail;
        QStoreItem i = QStoreItem.storeItem;
        QStoreCategory c = QStoreCategory.storeCategory;

        BooleanBuilder where = new BooleanBuilder();

        // keyword 필터링 (빈 문자열이면 전체)
        if (isValid(keyword)) {
            where.andAnyOf(
                    c.category.containsIgnoreCase(keyword),
                    i.koName.containsIgnoreCase(keyword),
                    i.enName.containsIgnoreCase(keyword),
                    i.itemCode.containsIgnoreCase(keyword),
                    d.itemDetailCode.containsIgnoreCase(keyword),
                    d.color.containsIgnoreCase(keyword)
            );
        }

        // category 필터링 (all이면 전체)
        if (isValid(category) && !"all".equalsIgnoreCase(category)) {
            where.and(c.category.eq(category));
        }

        return where;
    }

    private NumberExpression<Integer> buildRequestedQuantitySum() {
        QPurchaseOrder p = QPurchaseOrder.purchaseOrder;

        return new CaseBuilder()
                .when(p.purchaseOrderState.eq(PurchaseOrderState.REQUESTED))
                .then(p.quantity)
                .otherwise(0)
                .sum()
                .coalesce(0);
    }

    private List<ItemDetailsInfoRes> fetchPurchaseOrderInfoList(
            Pageable pageable,
            BooleanBuilder where,
            NumberExpression<Integer> requestedQtySum
    ) {
        QStoreItemDetail d = QStoreItemDetail.storeItemDetail;
        QStoreItem i = QStoreItem.storeItem;
        QStoreCategory c = QStoreCategory.storeCategory;
        QPurchaseOrder p = QPurchaseOrder.purchaseOrder;

        return jpaQueryFactory
                .select(new QItemDetailsInfoRes(
                        d.id,
                        i.koName,
                        i.enName,
                        c.category,
                        d.color,
                        d.colorCode,
                        i.itemCode,
                        d.itemDetailCode,
                        d.stock,
                        requestedQtySum
                ))
                .from(d)
                .leftJoin(d.item, i)
                .leftJoin(i.category, c)
                .leftJoin(p).on(p.itemDetail.itemDetailCode.eq(d.itemDetailCode))
                .where(where)
                .groupBy(
                        i.id, i.koName, i.enName, c.category,
                        d.color, d.colorCode,
                        i.itemCode, d.itemDetailCode, d.stock
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private Long fetchTotalCount(BooleanBuilder where) {
        QStoreItemDetail d = QStoreItemDetail.storeItemDetail;
        QStoreItem i = QStoreItem.storeItem;
        QStoreCategory c = QStoreCategory.storeCategory;

        return jpaQueryFactory
                .select(d.id.countDistinct())
                .from(d)
                .leftJoin(d.item, i)
                .leftJoin(i.category, c)
                .where(where)
                .fetchOne();
    }

    private boolean isValid(String s) {
        return s != null && !s.isBlank();
    }
}
