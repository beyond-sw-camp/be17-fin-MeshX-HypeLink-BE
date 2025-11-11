package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.head_office.item.model.entity.Category;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.model.entity.QCategory;
import MeshX.HypeLink.head_office.item.model.entity.QItem;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static MeshX.HypeLink.head_office.item.exception.ItemExceptionMessage.*;

@Repository
@RequiredArgsConstructor
public class ItemJpaRepositoryVerify {
    private final ItemRepository repository;
    private final JPAQueryFactory jpaQueryFactory;

    public Item save(Item entity) {
        return repository.save(entity);
    }

    public void saveWithId(Item entity) {
        repository.upsert(entity);
    }

    public void merge(Item entity) {
        repository.save(entity);
    }

    public Boolean isExist(String itemCode) {
        Optional<Item> optional = repository.findByItemCode(itemCode);

        if(optional.isPresent()){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public Item findById(Integer id) {
        Optional<Item> optional = repository.findById(id);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_ID);
        }

        return optional.get();
    }

    public Item findByItemCode(String itemCode) {
        Optional<Item> optional = repository.findByItemCode(itemCode);

        if(optional.isEmpty()){
            throw new BaseException(NOTFOUND_ID);
        }

        return optional.get();
    }

    public List<Item> findItems() {
        return repository.findAllWithImage();
    }

    public List<Item> findItemsByCategory(Category category) {
        return repository.findByCategory(category);
    }

    public Page<Item> findItemsByCategoryWithPaging(Category category, Pageable pageable) {
        return repository.findByCategory(category, pageable);
    }

    public List<Item> findItemsByName(String name) {
        return Stream.of(
                        repository.findByEnName(name),
                        repository.findByKoName(name),
                        repository.findByCompany(name)
                )
                .flatMap(List::stream)
                .distinct() // 중복 제거
                .toList();
    }

    public Page<Item> findItemsByNameWithPaging(String name, Pageable pageable) {
        return Stream.of(
                        repository.findByEnName(name, pageable),
                        repository.findByKoName(name, pageable),
                        repository.findByCompany(name, pageable)
                )
                .flatMap(page -> page.getContent().stream())
                .distinct()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
    }

    public Page<Item> findItemsWithPaging(Pageable pageable, String keyword, String category) {
        QItem item = QItem.item;
        QCategory c = QCategory.category1;

        BooleanBuilder builder = new BooleanBuilder();

        // --- 카테고리 필터 ---
        if (category != null && !"all".equalsIgnoreCase(category.trim())) {
            builder.and(item.category.category.eq(category));
        }

        // --- 키워드 필터 ---
        if (keyword != null && !keyword.trim().isEmpty()) {
            builder.andAnyOf(
                    item.itemCode.containsIgnoreCase(keyword),
                    item.koName.containsIgnoreCase(keyword),
                    item.enName.containsIgnoreCase(keyword),
                    item.company.containsIgnoreCase(keyword)
            );
        }

        // --- 쿼리 실행 ---
        List<Item> content = jpaQueryFactory
                .selectFrom(item)
                .leftJoin(item.category, c).fetchJoin()
                .leftJoin(item.itemImages).fetchJoin() // 이미지까지 fetch 조인 (원래 findAllWithImages의 역할)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(item.id.desc())
                .fetch();

        // --- 전체 개수 ---
        Long total = jpaQueryFactory
                .select(item.count())
                .from(item)
                .leftJoin(item.category, c)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    public Page<Item> findItemsWithRelations(Pageable pageable) {
        QItem item = QItem.item;

        // 먼저 Item의 ID만 페이징
        List<Integer> ids = jpaQueryFactory
                .select(item.id)
                .from(item)
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (ids.isEmpty()) return new PageImpl<>(Collections.emptyList(), pageable, 0);

        // 이제 ID 리스트로 실제 Item + 연관관계 fetchJoin
        List<Item> content = jpaQueryFactory
                .selectFrom(item)
                .leftJoin(item.category).fetchJoin()
                .leftJoin(item.itemDetails).fetchJoin()
                .where(item.id.in(ids))
                .orderBy(item.id.desc())
                .distinct()
                .fetch();

        // 전체 count
        Long total = jpaQueryFactory
                .select(item.count())
                .from(item)
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    public void deleteItem(Integer itemId) {
        repository.deleteById(itemId);
    }
}
