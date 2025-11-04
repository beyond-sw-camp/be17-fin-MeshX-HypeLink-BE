package MeshX.HypeLink.head_office.customer.repository;

import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static MeshX.HypeLink.head_office.customer.model.entity.QCustomer.customer;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Customer> searchCustomers(String keyword, String ageGroup) {
        BooleanBuilder builder = new BooleanBuilder();

        // 키워드 검색 (고객명 또는 전화번호)
        if (keyword != null && !keyword.trim().isEmpty()) {
            builder.and(
                customer.name.containsIgnoreCase(keyword)
                    .or(customer.phone.contains(keyword))
            );
        }

        // 연령대 필터
        if (ageGroup != null && !ageGroup.equals("all")) {
            addAgeGroupCondition(builder, ageGroup);
        }

        return queryFactory
            .selectFrom(customer)
            .where(builder)
            .orderBy(customer.name.asc())
            .fetch();
    }

    @Override
    public Page<Customer> searchCustomersPaged(String keyword, String ageGroup, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // 키워드 검색 (고객명 또는 전화번호)
        if (keyword != null && !keyword.trim().isEmpty()) {
            builder.and(
                customer.name.containsIgnoreCase(keyword)
                    .or(customer.phone.contains(keyword))
            );
        }

        // 연령대 필터
        if (ageGroup != null && !ageGroup.equals("all")) {
            addAgeGroupCondition(builder, ageGroup);
        }

        List<Customer> content = queryFactory
            .selectFrom(customer)
            .where(builder)
            .orderBy(customer.name.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = queryFactory
            .selectFrom(customer)
            .where(builder)
            .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    private void addAgeGroupCondition(BooleanBuilder builder, String ageGroup) {
        int currentYear = LocalDate.now().getYear();

        switch (ageGroup) {
            case "10대":
                builder.and(customer.birthDate.year().between(currentYear - 19, currentYear - 10));
                break;
            case "20대":
                builder.and(customer.birthDate.year().between(currentYear - 29, currentYear - 20));
                break;
            case "30대":
                builder.and(customer.birthDate.year().between(currentYear - 39, currentYear - 30));
                break;
            case "40대":
                builder.and(customer.birthDate.year().between(currentYear - 49, currentYear - 40));
                break;
            case "50대 이상":
                builder.and(customer.birthDate.year().loe(currentYear - 50));
                break;
        }
    }
}
