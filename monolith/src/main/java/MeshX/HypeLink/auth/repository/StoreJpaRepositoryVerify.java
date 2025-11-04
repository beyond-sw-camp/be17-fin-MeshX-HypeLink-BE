package MeshX.HypeLink.auth.repository;

import MeshX.HypeLink.auth.exception.AuthException;
import MeshX.HypeLink.auth.exception.AuthExceptionMessage;
import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.QStore;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.model.entity.StoreState;
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
public class StoreJpaRepositoryVerify {
    private final StoreRepository repository;
    private final JPAQueryFactory jpaQueryFactory;

    public void save(Store entity) {
        repository.save(entity);
    }

    public Store findById(Integer id) {
        Optional<Store> optional = repository.findById(id);
        if(optional.isEmpty()) {
            throw new AuthException(AuthExceptionMessage.STORE_NOT_FOUND);
        }
        return optional.get();
    }

    public List<Store> findAll() {
        return repository.findAll();
    }

    public Page<Store> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Store> findAllWithMember()  {
        return repository.findAllWithMember();
    }

    public Store findByMember(Member member) {
        Optional<Store> optional = repository.findByMember(member);
        if(optional.isEmpty()) {
            throw new AuthException(AuthExceptionMessage.STORE_NOT_FOUND);
        }
        return optional.get();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public Page<Store> findAll(Pageable pageable, String keyWord, String status) {
        QStore store = QStore.store;
        BooleanBuilder builder = new BooleanBuilder();

        // --- 키워드 검색 (매장명 또는 주소) ---
        if (keyWord != null && !keyWord.trim().isEmpty()) {
            builder.and(
                    store.member.name.containsIgnoreCase(keyWord)
                            .or(store.member.address.containsIgnoreCase(keyWord))
            );
        }

        // --- 상태 필터 ---
        if (status != null && !"all".equalsIgnoreCase(status)) {
            StoreState storeState = StoreState.fromDescription(status);
            builder.and(store.storeState.eq(storeState));
        }

        // --- 메인 쿼리 ---
        List<Store> results = jpaQueryFactory
                .selectFrom(store)
                .leftJoin(store.member).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(store.id.desc())
                .fetch();

        // --- 카운트 쿼리 ---
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(store.count())
                .from(store)
                .where(builder);

        // --- 결과 반환 ---
        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }
}