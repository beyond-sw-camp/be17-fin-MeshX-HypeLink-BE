package com.example.apiitem.item.adaptor.out.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemQueryDSLRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<ItemEntity> findItemsWithPaging(Pageable pageable, String keyword, String category) {
        QItemEntity item = QItemEntity.itemEntity;
        QCategoryEntity c = QCategoryEntity.categoryEntity;

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
        List<ItemEntity> content = jpaQueryFactory
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
}
