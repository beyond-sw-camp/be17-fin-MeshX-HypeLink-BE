package MeshX.HypeLink.head_office.analytics.repository;

import MeshX.HypeLink.head_office.analytics.dto.*;
import MeshX.HypeLink.head_office.customer.model.entity.PaymentStatus;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static MeshX.HypeLink.auth.model.entity.QMember.member;
import static MeshX.HypeLink.auth.model.entity.QStore.store;
import static MeshX.HypeLink.direct_store.item.model.entity.QStoreItem.storeItem;
import static MeshX.HypeLink.direct_store.item.model.entity.QStoreItemDetail.storeItemDetail;
import static MeshX.HypeLink.head_office.customer.model.entity.QCustomer.customer;
import static MeshX.HypeLink.head_office.customer.model.entity.QCustomerReceipt.customerReceipt;
import static MeshX.HypeLink.head_office.customer.model.entity.QOrderItem.orderItem;

@Repository
@RequiredArgsConstructor
public class AnalyticsRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 매출 현황 조회 (고객 구매 기반)
     */
    public SalesOverviewDTO getSalesOverview(LocalDateTime startDate, LocalDateTime endDate) {
        NumberExpression<Integer> totalRevenue = customerReceipt.finalAmount.sum();
        NumberExpression<Long> totalTransactions = customerReceipt.count();
        NumberExpression<Double> avgOrderValue = customerReceipt.finalAmount.avg();

        SalesOverviewDTO result = queryFactory
            .select(Projections.constructor(SalesOverviewDTO.class,
                totalRevenue.longValue(),
                totalTransactions,
                avgOrderValue.longValue(),
                Expressions.constant(0.0) // growthRate는 나중에 계산
            ))
            .from(customerReceipt)
            .where(
                customerReceipt.paidAt.between(startDate, endDate),
                customerReceipt.status.eq(PaymentStatus.PAID)
            )
            .fetchOne();

        if (result == null || result.getTotalTransactions() == 0) {
            result = new SalesOverviewDTO(0L, 0L, 0L, 0.0);
        }

        long periodDays = java.time.Duration.between(startDate, endDate).toDays();
        if (periodDays == 0) periodDays = 1;
        LocalDateTime prevStartDate = startDate.minusDays(periodDays);

        Integer prevRevenue = queryFactory
            .select(customerReceipt.finalAmount.sum())
            .from(customerReceipt)
            .where(
                customerReceipt.paidAt.between(prevStartDate, startDate),
                customerReceipt.status.eq(PaymentStatus.PAID)
            )
            .fetchOne();

        if (prevRevenue != null && prevRevenue > 0) {
            double growthRate = ((result.getTotalRevenue() - prevRevenue) * 100.0) / prevRevenue;
            result.setGrowthRate(Math.round(growthRate * 100.0) / 100.0);
        }

        return result;
    }

    /**
     * 주문 현황 조회 (고객 구매 기반)
     */
    public OrderOverviewDTO getOrderOverview(LocalDateTime startDate, LocalDateTime endDate) {
        Long totalOrders = queryFactory.select(customerReceipt.count()).from(customerReceipt).where(customerReceipt.createdAt.between(startDate, endDate)).fetchOne();
        Long pendingOrders = queryFactory.select(customerReceipt.count()).from(customerReceipt).where(customerReceipt.createdAt.between(startDate, endDate), customerReceipt.status.eq(PaymentStatus.READY)).fetchOne();
        Long completedOrders = queryFactory.select(customerReceipt.count()).from(customerReceipt).where(customerReceipt.createdAt.between(startDate, endDate), customerReceipt.status.eq(PaymentStatus.PAID)).fetchOne();
        Long cancelledOrders = queryFactory.select(customerReceipt.count()).from(customerReceipt).where(customerReceipt.createdAt.between(startDate, endDate), customerReceipt.status.eq(PaymentStatus.CANCELLED)).fetchOne();

        totalOrders = totalOrders != null ? totalOrders : 0L;
        pendingOrders = pendingOrders != null ? pendingOrders : 0L;
        completedOrders = completedOrders != null ? completedOrders : 0L;
        cancelledOrders = cancelledOrders != null ? cancelledOrders : 0L;

        double completionRate = totalOrders > 0 ? (completedOrders * 100.0) / (totalOrders - cancelledOrders) : 0.0;

        return new OrderOverviewDTO(totalOrders, pendingOrders, completedOrders, cancelledOrders, Math.round(completionRate * 100.0) / 100.0);
    }

    /**
     * 매장별 매출 TOP N (고객 구매 기반)
     */
    public List<StoreSalesDTO> getTopStoresBySales(LocalDateTime startDate, LocalDateTime endDate, int limit) {
        return queryFactory
            .select(Projections.constructor(StoreSalesDTO.class,
                store.id,
                store.storeNumber,
                member.name,
                customerReceipt.finalAmount.sum().longValue(),
                customerReceipt.count(),
                Expressions.constant(0.0)
            ))
            .from(customerReceipt)
            .join(customerReceipt.store, store)
            .join(store.member, member)
            .where(
                customerReceipt.paidAt.between(startDate, endDate),
                customerReceipt.status.eq(PaymentStatus.PAID)
            )
            .groupBy(store.id, store.storeNumber, member.name)
            .orderBy(customerReceipt.finalAmount.sum().desc())
            .limit(limit)
            .fetch();
    }

    /**
     * 상품별 매출 TOP N (고객 구매 기반)
     */
    public List<ProductSalesDTO> getTopProductsBySales(LocalDateTime startDate, LocalDateTime endDate, int limit) {
        return queryFactory
            .select(Projections.constructor(ProductSalesDTO.class,
                storeItem.id, // DTO expects Integer itemId
                storeItem.itemCode,
                storeItem.koName,
                storeItem.enName,
                storeItem.category.category,
                orderItem.totalPrice.sum().longValue(),
                orderItem.quantity.sum().longValue(),
                orderItem.unitPrice.avg().longValue(),
                Expressions.constant(0.0)
            ))
            .from(orderItem)
            .join(orderItem.customerReceipt, customerReceipt)
            .join(orderItem.storeItemDetail, storeItemDetail)
            .join(storeItemDetail.item, storeItem)
            .where(
                customerReceipt.paidAt.between(startDate, endDate),
                customerReceipt.status.eq(PaymentStatus.PAID)
            )
            .groupBy(storeItem.id, storeItem.itemCode, storeItem.koName, storeItem.enName, storeItem.category.category)
            .orderBy(orderItem.totalPrice.sum().desc())
            .limit(limit)
            .fetch();
    }

    /**
     * 카테고리별 성과 (고객 구매 기반)
     */
    public List<CategoryPerformanceDTO> getCategoryPerformance(LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory
            .select(Projections.constructor(CategoryPerformanceDTO.class,
                storeItem.category.category,
                orderItem.totalPrice.sum().longValue(),
                orderItem.quantity.sum().longValue(),
                orderItem.unitPrice.avg().longValue(),
                Expressions.constant(0.0)
            ))
            .from(orderItem)
            .join(orderItem.customerReceipt, customerReceipt)
            .join(orderItem.storeItemDetail, storeItemDetail)
            .join(storeItemDetail.item, storeItem)
            .where(
                customerReceipt.paidAt.between(startDate, endDate),
                customerReceipt.status.eq(PaymentStatus.PAID)
            )
            .groupBy(storeItem.category.category)
            .orderBy(orderItem.totalPrice.sum().desc())
            .fetch();
    }

    /**
     * 재고 부족 품목 조회 (StoreItemDetail 기반)
     */
    public List<LowStockItemDTO> getLowStockItems(int threshold) {
        return queryFactory
            .select(Projections.constructor(LowStockItemDTO.class,
                storeItemDetail.id,
                storeItem.itemCode,
                storeItem.koName,
                storeItem.category.category,
                storeItemDetail.color,
                storeItemDetail.size,
                storeItemDetail.stock,
                Expressions.cases()
                    .when(storeItemDetail.stock.eq(0)).then("critical")
                    .when(storeItemDetail.stock.loe(5)).then("high")
                    .when(storeItemDetail.stock.loe(10)).then("medium")
                    .otherwise("low")
            ))
            .from(storeItemDetail)
            .join(storeItemDetail.item, storeItem)
            .where(storeItemDetail.stock.lt(threshold))
            .orderBy(storeItemDetail.stock.asc())
            .fetch();
    }

    /**
     * 매출 추이 조회 (고객 구매 기반)
     */
    public List<SalesTrendDTO> getSalesTrend(LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory
            .select(Projections.constructor(SalesTrendDTO.class,
                Expressions.stringTemplate("DATE({0})", customerReceipt.paidAt),
                customerReceipt.finalAmount.sum().longValue(),
                customerReceipt.count(),
                customerReceipt.finalAmount.avg().longValue()
            ))
            .from(customerReceipt)
            .where(
                customerReceipt.paidAt.between(startDate, endDate),
                customerReceipt.status.eq(PaymentStatus.PAID)
            )
            .groupBy(Expressions.stringTemplate("DATE({0})", customerReceipt.paidAt))
            .orderBy(Expressions.stringTemplate("DATE({0})", customerReceipt.paidAt).asc())
            .fetch();
    }

    /**
     * 주문 추이 조회 (고객 구매 기반)
     */
    public List<OrderTrendDTO> getOrderTrend(LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory
            .select(Projections.constructor(OrderTrendDTO.class,
                Expressions.stringTemplate("DATE({0})", customerReceipt.createdAt),
                customerReceipt.count(),
                Expressions.cases().when(customerReceipt.status.eq(PaymentStatus.READY)).then(1L).otherwise(0L).sum(),
                Expressions.cases().when(customerReceipt.status.eq(PaymentStatus.PAID)).then(1L).otherwise(0L).sum(),
                Expressions.cases().when(customerReceipt.status.eq(PaymentStatus.CANCELLED)).then(1L).otherwise(0L).sum()
            ))
            .from(customerReceipt)
            .where(customerReceipt.createdAt.between(startDate, endDate))
            .groupBy(Expressions.stringTemplate("DATE({0})", customerReceipt.createdAt))
            .orderBy(Expressions.stringTemplate("DATE({0})", customerReceipt.createdAt).asc())
            .fetch();
    }

    /**
     * 시간대별 매출 히트맵 (고객 구매 기반)
     */
    public List<SalesHeatmapDTO> getSalesHeatmap(LocalDateTime startDate, LocalDateTime endDate) {
        NumberExpression<Integer> hourOfDay = Expressions.numberTemplate(Integer.class, "HOUR({0})", customerReceipt.paidAt);
        NumberExpression<Integer> dayOfWeek = Expressions.numberTemplate(Integer.class, "DAYOFWEEK({0})", customerReceipt.paidAt);

        return queryFactory
            .select(Projections.constructor(SalesHeatmapDTO.class,
                dayOfWeek,
                hourOfDay,
                customerReceipt.finalAmount.sum().longValue(),
                customerReceipt.count()
            ))
            .from(customerReceipt)
            .where(
                customerReceipt.paidAt.between(startDate, endDate),
                customerReceipt.status.eq(PaymentStatus.PAID)
            )
            .groupBy(dayOfWeek, hourOfDay)
            .orderBy(dayOfWeek.asc(), hourOfDay.asc())
            .fetch();
    }

    /**
     * 매장별 매출 (페이지네이션, 고객 구매 기반)
     */
    public Page<StoreSalesDTO> getStoresSalesPaged(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        List<StoreSalesDTO> content = queryFactory
            .select(Projections.constructor(StoreSalesDTO.class,
                store.id,
                store.storeNumber,
                member.name,
                customerReceipt.finalAmount.sum().longValue(),
                customerReceipt.count(),
                Expressions.constant(0.0)
            ))
            .from(customerReceipt)
            .join(customerReceipt.store, store)
            .join(store.member, member)
            .where(
                customerReceipt.paidAt.between(startDate, endDate),
                customerReceipt.status.eq(PaymentStatus.PAID)
            )
            .groupBy(store.id, store.storeNumber, member.name)
            .orderBy(customerReceipt.finalAmount.sum().desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(store.countDistinct())
            .from(customerReceipt)
            .join(customerReceipt.store, store)
            .where(
                customerReceipt.paidAt.between(startDate, endDate),
                customerReceipt.status.eq(PaymentStatus.PAID)
            )
            .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    /**
     * 상품별 매출 (페이지네이션, 고객 구매 기반)
     */
    public Page<ProductSalesDTO> getProductsSalesPaged(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        List<ProductSalesDTO> content = queryFactory
            .select(Projections.constructor(ProductSalesDTO.class,
                storeItem.id,
                storeItem.itemCode,
                storeItem.koName,
                storeItem.enName,
                storeItem.category.category,
                orderItem.totalPrice.sum().longValue(),
                orderItem.quantity.sum().longValue(),
                orderItem.unitPrice.avg().longValue(),
                Expressions.constant(0.0)
            ))
            .from(orderItem)
            .join(orderItem.customerReceipt, customerReceipt)
            .join(orderItem.storeItemDetail, storeItemDetail)
            .join(storeItemDetail.item, storeItem)
            .where(
                customerReceipt.paidAt.between(startDate, endDate),
                customerReceipt.status.eq(PaymentStatus.PAID)
            )
            .groupBy(storeItem.id, storeItem.itemCode, storeItem.koName, storeItem.enName, storeItem.category.category)
            .orderBy(orderItem.totalPrice.sum().desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(storeItem.countDistinct())
            .from(orderItem)
            .join(orderItem.customerReceipt, customerReceipt)
            .where(
                customerReceipt.paidAt.between(startDate, endDate),
                customerReceipt.status.eq(PaymentStatus.PAID)
            )
            .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    /**
     * 고객 RFM 분석 (고객 구매 기반)
     */
    public List<CustomerRFMDTO> getCustomerRFMAnalysis(int limit) {
        LocalDateTime now = LocalDateTime.now();

        List<Tuple> customerStats = queryFactory
            .select(
                customer.id,
                customer.name,
                customer.phone,
                customerReceipt.paidAt.max(),
                customerReceipt.count(),
                customerReceipt.finalAmount.sum()
            )
            .from(customerReceipt)
            .join(customerReceipt.customer, customer)
            .where(customerReceipt.status.eq(PaymentStatus.PAID))
            .groupBy(customer.id, customer.name, customer.phone)
            .having(customerReceipt.count().gt(0))
            .fetch();

        List<Long> recencyDaysList = new ArrayList<>();
        List<Long> frequencyList = new ArrayList<>();
        List<Long> monetaryList = new ArrayList<>();

        for (Tuple stat : customerStats) {
            LocalDateTime lastOrderDate = stat.get(customerReceipt.paidAt.max());
            long daysSince = ChronoUnit.DAYS.between(Objects.requireNonNull(lastOrderDate), now);
            long frequency = stat.get(customerReceipt.count());
            long monetary = Objects.requireNonNull(stat.get(customerReceipt.finalAmount.sum())).longValue();

            recencyDaysList.add(daysSince);
            frequencyList.add(frequency);
            monetaryList.add(monetary);
        }

        int[] recencyQuintiles = calculateQuintiles(recencyDaysList);
        int[] frequencyQuintiles = calculateQuintiles(frequencyList);
        int[] monetaryQuintiles = calculateQuintiles(monetaryList);

        List<CustomerRFMDTO> results = new ArrayList<>();
        for (Tuple stat : customerStats) {
            Long customerId = Objects.requireNonNull(stat.get(customer.id)).longValue();
            String customerName = stat.get(customer.name);
            String customerPhone = stat.get(customer.phone);
            LocalDateTime lastOrderDate = stat.get(customerReceipt.paidAt.max());
            long frequency = stat.get(customerReceipt.count());
            long monetary = Objects.requireNonNull(stat.get(customerReceipt.finalAmount.sum())).longValue();
            long daysSince = ChronoUnit.DAYS.between(Objects.requireNonNull(lastOrderDate), now);

            int recencyScore = 6 - getQuintileScore(daysSince, recencyQuintiles);
            int frequencyScore = getQuintileScore(frequency, frequencyQuintiles);
            int monetaryScore = getQuintileScore(monetary, monetaryQuintiles);
            double rfmScore = (recencyScore + frequencyScore + monetaryScore) / 3.0;

            String segment = classifyRFMSegment(recencyScore, frequencyScore, monetaryScore);
            String segmentDescription = getSegmentDescription(segment);

            results.add(new CustomerRFMDTO(
                customerId, customerName, customerPhone, // email is not available in Customer entity
                recencyScore, frequencyScore, monetaryScore,
                Math.round(rfmScore * 100.0) / 100.0,
                (int) daysSince, frequency, monetary,
                segment, segmentDescription
            ));
        }

        results.sort((a, b) -> Double.compare(b.getRfmScore(), a.getRfmScore()));
        return results.stream().limit(limit).toList();
    }

    /**
     * 상품 ABC 분석 (고객 구매 기반)
     */
    public List<ProductABCDTO> getProductABCAnalysis(LocalDateTime startDate, LocalDateTime endDate) {
        List<Tuple> productSales = queryFactory
            .select(
                storeItem.id,
                storeItem.koName,
                storeItem.category.category,
                orderItem.totalPrice.sum(),
                orderItem.quantity.sum()
            )
            .from(orderItem)
            .join(orderItem.customerReceipt, customerReceipt)
            .join(orderItem.storeItemDetail, storeItemDetail)
            .join(storeItemDetail.item, storeItem)
            .where(
                customerReceipt.paidAt.between(startDate, endDate),
                customerReceipt.status.eq(PaymentStatus.PAID)
            )
            .groupBy(storeItem.id, storeItem.koName, storeItem.category.category)
            .orderBy(orderItem.totalPrice.sum().desc())
            .fetch();

        long totalRevenue = productSales.stream()
            .mapToLong(stat -> Objects.requireNonNull(stat.get(orderItem.totalPrice.sum())).longValue())
            .sum();

        if (totalRevenue == 0) return new ArrayList<>();

        List<ProductABCDTO> results = new ArrayList<>();
        double cumulativePercentage = 0.0;
        int ranking = 1;

        for (Tuple stat : productSales) {
            Long productId = Objects.requireNonNull(stat.get(storeItem.id)).longValue();
            String productName = stat.get(storeItem.koName);
            String categoryName = stat.get(storeItem.category.category);
            long revenue = Objects.requireNonNull(stat.get(orderItem.totalPrice.sum())).longValue();
            long quantity = Objects.requireNonNull(stat.get(orderItem.quantity.sum())).longValue();

            double revenuePercentage = (revenue * 100.0) / totalRevenue;
            cumulativePercentage += revenuePercentage;

            String abcGrade = (cumulativePercentage <= 80) ? "A" : (cumulativePercentage <= 95) ? "B" : "C";
            String recommendation = getAbcRecommendation(abcGrade);

            results.add(new ProductABCDTO(
                productId, productName, categoryName, revenue, quantity,
                Math.round(revenuePercentage * 100.0) / 100.0,
                Math.round(cumulativePercentage * 100.0) / 100.0,
                abcGrade, ranking++, recommendation
            ));
        }
        return results;
    }

    // ===== Helper Methods =====

    private int[] calculateQuintiles(List<Long> values) {
        if (values.isEmpty()) return new int[]{0, 0, 0, 0, 0};
        List<Long> sorted = new ArrayList<>(values);
        sorted.sort(Long::compareTo);
        int size = sorted.size();
        return new int[]{
            sorted.get(Math.max(0, (int) (size * 0.2) - 1)).intValue(),
            sorted.get(Math.max(0, (int) (size * 0.4) - 1)).intValue(),
            sorted.get(Math.max(0, (int) (size * 0.6) - 1)).intValue(),
            sorted.get(Math.max(0, (int) (size * 0.8) - 1)).intValue(),
            sorted.get(size - 1).intValue()
        };
    }

    private int getQuintileScore(long value, int[] quintiles) {
        if (value <= quintiles[0]) return 1;
        if (value <= quintiles[1]) return 2;
        if (value <= quintiles[2]) return 3;
        if (value <= quintiles[3]) return 4;
        return 5;
    }

    private String classifyRFMSegment(int r, int f, int m) {
        if (r >= 4 && f >= 4 && m >= 4) return "VIP";
        if (r >= 4 && f >= 3) return "충성고객";
        if (r >= 3 && m >= 3) return "잠재고객";
        if (r <= 2 && f >= 3) return "이탈위험";
        return "휴면고객";
    }

    private String getSegmentDescription(String segment) {
        return switch (segment) {
            case "VIP" -> "최근 구매, 높은 빈도, 높은 금액 - 최우선 관리 대상";
            case "충성고객" -> "최근 구매, 높은 빈도 - 지속적인 관계 유지";
            case "잠재고객" -> "최근 구매, 높은 금액 - 빈도 증가 유도";
            case "이탈위험" -> "구매 기록 있으나 최근 미구매 - 재활성화 필요";
            case "휴면고객" -> "장기 미구매 - 특별 프로모션 필요";
            default -> "분류 미정";
        };
    }

    private String getAbcRecommendation(String grade) {
        return switch (grade) {
            case "A" -> "핵심 상품 - 재고 최우선 관리 및 프로모션 집중";
            case "B" -> "중요 상품 - 안정적 재고 유지 및 주기적 프로모션";
            case "C" -> "일반 상품 - 최소 재고 유지, 재고 회전율 개선 필요";
            default -> "등급 외 상품";
        };
    }

    /**
     * 일별 매출 데이터 조회 (Sales Management 페이지용)
     */
    public List<MeshX.HypeLink.head_office.analytics.dto.DailySalesDTO> getDailySales(
            LocalDateTime startDate, LocalDateTime endDate, Integer storeId) {

        var query = queryFactory
            .select(Projections.constructor(MeshX.HypeLink.head_office.analytics.dto.DailySalesDTO.class,
                customerReceipt.id,
                store.id,
                member.name,
                store.storeNumber,
                Expressions.stringTemplate("DATE({0})", customerReceipt.paidAt),
                customerReceipt.finalAmount.sum().longValue()
            ))
            .from(customerReceipt)
            .join(customerReceipt.store, store)
            .join(store.member, member)
            .where(
                customerReceipt.paidAt.between(startDate, endDate),
                customerReceipt.status.eq(PaymentStatus.PAID)
            )
            .groupBy(
                Expressions.stringTemplate("DATE({0})", customerReceipt.paidAt),
                store.id,
                member.name,
                store.storeNumber
            )
            .orderBy(Expressions.stringTemplate("DATE({0})", customerReceipt.paidAt).desc());

        // storeId 필터링 (선택적)
        if (storeId != null) {
            query.where(store.id.eq(storeId));
        }

        return query.fetch();
    }

    /**
     * 최근 7일간 매출 차트 데이터 (Sales Management 페이지용)
     */
    public List<MeshX.HypeLink.head_office.analytics.dto.SalesChartDataDTO> getSalesChartData(Integer storeId) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);

        // 매장별로 그룹화된 일별 매출 조회
        List<Tuple> results = queryFactory
            .select(
                store.id,
                member.name,
                Expressions.stringTemplate("DAYNAME({0})", customerReceipt.paidAt),
                customerReceipt.finalAmount.sum()
            )
            .from(customerReceipt)
            .join(customerReceipt.store, store)
            .join(store.member, member)
            .where(
                customerReceipt.paidAt.between(startDate, endDate),
                customerReceipt.status.eq(PaymentStatus.PAID),
                storeId != null ? store.id.eq(storeId) : null
            )
            .groupBy(store.id, member.name, Expressions.stringTemplate("DAYOFWEEK({0})", customerReceipt.paidAt))
            .orderBy(Expressions.stringTemplate("DAYOFWEEK({0})", customerReceipt.paidAt).asc())
            .fetch();

        // 결과를 SalesChartDataDTO로 변환 (간단한 구현)
        // 실제로는 더 복잡한 로직이 필요할 수 있음
        return List.of();  // TODO: 구현 필요
    }

    /**
     * 고객 분석 데이터 조회 (Customer Analytics 페이지용)
     */
    public List<MeshX.HypeLink.head_office.analytics.dto.CustomerAnalyticsDTO> getCustomerAnalytics() {
        // 기본 고객 정보 + 총 구매액 + 최근 구매일
        List<Tuple> customerData = queryFactory
            .select(
                customer.id,
                customer.name,
                customer.phone,
                customer.birthDate,
                customerReceipt.finalAmount.sum(),
                customerReceipt.paidAt.max()
            )
            .from(customer)
            .leftJoin(customerReceipt).on(customerReceipt.customer.eq(customer))
            .where(customerReceipt.status.eq(PaymentStatus.PAID).or(customerReceipt.id.isNull()))
            .groupBy(customer.id, customer.name, customer.phone, customer.birthDate)
            .fetch();

        // TODO: purchaseHistory 추가 구현
        return List.of();  // 간단한 구현
    }

    /**
     * 연령대별 고객 분포
     */
    public List<MeshX.HypeLink.head_office.analytics.dto.AgeDistributionDTO> getAgeDistribution() {
        // YEAR 함수를 사용하여 연령 계산
        List<Tuple> results = queryFactory
            .select(
                Expressions.stringTemplate(
                    "CASE " +
                    "WHEN YEAR(CURDATE()) - YEAR({0}) < 20 THEN '10대' " +
                    "WHEN YEAR(CURDATE()) - YEAR({0}) < 30 THEN '20대' " +
                    "WHEN YEAR(CURDATE()) - YEAR({0}) < 40 THEN '30대' " +
                    "WHEN YEAR(CURDATE()) - YEAR({0}) < 50 THEN '40대' " +
                    "ELSE '50대 이상' END",
                    customer.birthDate
                ),
                customer.count()
            )
            .from(customer)
            .where(customer.birthDate.isNotNull())
            .groupBy(Expressions.stringTemplate(
                "CASE " +
                "WHEN YEAR(CURDATE()) - YEAR({0}) < 20 THEN '10대' " +
                "WHEN YEAR(CURDATE()) - YEAR({0}) < 30 THEN '20대' " +
                "WHEN YEAR(CURDATE()) - YEAR({0}) < 40 THEN '30대' " +
                "WHEN YEAR(CURDATE()) - YEAR({0}) < 50 THEN '40대' " +
                "ELSE '50대 이상' END",
                customer.birthDate
            ))
            .fetch();

        List<MeshX.HypeLink.head_office.analytics.dto.AgeDistributionDTO> distribution = new ArrayList<>();
        for (Tuple tuple : results) {
            String ageGroup = tuple.get(0, String.class);
            Long count = tuple.get(1, Long.class);
            distribution.add(new MeshX.HypeLink.head_office.analytics.dto.AgeDistributionDTO(ageGroup, count));
        }

        return distribution;
    }

    /**
     * 카테고리별 고객 매출 (Customer Analytics 페이지용)
     */
    public List<MeshX.HypeLink.head_office.analytics.dto.CategoryCustomerSalesDTO> getCategoryCustomerSales() {
        return queryFactory
            .select(Projections.constructor(MeshX.HypeLink.head_office.analytics.dto.CategoryCustomerSalesDTO.class,
                storeItem.category.category,
                orderItem.totalPrice.sum().longValue()
            ))
            .from(orderItem)
            .join(orderItem.customerReceipt, customerReceipt)
            .join(orderItem.storeItemDetail, storeItemDetail)
            .join(storeItemDetail.item, storeItem)
            .where(customerReceipt.status.eq(PaymentStatus.PAID))
            .groupBy(storeItem.category.category)
            .orderBy(orderItem.totalPrice.sum().desc())
            .fetch();
    }
}