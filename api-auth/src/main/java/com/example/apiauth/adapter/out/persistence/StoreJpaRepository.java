package com.example.apiauth.adapter.out.persistence;

import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.entity.QStoreEntity;
import com.example.apiauth.adapter.out.persistence.entity.StoreEntity;
import com.example.apiauth.domain.model.value.StoreState;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StoreJpaRepository {

    private final StoreRepository repository;
    private final JPAQueryFactory jpaQueryFactory;

    public StoreEntity save(StoreEntity entity) {
        return repository.save(entity);
    }

    public Optional<StoreEntity> findById(Integer id) {
        return repository.findById(id);
    }

    public List<StoreEntity> findAll() {
        return repository.findAll();
    }

    public List<StoreEntity> findAllWithMember() {
        return repository.findAllWithMember();
    }

    public Optional<StoreEntity> findByMember(MemberEntity member) {
        return repository.findByMember(member);
    }

    public Optional<StoreEntity> findByMember_Email(String memberEmail) {
        return repository.findByMember_Email(memberEmail);
    }

    public Optional<StoreEntity> findByMember_Id(Integer memberId) {
        return repository.findByMember_Id(memberId);
    }

    public Page<StoreEntity> findAll(Pageable pageable, String keyWord, String status) {
        QStoreEntity store = QStoreEntity.storeEntity;
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
        List<StoreEntity> results = jpaQueryFactory
                .selectFrom(store)
                .leftJoin(store.member).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(store.id.desc())
                .fetch();

        // 카운트 쿼리
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(store.count())
                .from(store)
                .where(builder);

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}