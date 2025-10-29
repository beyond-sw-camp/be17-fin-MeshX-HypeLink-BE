package MeshX.HypeLink.head_office.analytics.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.analytics.dto.*;
import MeshX.HypeLink.head_office.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    /**
     * 매출 현황 조회
     * GET /api/analytics/sales/overview?period=weekly&startDate=2024-01-01&endDate=2024-12-31&storeIds=1,2,3&categories=상의,하의
     */
    @GetMapping("/sales/overview")
    public ResponseEntity<BaseResponse<SalesOverviewDTO>> getSalesOverview(
            @RequestParam(defaultValue = "weekly") String period,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<Long> storeIds,
            @RequestParam(required = false) List<String> categories) {
        SalesOverviewDTO data = analyticsService.getSalesOverview(period, startDate, endDate, storeIds, categories);
        return ResponseEntity.ok(BaseResponse.of(data, "매출 현황 조회 성공"));
    }

    /**
     * 주문 현황 조회
     * GET /api/analytics/orders/overview?period=weekly
     */
    @GetMapping("/orders/overview")
    public ResponseEntity<BaseResponse<OrderOverviewDTO>> getOrderOverview(
            @RequestParam(defaultValue = "weekly") String period) {
        OrderOverviewDTO data = analyticsService.getOrderOverview(period);
        return ResponseEntity.ok(BaseResponse.of(data, "주문 현황 조회 성공"));
    }

    /**
     * 매장별 매출 TOP N
     * GET /api/analytics/stores/top?period=weekly&limit=5&startDate=2024-01-01&endDate=2024-12-31&categories=상의,하의
     */
    @GetMapping("/stores/top")
    public ResponseEntity<BaseResponse<List<StoreSalesDTO>>> getTopStores(
            @RequestParam(defaultValue = "weekly") String period,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<String> categories) {
        List<StoreSalesDTO> data = analyticsService.getTopStoresBySales(period, limit, startDate, endDate, categories);
        return ResponseEntity.ok(BaseResponse.of(data, "매장별 매출 조회 성공"));
    }

    /**
     * 상품별 매출 TOP N
     * GET /api/analytics/products/top?period=weekly&limit=20&startDate=2024-01-01&endDate=2024-12-31&storeIds=1,2,3&categories=상의,하의
     */
    @GetMapping("/products/top")
    public ResponseEntity<BaseResponse<List<ProductSalesDTO>>> getTopProducts(
            @RequestParam(defaultValue = "weekly") String period,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<Long> storeIds,
            @RequestParam(required = false) List<String> categories) {
        List<ProductSalesDTO> data = analyticsService.getTopProductsBySales(period, limit, startDate, endDate, storeIds, categories);
        return ResponseEntity.ok(BaseResponse.of(data, "상품별 매출 조회 성공"));
    }

    /**
     * 카테고리별 성과
     * GET /api/analytics/categories/performance?period=weekly
     */
    @GetMapping("/categories/performance")
    public ResponseEntity<BaseResponse<List<CategoryPerformanceDTO>>> getCategoryPerformance(
            @RequestParam(defaultValue = "weekly") String period) {
        List<CategoryPerformanceDTO> data = analyticsService.getCategoryPerformance(period);
        return ResponseEntity.ok(BaseResponse.of(data, "카테고리별 성과 조회 성공"));
    }

    /**
     * 재고 부족 품목 조회 (페이지네이션)
     * GET /api/analytics/inventory/low-stock?threshold=20&page=0&size=10
     */
    @GetMapping("/inventory/low-stock")
    public ResponseEntity<BaseResponse<Page<LowStockItemDTO>>> getLowStockItems(
            @RequestParam(defaultValue = "20") int threshold,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Pageable pageable) {
        Page<LowStockItemDTO> data = analyticsService.getLowStockItems(threshold, pageable);
        return ResponseEntity.ok(BaseResponse.of(data, "재고 부족 품목 조회 성공"));
    }

    /**
     * 매출 추이 조회
     * GET /api/analytics/sales/trend?period=weekly
     */
    @GetMapping("/sales/trend")
    public ResponseEntity<BaseResponse<List<SalesTrendDTO>>> getSalesTrend(
            @RequestParam(defaultValue = "weekly") String period) {
        List<SalesTrendDTO> data = analyticsService.getSalesTrend(period);
        return ResponseEntity.ok(BaseResponse.of(data, "매출 추이 조회 성공"));
    }

    /**
     * 주문 추이 조회
     * GET /api/analytics/orders/trend?period=weekly
     */
    @GetMapping("/orders/trend")
    public ResponseEntity<BaseResponse<List<OrderTrendDTO>>> getOrderTrend(
            @RequestParam(defaultValue = "weekly") String period) {
        List<OrderTrendDTO> data = analyticsService.getOrderTrend(period);
        return ResponseEntity.ok(BaseResponse.of(data, "주문 추이 조회 성공"));
    }

    /**
     * 시간대별 매출 히트맵
     * GET /api/analytics/sales/heatmap?period=weekly
     */
    @GetMapping("/sales/heatmap")
    public ResponseEntity<BaseResponse<List<SalesHeatmapDTO>>> getSalesHeatmap(
            @RequestParam(defaultValue = "weekly") String period) {
        List<SalesHeatmapDTO> data = analyticsService.getSalesHeatmap(period);
        return ResponseEntity.ok(BaseResponse.of(data, "시간대별 매출 조회 성공"));
    }

    /**
     * 매장별 매출 전체 리스트 (페이지네이션)
     * GET /api/analytics/stores/all?page=0&size=20&period=weekly
     */
    @GetMapping("/stores/all")
    public ResponseEntity<BaseResponse<Page<StoreSalesDTO>>> getAllStores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "weekly") String period) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "totalRevenue"));
        Page<StoreSalesDTO> data = analyticsService.getStoresSalesPaged(period, pageable);
        return ResponseEntity.ok(BaseResponse.of(data, "매장 리스트 조회 성공"));
    }

    /**
     * 상품별 매출 전체 리스트 (페이지네이션)
     * GET /api/analytics/products/all?page=0&size=50&period=weekly
     */
    @GetMapping("/products/all")
    public ResponseEntity<BaseResponse<Page<ProductSalesDTO>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "weekly") String period) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "totalRevenue"));
        Page<ProductSalesDTO> data = analyticsService.getProductsSalesPaged(period, pageable);
        return ResponseEntity.ok(BaseResponse.of(data, "상품 리스트 조회 성공"));
    }

    /**
     * 고객 RFM 분석
     * GET /api/analytics/customers/rfm?limit=100
     */
    @GetMapping("/customers/rfm")
    public ResponseEntity<BaseResponse<List<CustomerRFMDTO>>> getCustomerRFMAnalysis(
            @RequestParam(defaultValue = "100") int limit) {
        List<CustomerRFMDTO> data = analyticsService.getCustomerRFMAnalysis(limit);
        return ResponseEntity.ok(BaseResponse.of(data, "고객 RFM 분석 조회 성공"));
    }

    /**
     * 상품 ABC 분석
     * GET /api/analytics/products/abc?period=monthly
     */
    @GetMapping("/products/abc")
    public ResponseEntity<BaseResponse<List<ProductABCDTO>>> getProductABCAnalysis(
            @RequestParam(defaultValue = "monthly") String period) {
        List<ProductABCDTO> data = analyticsService.getProductABCAnalysis(period);
        return ResponseEntity.ok(BaseResponse.of(data, "상품 ABC 분석 조회 성공"));
    }

    /**
     * 일별 매출 데이터 조회 (Sales Management 페이지용)
     * GET /api/analytics/sales/daily?startDate=2024-01-01&endDate=2024-12-31&storeId=1
     */
    @GetMapping("/sales/daily")
    public ResponseEntity<BaseResponse<List<DailySalesDTO>>> getDailySales(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Integer storeId) {
        List<DailySalesDTO> data = analyticsService.getDailySales(startDate, endDate, storeId);
        return ResponseEntity.ok(BaseResponse.of(data, "일별 매출 데이터 조회 성공"));
    }

    /**
     * 날짜별로 그룹화된 일별 매출 데이터 조회 (Sales Management 페이지용)
     * GET /api/analytics/sales/daily-grouped?startDate=2024-01-01&endDate=2024-12-31&storeId=1
     */
    @GetMapping("/sales/daily-grouped")
    public ResponseEntity<BaseResponse<List<DailySalesGroupDTO>>> getDailySalesGrouped(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Integer storeId) {
        List<DailySalesGroupDTO> data = analyticsService.getDailySalesGrouped(startDate, endDate, storeId);
        return ResponseEntity.ok(BaseResponse.of(data, "날짜별 그룹화된 매출 데이터 조회 성공"));
    }

    /**
     * 최근 7일간 매출 차트 데이터 (Sales Management 페이지용)
     * GET /api/analytics/sales/chart?storeId=1
     */
    @GetMapping("/sales/chart")
    public ResponseEntity<BaseResponse<List<SalesChartDataDTO>>> getSalesChartData(
            @RequestParam(required = false) Integer storeId) {
        List<SalesChartDataDTO> data = analyticsService.getSalesChartData(storeId);
        return ResponseEntity.ok(BaseResponse.of(data, "매출 차트 데이터 조회 성공"));
    }

    /**
     * 고객 분석 데이터 조회 (Customer Analytics 페이지용)
     * GET /api/analytics/customers/analytics
     */
    @GetMapping("/customers/analytics")
    public ResponseEntity<BaseResponse<List<CustomerAnalyticsDTO>>> getCustomerAnalytics() {
        List<CustomerAnalyticsDTO> data = analyticsService.getCustomerAnalytics();
        return ResponseEntity.ok(BaseResponse.of(data, "고객 분석 데이터 조회 성공"));
    }

    /**
     * 연령대별 고객 분포
     * GET /api/analytics/customers/age-distribution
     */
    @GetMapping("/customers/age-distribution")
    public ResponseEntity<BaseResponse<List<AgeDistributionDTO>>> getAgeDistribution() {
        List<AgeDistributionDTO> data = analyticsService.getAgeDistribution();
        return ResponseEntity.ok(BaseResponse.of(data, "연령대별 고객 분포 조회 성공"));
    }

    /**
     * 카테고리별 고객 매출 (Customer Analytics 페이지용)
     * GET /api/analytics/customers/category-sales
     */
    @GetMapping("/customers/category-sales")
    public ResponseEntity<BaseResponse<List<CategoryCustomerSalesDTO>>> getCategoryCustomerSales() {
        List<CategoryCustomerSalesDTO> data = analyticsService.getCategoryCustomerSales();
        return ResponseEntity.ok(BaseResponse.of(data, "카테고리별 고객 매출 조회 성공"));
    }
}
