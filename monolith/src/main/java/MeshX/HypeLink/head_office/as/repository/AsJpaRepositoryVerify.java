package MeshX.HypeLink.head_office.as.repository;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.head_office.as.exception.AsException;
import MeshX.HypeLink.head_office.as.model.entity.As;
import MeshX.HypeLink.head_office.as.model.entity.AsStatus;
import MeshX.HypeLink.head_office.as.model.entity.QAs;
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

import static MeshX.HypeLink.head_office.as.exception.AsExceptionMessage.AS_NOT_FOUNT;

@Repository
@RequiredArgsConstructor
public class AsJpaRepositoryVerify {
    private final AsRepository repository;
    private final JPAQueryFactory jpaQueryFactory;

    public As findById(Integer id) {
        Optional<As> as = repository.findById(id);
        if (as.isPresent()) {
            return as.get();
        }
        throw new AsException(AS_NOT_FOUNT);
    }

    public void save(As as) {
        repository.save(as);
    }

    public void delete(As as) {
        repository.delete(as);
    }

    public List<As> findByStore(Store store) {
        return repository.findByStore(store);
    }

    public Page<As> findAll(Pageable pageable) {
        Page<As> asPage = repository.findAll(pageable);
        if (asPage.isEmpty()) {
            throw new AsException(AS_NOT_FOUNT);
        }
        return asPage;
    }

    public Page<As> findAll(Member member, Pageable pageable, String keyWord, String status) {
        QAs as = QAs.as;
        BooleanBuilder builder = new BooleanBuilder();

        if (member != null) {
            builder.and(as.store.member.eq(member));
        }

        // --- 키워드 검색 ---
        if (keyWord != null && !keyWord.trim().isEmpty()) {
            builder.and(
                    as.title.containsIgnoreCase(keyWord)
                            .or(as.store.member.name.containsIgnoreCase(keyWord))
            );
        }

        // --- 상태 필터 ---
        if (status != null && !"all".equalsIgnoreCase(status)) {
            AsStatus asStatus = AsStatus.fromDescription(status);
            builder.and(as.status.eq(asStatus));
        }

        // --- 메인 쿼리 ---
        List<As> results = jpaQueryFactory
                .selectFrom(as)
                .leftJoin(as.store).fetchJoin()
                .leftJoin(as.store.member).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(as.id.desc())
                .fetch();

        // --- 카운트 쿼리 ---
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(as.count())
                .from(as)
                .where(builder);

        // --- 결과 반환 ---
        Page<As> asPage = PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);

        if (asPage.isEmpty()) {
            throw new AsException(AS_NOT_FOUNT);
        }

        return asPage;
    }

    public Page<As> findAll(Pageable pageable, String keyWord, String status) {
        QAs as = QAs.as;
        BooleanBuilder builder = new BooleanBuilder();

        // --- 키워드 검색 ---
        if (keyWord != null && !keyWord.trim().isEmpty()) {
            builder.and(
                    as.title.containsIgnoreCase(keyWord)
                            .or(as.store.member.name.containsIgnoreCase(keyWord))
            );
        }

        // --- 상태 필터 ---
        if (status != null && !"all".equalsIgnoreCase(status)) {
            AsStatus asStatus = AsStatus.fromDescription(status);
            builder.and(as.status.eq(asStatus));
        }

        // --- 메인 쿼리 ---
        List<As> results = jpaQueryFactory
                .selectFrom(as)
                .leftJoin(as.store).fetchJoin()
                .leftJoin(as.store.member).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(as.id.desc())
                .fetch();

        // --- 카운트 쿼리 ---
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(as.count())
                .from(as)
                .where(builder);

        // --- 결과 반환 ---
        Page<As> asPage = PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);

        if (asPage.isEmpty()) {
            throw new AsException(AS_NOT_FOUNT);
        }

        return asPage;
    }
}
