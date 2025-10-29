package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.MemberRepository;
import MeshX.HypeLink.auth.service.MemberService;
import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.head_office.item.model.entity.*;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoRes;
import MeshX.HypeLink.head_office.order.model.dto.response.QPurchaseOrderInfoRes;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState;
import MeshX.HypeLink.head_office.order.model.entity.QPurchaseOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.item.exception.ItemExceptionMessage.NOTFOUND_ID;

@Repository
@RequiredArgsConstructor
public class ItemDetailJpaRepositoryVerify {
    private final ItemDetailRepository repository;
    private final JPAQueryFactory jpaQueryFactory;
    private final MemberJpaRepositoryVerify memberRepository;

    public void save(ItemDetail entity) {
        repository.save(entity);
    }

    public void saveAll(List<ItemDetail> entities) {
        repository.saveAll(entities);
    }

    public ItemDetail findById(Integer id) {
        Optional<ItemDetail> optional = repository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }

    public ItemDetail findByItemDetailCode(String itemDetailCode) {
        Optional<ItemDetail> optional = repository.findByItemDetailCode(itemDetailCode);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }

    public ItemDetail findByItemDetailCodeWithLock(String itemDetailCode) {
        Optional<ItemDetail> optional = repository.findByItemDetailCodeForUpdateWithLock(itemDetailCode);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }

    public Page<ItemDetail> findByAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<ItemDetail> findByItem(Item item) {
        List<ItemDetail> itemDetails = repository.findByItem(item);
        if(itemDetails.isEmpty()) {
            throw new BaseException(null);
        }
        return itemDetails;
    }

    public List<ItemDetail> findByItemId(Integer itemId) {
        List<ItemDetail> itemDetails = repository.findByItemId(itemId);
        if(itemDetails.isEmpty()) {
            throw new BaseException(null);
        }
        return itemDetails;
    }

    public ItemDetail findByIdWithLock(Integer id) {
        Optional<ItemDetail> optional = repository.findByIdForUpdateWithLock(id);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_ID);
        }

        return optional.get();
    }

    public Page<PurchaseOrderInfoRes> findItemsAndPurchaseOrdersWithPaging(Pageable pageable, String keyword, String category) {
        Member head = memberRepository.findByEmail("hq@company.com");
//        return repository.findItemDetailWithRequestedTotalQuantity(pageable);
        return findItemDetailWithRequestedTotalQuantity(pageable, keyword, category, head);
    } // 변경

    public Page<PurchaseOrderInfoRes> findItemDetailWithRequestedTotalQuantity(
            Pageable pageable, String keyword, String category, Member head) {
        // 검색 조건 생성
        BooleanBuilder where = buildSearchCondition(keyword, category);

        // 요청 수량 합계 식
        NumberExpression<Integer> requestedQtySum = buildRequestedQuantitySum();

        // 메인 리스트 조회
        List<PurchaseOrderInfoRes> content = fetchPurchaseOrderInfoList(pageable, where, requestedQtySum, head);

        // 총 개수 조회
        Long total = fetchTotalCount(where);

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    private BooleanBuilder buildSearchCondition(String keyword, String category) {
        QItemDetail d = QItemDetail.itemDetail;
        QItem i = QItem.item;
        QCategory c = QCategory.category1;
        QColor col = QColor.color;

        BooleanBuilder where = new BooleanBuilder();

        // keyword 필터링 (빈 문자열이면 전체)
        if (isValid(keyword)) {
            where.andAnyOf(
                    c.category.containsIgnoreCase(keyword),
                    i.koName.containsIgnoreCase(keyword),
                    i.enName.containsIgnoreCase(keyword),
                    i.itemCode.containsIgnoreCase(keyword),
                    d.itemDetailCode.containsIgnoreCase(keyword),
                    col.colorName.containsIgnoreCase(keyword)
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

    private List<PurchaseOrderInfoRes> fetchPurchaseOrderInfoList(
            Pageable pageable,
            BooleanBuilder where,
            NumberExpression<Integer> requestedQtySum,
            Member head
    ) {
        QItemDetail d = QItemDetail.itemDetail;
        QItem i = QItem.item;
        QCategory c = QCategory.category1;
        QColor col = QColor.color;
        QPurchaseOrder p = QPurchaseOrder.purchaseOrder;

        return jpaQueryFactory
                .select(new QPurchaseOrderInfoRes(
                        d.id,
                        i.koName,
                        i.enName,
                        c.category,
                        col.colorName,
                        col.colorCode,
                        i.itemCode,
                        d.itemDetailCode,
                        d.stock,
                        requestedQtySum
                ))
                .from(d)
                .leftJoin(d.item, i)
                .leftJoin(i.category, c)
                .leftJoin(d.color, col)
                .leftJoin(p).on(p.itemDetail.eq(d).and(p.requester.eq(head)))
                .where(where)
                .groupBy(
                        i.id, i.koName, i.enName, c.category,
                        col.colorName, col.colorCode,
                        i.itemCode, d.itemDetailCode, d.stock
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private Long fetchTotalCount(BooleanBuilder where) {
        QItemDetail d = QItemDetail.itemDetail;
        QItem i = QItem.item;
        QCategory c = QCategory.category1;
        QColor col = QColor.color;

        return jpaQueryFactory
                .select(d.id.countDistinct())
                .from(d)
                .leftJoin(d.item, i)
                .leftJoin(i.category, c)
                .leftJoin(d.color, col)
                .where(where)
                .fetchOne();
    }

    private boolean isValid(String s) {
        return s != null && !s.isBlank();
    }

    public void merge(ItemDetail entity) {
        repository.save(entity);
    }
}
