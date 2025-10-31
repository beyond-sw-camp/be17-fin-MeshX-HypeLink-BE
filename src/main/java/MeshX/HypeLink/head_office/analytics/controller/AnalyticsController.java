package MeshX.HypeLink.head_office.analytics.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.analytics.constansts.AnalyticsSwaggerConstants;
import MeshX.HypeLink.head_office.analytics.dto.*;
import MeshX.HypeLink.head_office.analytics.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import MeshX.HypeLink.head_office.analytics.constansts.AnalyticsSwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "본사 분석 및 통계", description = "본사에서 전체 매장의 매출, 주문, 재고, 고객 등 다양한 분석 데이터를 조회하는 API")
@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    /**
     * 매출 현황 조회
     * GET /api/analytics/sales/overview?period=weekly&startDate=2024-01-01&endDate=2024-12-31&storeIds=1,2,3&categories=상의,하의
     */
    @Operation(summary = "매출 현황 조회", description = "지정된 기간 및 필터에 따른 매출 현황 요약 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매출 현황 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.SALES_OVERVIEW_RES_EXAMPLE)))
    })
    @GetMapping("/sales/overview")
    public ResponseEntity<BaseResponse<SalesOverviewDTO>> getSalesOverview(
            @Parameter(description = "조회 기간 (daily, weekly, monthly, yearly)", example = "weekly") @RequestParam(defaultValue = "weekly") String period,
            @Parameter(description = "시작일 (YYYY-MM-DD)", example = "2024-01-01") @RequestParam(required = false) String startDate,
            @Parameter(description = "종료일 (YYYY-MM-DD)", example = "2024-12-31") @RequestParam(required = false) String endDate,
            @Parameter(description = "매장 ID 목록 (쉼표로 구분)", example = "1,2,3") @RequestParam(required = false) List<Long> storeIds,
            @Parameter(description = "카테고리 목록 (쉼표로 구분)", example = "음료,과자") @RequestParam(required = false) List<String> categories) {
        SalesOverviewDTO data = analyticsService.getSalesOverview(period, startDate, endDate, storeIds, categories);
        return ResponseEntity.ok(BaseResponse.of(data, "매출 현황 조회 성공"));
    }

    /**
     * 주문 현황 조회
     * GET /api/analytics/orders/overview?period=weekly
     */
    @Operation(summary = "주문 현황 조회", description = "지정된 기간에 따른 주문 현황 요약 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 현황 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.ORDER_OVERVIEW_RES_EXAMPLE)))
    })
    @GetMapping("/orders/overview")
    public ResponseEntity<BaseResponse<OrderOverviewDTO>> getOrderOverview(
            @Parameter(description = "조회 기간 (daily, weekly, monthly, yearly)", example = "weekly") @RequestParam(defaultValue = "weekly") String period) {
        OrderOverviewDTO data = analyticsService.getOrderOverview(period);
        return ResponseEntity.ok(BaseResponse.of(data, "주문 현황 조회 성공"));
    }

    /**
     * 매장별 매출 TOP N
     * GET /api/analytics/stores/top?period=weekly&limit=5&startDate=2024-01-01&endDate=2024-12-31&categories=상의,하의
     */
    @Operation(summary = "매장별 매출 TOP N 조회", description = "지정된 기간 및 필터에 따라 매출 상위 N개 매장을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매장별 매출 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.STORE_SALES_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/stores/top")
    public ResponseEntity<BaseResponse<List<StoreSalesDTO>>> getTopStores(
            @Parameter(description = "조회 기간 (daily, weekly, monthly, yearly)", example = "weekly") @RequestParam(defaultValue = "weekly") String period,
            @Parameter(description = "조회할 매장 수", example = "5") @RequestParam(defaultValue = "5") int limit,
            @Parameter(description = "시작일 (YYYY-MM-DD)", example = "2024-01-01") @RequestParam(required = false) String startDate,
            @Parameter(description = "종료일 (YYYY-MM-DD)", example = "2024-12-31") @RequestParam(required = false) String endDate,
            @Parameter(description = "카테고리 목록 (쉼표로 구분)", example = "음료,과자") @RequestParam(required = false) List<String> categories) {
        List<StoreSalesDTO> data = analyticsService.getTopStoresBySales(period, limit, startDate, endDate, categories);
        return ResponseEntity.ok(BaseResponse.of(data, "매장별 매출 조회 성공"));
    }

    /**
     * 상품별 매출 TOP N
     * GET /api/analytics/products/top?period=weekly&limit=20&startDate=2024-01-01&endDate=2024-12-31&storeIds=1,2,3&categories=상의,하의
     */
    @Operation(summary = "상품별 매출 TOP N 조회", description = "지정된 기간 및 필터에 따라 매출 상위 N개 상품을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품별 매출 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.PRODUCT_SALES_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/products/top")
    public ResponseEntity<BaseResponse<List<ProductSalesDTO>>> getTopProducts(
            @Parameter(description = "조회 기간 (daily, weekly, monthly, yearly)", example = "weekly") @RequestParam(defaultValue = "weekly") String period,
            @Parameter(description = "조회할 상품 수", example = "20") @RequestParam(defaultValue = "20") int limit,
            @Parameter(description = "시작일 (YYYY-MM-DD)", example = "2024-01-01") @RequestParam(required = false) String startDate,
            @Parameter(description = "종료일 (YYYY-MM-DD)", example = "2024-12-31") @RequestParam(required = false) String endDate,
            @Parameter(description = "매장 ID 목록 (쉼표로 구분)", example = "1,2,3") @RequestParam(required = false) List<Long> storeIds,
            @Parameter(description = "카테고리 목록 (쉼표로 구분)", example = "음료,과자") @RequestParam(required = false) List<String> categories) {
        List<ProductSalesDTO> data = analyticsService.getTopProductsBySales(period, limit, startDate, endDate, storeIds, categories);
        return ResponseEntity.ok(BaseResponse.of(data, "상품별 매출 조회 성공"));
    }

    /**
     * 카테고리별 성과
     * GET /api/analytics/categories/performance?period=weekly
     */
    @Operation(summary = "카테고리별 성과 조회", description = "지정된 기간에 따른 카테고리별 매출 성과를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리별 성과 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.CATEGORY_PERFORMANCE_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/categories/performance")
    public ResponseEntity<BaseResponse<List<CategoryPerformanceDTO>>> getCategoryPerformance(
            @Parameter(description = "조회 기간 (daily, weekly, monthly, yearly)", example = "weekly") @RequestParam(defaultValue = "weekly") String period) {
        List<CategoryPerformanceDTO> data = analyticsService.getCategoryPerformance(period);
        return ResponseEntity.ok(BaseResponse.of(data, "카테고리별 성과 조회 성공"));
    }

    /**
     * 재고 부족 품목 조회
     * GET /api/analytics/inventory/low-stock?threshold=20
     */
    @Operation(summary = "재고 부족 품목 조회", description = "지정된 임계값 이하의 재고를 가진 품목 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재고 부족 품목 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.LOW_STOCK_ITEM_LIST_RES_EXAMPLE)))
    })
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
    @Operation(summary = "매출 추이 조회", description = "지정된 기간에 따른 매출 추이 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매출 추이 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.SALES_TREND_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/sales/trend")
    public ResponseEntity<BaseResponse<List<SalesTrendDTO>>> getSalesTrend(
            @Parameter(description = "조회 기간 (daily, weekly, monthly, yearly)", example = "weekly") @RequestParam(defaultValue = "weekly") String period) {
        List<SalesTrendDTO> data = analyticsService.getSalesTrend(period);
        return ResponseEntity.ok(BaseResponse.of(data, "매출 추이 조회 성공"));
    }

    /**
     * 주문 추이 조회
     * GET /api/analytics/orders/trend?period=weekly
     */
    @Operation(summary = "주문 추이 조회", description = "지정된 기간에 따른 주문 추이 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 추이 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.ORDER_TREND_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/orders/trend")
    public ResponseEntity<BaseResponse<List<OrderTrendDTO>>> getOrderTrend(
            @Parameter(description = "조회 기간 (daily, weekly, monthly, yearly)", example = "weekly") @RequestParam(defaultValue = "weekly") String period) {
        List<OrderTrendDTO> data = analyticsService.getOrderTrend(period);
        return ResponseEntity.ok(BaseResponse.of(data, "주문 추이 조회 성공"));
    }

    /**
     * 시간대별 매출 히트맵
     * GET /api/analytics/sales/heatmap?period=weekly
     */
    @Operation(summary = "시간대별 매출 히트맵 조회", description = "지정된 기간에 따른 시간대별 매출 히트맵 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "시간대별 매출 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.SALES_HEATMAP_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/sales/heatmap")
    public ResponseEntity<BaseResponse<List<SalesHeatmapDTO>>> getSalesHeatmap(
            @Parameter(description = "조회 기간 (daily, weekly, monthly, yearly)", example = "weekly") @RequestParam(defaultValue = "weekly") String period) {
        List<SalesHeatmapDTO> data = analyticsService.getSalesHeatmap(period);
        return ResponseEntity.ok(BaseResponse.of(data, "시간대별 매출 조회 성공"));
    }

    /**
     * 매장별 매출 전체 리스트 (페이지네이션)
     * GET /api/analytics/stores/all?page=0&size=20&period=weekly
     */
    @Operation(summary = "매장별 매출 전체 리스트 조회 (페이지네이션)", description = "모든 매장의 매출 데이터를 페이지네이션하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매장 리스트 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.STORE_SALES_PAGE_RES_EXAMPLE)))
    })
    @GetMapping("/stores/all")
    public ResponseEntity<BaseResponse<Page<StoreSalesDTO>>> getAllStores(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "20") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "조회 기간 (daily, weekly, monthly, yearly)", example = "weekly") @RequestParam(defaultValue = "weekly") String period) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "totalRevenue"));
        Page<StoreSalesDTO> data = analyticsService.getStoresSalesPaged(period, pageable);
        return ResponseEntity.ok(BaseResponse.of(data, "매장 리스트 조회 성공"));
    }

    /**
     * 상품별 매출 전체 리스트 (페이지네이션)
     * GET /api/analytics/products/all?page=0&size=50&period=weekly
     */
    @Operation(summary = "상품별 매출 전체 리스트 조회 (페이지네이션)", description = "모든 상품의 매출 데이터를 페이지네이션하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 리스트 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.PRODUCT_SALES_PAGE_RES_EXAMPLE)))
    })
    @GetMapping("/products/all")
    public ResponseEntity<BaseResponse<Page<ProductSalesDTO>>> getAllProducts(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "50") @RequestParam(defaultValue = "50") int size,
            @Parameter(description = "조회 기간 (daily, weekly, monthly, yearly)", example = "weekly") @RequestParam(defaultValue = "weekly") String period) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "totalRevenue"));
        Page<ProductSalesDTO> data = analyticsService.getProductsSalesPaged(period, pageable);
        return ResponseEntity.ok(BaseResponse.of(data, "상품 리스트 조회 성공"));
    }

    /**
     * 고객 RFM 분석
     * GET /api/analytics/customers/rfm?limit=100
     */
    @Operation(summary = "고객 RFM 분석 조회", description = "고객의 RFM(Recency, Frequency, Monetary) 분석 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고객 RFM 분석 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.CUSTOMER_RFM_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/customers/rfm")
    public ResponseEntity<BaseResponse<List<CustomerRFMDTO>>> getCustomerRFMAnalysis(
            @Parameter(description = "조회할 고객 수", example = "100") @RequestParam(defaultValue = "100") int limit) {
        List<CustomerRFMDTO> data = analyticsService.getCustomerRFMAnalysis(limit);
        return ResponseEntity.ok(BaseResponse.of(data, "고객 RFM 분석 조회 성공"));
    }

    /**
     * 상품 ABC 분석
     * GET /api/analytics/products/abc?period=monthly
     */
    @Operation(summary = "상품 ABC 분석 조회", description = "상품의 ABC 분석 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 ABC 분석 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.PRODUCT_ABC_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/products/abc")
    public ResponseEntity<BaseResponse<List<ProductABCDTO>>> getProductABCAnalysis(
            @Parameter(description = "조회 기간 (daily, weekly, monthly, yearly)", example = "monthly") @RequestParam(defaultValue = "monthly") String period) {
        List<ProductABCDTO> data = analyticsService.getProductABCAnalysis(period);
        return ResponseEntity.ok(BaseResponse.of(data, "상품 ABC 분석 조회 성공"));
    }

    /**
     * 일별 매출 데이터 조회 (Sales Management 페이지용)
     * GET /api/analytics/sales/daily?startDate=2024-01-01&endDate=2024-12-31&storeId=1
     */
    @Operation(summary = "일별 매출 데이터 조회 (Sales Management 페이지용)", description = "Sales Management 페이지에서 사용되는 일별 매출 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일별 매출 데이터 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.DAILY_SALES_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/sales/daily")
    public ResponseEntity<BaseResponse<List<DailySalesDTO>>> getDailySales(
            @Parameter(description = "시작일 (YYYY-MM-DD)", example = "2024-01-01") @RequestParam(required = false) String startDate,
            @Parameter(description = "종료일 (YYYY-MM-DD)", example = "2024-12-31") @RequestParam(required = false) String endDate,
            @Parameter(description = "매장 ID", example = "1") @RequestParam(required = false) Integer storeId) {
        List<DailySalesDTO> data = analyticsService.getDailySales(startDate, endDate, storeId);
        return ResponseEntity.ok(BaseResponse.of(data, "일별 매출 데이터 조회 성공"));
    }

    /**
     * 날짜별로 그룹화된 일별 매출 데이터 조회 (Sales Management 페이지용)
     * GET /api/analytics/sales/daily-grouped?startDate=2024-01-01&endDate=2024-12-31&storeId=1
     */
    @Operation(summary = "날짜별 그룹화된 일별 매출 데이터 조회 (Sales Management 페이지용)", description = "Sales Management 페이지에서 사용되는 날짜별로 그룹화된 일별 매출 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "날짜별 그룹화된 매출 데이터 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.DAILY_SALES_GROUPED_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/sales/daily-grouped")
    public ResponseEntity<BaseResponse<List<DailySalesGroupDTO>>> getDailySalesGrouped(
            @Parameter(description = "시작일 (YYYY-MM-DD)", example = "2024-01-01") @RequestParam(required = false) String startDate,
            @Parameter(description = "종료일 (YYYY-MM-DD)", example = "2024-12-31") @RequestParam(required = false) String endDate,
            @Parameter(description = "매장 ID", example = "1") @RequestParam(required = false) Integer storeId) {
        List<DailySalesGroupDTO> data = analyticsService.getDailySalesGrouped(startDate, endDate, storeId);
        return ResponseEntity.ok(BaseResponse.of(data, "날짜별 그룹화된 매출 데이터 조회 성공"));
    }

    /**
     * 최근 7일간 매출 차트 데이터 (Sales Management 페이지용)
     * GET /api/analytics/sales/chart?storeId=1
     */
    @Operation(summary = "최근 7일간 매출 차트 데이터 조회 (Sales Management 페이지용)", description = "Sales Management 페이지에서 사용되는 최근 7일간의 매출 차트 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매출 차트 데이터 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.SALES_CHART_DATA_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/sales/chart")
    public ResponseEntity<BaseResponse<List<SalesChartDataDTO>>> getSalesChartData(
            @Parameter(description = "매장 ID", example = "1") @RequestParam(required = false) Integer storeId) {
        List<SalesChartDataDTO> data = analyticsService.getSalesChartData(storeId);
        return ResponseEntity.ok(BaseResponse.of(data, "매출 차트 데이터 조회 성공"));
    }

    /**
     * 고객 분석 데이터 조회 (Customer Analytics 페이지용)
     * GET /api/analytics/customers/analytics
     */
    @Operation(summary = "고객 분석 데이터 조회 (Customer Analytics 페이지용)", description = "Customer Analytics 페이지에서 사용되는 고객 분석 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고객 분석 데이터 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.CUSTOMER_ANALYTICS_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/customers/analytics")
    public ResponseEntity<BaseResponse<List<CustomerAnalyticsDTO>>> getCustomerAnalytics() {
        List<CustomerAnalyticsDTO> data = analyticsService.getCustomerAnalytics();
        return ResponseEntity.ok(BaseResponse.of(data, "고객 분석 데이터 조회 성공"));
    }

    /**
     * 연령대별 고객 분포
     * GET /api/analytics/customers/age-distribution
     */
    @Operation(summary = "연령대별 고객 분포 조회", description = "연령대별 고객 분포 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "연령대별 고객 분포 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.AGE_DISTRIBUTION_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/customers/age-distribution")
    public ResponseEntity<BaseResponse<List<AgeDistributionDTO>>> getAgeDistribution() {
        List<AgeDistributionDTO> data = analyticsService.getAgeDistribution();
        return ResponseEntity.ok(BaseResponse.of(data, "연령대별 고객 분포 조회 성공"));
    }

    /**
     * 카테고리별 고객 매출 (Customer Analytics 페이지용)
     * GET /api/analytics/customers/category-sales
     */
    @Operation(summary = "카테고리별 고객 매출 조회 (Customer Analytics 페이지용)", description = "Customer Analytics 페이지에서 사용되는 카테고리별 고객 매출 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리별 고객 매출 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AnalyticsSwaggerConstants.CATEGORY_CUSTOMER_SALES_LIST_RES_EXAMPLE))) 
    })
    @GetMapping("/customers/category-sales")
    public ResponseEntity<BaseResponse<List<CategoryCustomerSalesDTO>>> getCategoryCustomerSales() {
        List<CategoryCustomerSalesDTO> data = analyticsService.getCategoryCustomerSales();
        return ResponseEntity.ok(BaseResponse.of(data, "카테고리별 고객 매출 조회 성공"));
    }

    /**
     * 특정 연령대의 인기 품목 조회 (드릴다운, 페이지네이션)
     * GET /api/analytics/customers/age-distribution/top-items?ageGroup=20대&page=0&size=10
     */
    @GetMapping("/customers/age-distribution/top-items")
    public ResponseEntity<BaseResponse<Page<TopItemBySegmentDTO>>> getTopItemsByAgeGroup(
            @RequestParam String ageGroup,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "salesAmount"));
        Page<TopItemBySegmentDTO> data = analyticsService.getTopItemsByAgeGroup(ageGroup, pageable);
        return ResponseEntity.ok(BaseResponse.of(data, "연령대별 인기 품목 조회 성공"));
    }

    /**
     * 특정 카테고리의 인기 품목 조회 (드릴다운, 페이지네이션)
     * GET /api/analytics/customers/category-sales/top-items?category=상의&page=0&size=10
     */
    @GetMapping("/customers/category-sales/top-items")
    public ResponseEntity<BaseResponse<Page<TopItemBySegmentDTO>>> getTopItemsByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "salesAmount"));
        Page<TopItemBySegmentDTO> data = analyticsService.getTopItemsByCategory(category, pageable);
        return ResponseEntity.ok(BaseResponse.of(data, "카테고리별 인기 품목 조회 성공"));
    }
}
