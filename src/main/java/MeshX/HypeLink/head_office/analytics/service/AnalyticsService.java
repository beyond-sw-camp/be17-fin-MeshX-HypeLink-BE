package MeshX.HypeLink.head_office.analytics.service;

import MeshX.HypeLink.head_office.analytics.dto.*;
import MeshX.HypeLink.head_office.analytics.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    /**
     * 매출 현황 조회
     * @param period "daily", "weekly", "monthly"
     * @param startDate 시작일 (optional)
     * @param endDate 종료일 (optional)
     * @param storeIds 매장 ID 리스트 (optional, 현재 미사용)
     * @param categories 카테고리 리스트 (optional, 현재 미사용)
     */
    public SalesOverviewDTO getSalesOverview(String period, String startDate, String endDate,
                                            List<Long> storeIds, List<String> categories) {
        LocalDateTime[] dateRange = getDateRange(period);
        // TODO: 커스텀 날짜 범위 및 필터 적용 로직 추가
        return analyticsRepository.getSalesOverview(dateRange[0], dateRange[1]);
    }

    /**
     * 주문 현황 조회
     * @param period "daily", "weekly", "monthly"
     */
    public OrderOverviewDTO getOrderOverview(String period) {
        LocalDateTime[] dateRange = getDateRange(period);
        return analyticsRepository.getOrderOverview(dateRange[0], dateRange[1]);
    }

    /**
     * 매장별 매출 TOP N
     * @param period "daily", "weekly", "monthly"
     * @param limit 조회할 개수
     * @param startDate 시작일 (optional)
     * @param endDate 종료일 (optional)
     * @param categories 카테고리 리스트 (optional, 현재 미사용)
     */
    public List<StoreSalesDTO> getTopStoresBySales(String period, int limit, String startDate,
                                                   String endDate, List<String> categories) {
        LocalDateTime[] dateRange = getDateRange(period);
        // TODO: 커스텀 날짜 범위 및 필터 적용 로직 추가
        return analyticsRepository.getTopStoresBySales(dateRange[0], dateRange[1], limit);
    }

    /**
     * 상품별 매출 TOP N
     * @param period "daily", "weekly", "monthly"
     * @param limit 조회할 개수
     * @param startDate 시작일 (optional)
     * @param endDate 종료일 (optional)
     * @param storeIds 매장 ID 리스트 (optional, 현재 미사용)
     * @param categories 카테고리 리스트 (optional, 현재 미사용)
     */
    public List<ProductSalesDTO> getTopProductsBySales(String period, int limit, String startDate,
                                                       String endDate, List<Long> storeIds, List<String> categories) {
        LocalDateTime[] dateRange = getDateRange(period);
        // TODO: 커스텀 날짜 범위 및 필터 적용 로직 추가
        return analyticsRepository.getTopProductsBySales(dateRange[0], dateRange[1], limit);
    }

    /**
     * 카테고리별 성과
     * @param period "daily", "weekly", "monthly"
     */
    public List<CategoryPerformanceDTO> getCategoryPerformance(String period) {
        LocalDateTime[] dateRange = getDateRange(period);
        return analyticsRepository.getCategoryPerformance(dateRange[0], dateRange[1]);
    }

    /**
     * 재고 부족 품목 조회
     * @param threshold 재고 기준값 (기본 20)
     */
    public List<LowStockItemDTO> getLowStockItems(int threshold) {
        return analyticsRepository.getLowStockItems(threshold);
    }

    /**
     * 매출 추이 조회
     * @param period "daily", "weekly", "monthly"
     */
    public List<SalesTrendDTO> getSalesTrend(String period) {
        LocalDateTime[] dateRange = getDateRange(period);
        return analyticsRepository.getSalesTrend(dateRange[0], dateRange[1]);
    }

    /**
     * 주문 추이 조회
     * @param period "daily", "weekly", "monthly"
     */
    public List<OrderTrendDTO> getOrderTrend(String period) {
        LocalDateTime[] dateRange = getDateRange(period);
        return analyticsRepository.getOrderTrend(dateRange[0], dateRange[1]);
    }

    /**
     * 시간대별 매출 히트맵
     * @param period "daily", "weekly", "monthly"
     */
    public List<SalesHeatmapDTO> getSalesHeatmap(String period) {
        LocalDateTime[] dateRange = getDateRange(period);
        return analyticsRepository.getSalesHeatmap(dateRange[0], dateRange[1]);
    }

    /**
     * 매장별 매출 (페이지네이션)
     * @param period "daily", "weekly", "monthly"
     * @param pageable 페이지 정보
     */
    public Page<StoreSalesDTO> getStoresSalesPaged(String period, Pageable pageable) {
        LocalDateTime[] dateRange = getDateRange(period);
        return analyticsRepository.getStoresSalesPaged(dateRange[0], dateRange[1], pageable);
    }

    /**
     * 상품별 매출 (페이지네이션)
     * @param period "daily", "weekly", "monthly"
     * @param pageable 페이지 정보
     */
    public Page<ProductSalesDTO> getProductsSalesPaged(String period, Pageable pageable) {
        LocalDateTime[] dateRange = getDateRange(period);
        return analyticsRepository.getProductsSalesPaged(dateRange[0], dateRange[1], pageable);
    }

    /**
     * 고객 RFM 분석
     * @param limit 조회할 고객 수
     */
    public List<CustomerRFMDTO> getCustomerRFMAnalysis(int limit) {
        return analyticsRepository.getCustomerRFMAnalysis(limit);
    }

    /**
     * 상품 ABC 분석
     * @param period "daily", "weekly", "monthly"
     */
    public List<ProductABCDTO> getProductABCAnalysis(String period) {
        LocalDateTime[] dateRange = getDateRange(period);
        return analyticsRepository.getProductABCAnalysis(dateRange[0], dateRange[1]);
    }

    /**
     * 기간 계산 헬퍼 메서드
     * @param period "daily", "weekly", "monthly"
     * @return [startDate, endDate]
     */
    private LocalDateTime[] getDateRange(String period) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate;

        switch (period.toLowerCase()) {
            case "daily":
                startDate = endDate.minusDays(1);
                break;
            case "weekly":
                startDate = endDate.minusWeeks(1);
                break;
            case "monthly":
                startDate = endDate.minusMonths(1);
                break;
            default:
                // 기본값은 주간
                startDate = endDate.minusWeeks(1);
        }

        return new LocalDateTime[]{startDate, endDate};
    }
}
