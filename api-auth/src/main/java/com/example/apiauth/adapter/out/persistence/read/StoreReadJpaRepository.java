package com.example.apiauth.adapter.out.persistence.read;

import com.example.apiauth.adapter.out.persistence.read.entity.QStoreReadEntity;
import com.example.apiauth.adapter.out.persistence.read.entity.StoreReadEntity;
import com.example.apiauth.domain.model.value.StoreState;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StoreReadJpaRepository {

    private final StoreReadRepository repository;

    @PersistenceContext(unitName = "readEntityManagerFactory")
    private EntityManager entityManager;

    public Optional<StoreReadEntity> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<StoreReadEntity> findByIdWithMember(Integer id) {
        return repository.findByIdWithMember(id);
    }

    public List<StoreReadEntity> findByMemberIdWithMember(Integer memberId) {
        return repository.findByMemberIdWithMember(memberId);
    }

    public List<StoreReadEntity> findAllWithMember() {
        return repository.findAllWithMember();
    }

    public Page<StoreReadEntity> findAll(Pageable pageable, String keyWord, String status) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QStoreReadEntity store = QStoreReadEntity.storeReadEntity;
        BooleanBuilder builder = new BooleanBuilder();

        // 키워드 검색 (매장명 또는 주소)
        if (keyWord != null && !keyWord.trim().isEmpty()) {
            builder.and(
                    store.member.name.containsIgnoreCase(keyWord)
                            .or(store.member.address.containsIgnoreCase(keyWord))
            );
        }

        // 상태 필터
        if (status != null && !"all".equalsIgnoreCase(status)) {
            StoreState storeState = StoreState.fromDescription(status);
            builder.and(store.storeState.eq(storeState));
        }

        // 메인 쿼리
        List<StoreReadEntity> results = queryFactory
                .selectFrom(store)
                .leftJoin(store.member).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(store.id.desc())
                .fetch();

        // 카운트 쿼리
        JPAQuery<Long> countQuery = queryFactory
                .select(store.count())
                .from(store)
                .where(builder);

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }
}
