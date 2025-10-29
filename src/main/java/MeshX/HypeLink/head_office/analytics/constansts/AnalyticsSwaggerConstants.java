package MeshX.HypeLink.head_office.analytics.constansts;

public class AnalyticsSwaggerConstants {

    public static final String SALES_OVERVIEW_RES_EXAMPLE = """
            {
              "totalSales": 1234567.89,
              "totalOrders": 1234,
              "averageOrderValue": 1000.00,
              "salesGrowthRate": 0.15,
              "period": "weekly",
              "startDate": "2024-01-01",
              "endDate": "2024-01-07"
            }
            """;

    public static final String ORDER_OVERVIEW_RES_EXAMPLE = """
            {
              "totalOrders": 1234,
              "completedOrders": 1000,
              "pendingOrders": 200,
              "cancelledOrders": 34,
              "orderCompletionRate": 0.81,
              "period": "weekly"
            }
            """;

    public static final String STORE_SALES_LIST_RES_EXAMPLE = """
            [
              {
                "storeId": 1,
                "storeName": "강남점",
                "totalRevenue": 500000.00,
                "orderCount": 500
              },
              {
                "storeId": 2,
                "storeName": "홍대점",
                "totalRevenue": 300000.00,
                "orderCount": 300
              }
            ]
            """;

    public static final String PRODUCT_SALES_LIST_RES_EXAMPLE = """
            [
              {
                "productId": 101,
                "productName": "아메리카노",
                "totalRevenue": 150000.00,
                "salesVolume": 1000
              },
              {
                "productId": 102,
                "productName": "카페라떼",
                "totalRevenue": 120000.00,
                "salesVolume": 800
              }
            ]
            """;

    public static final String CATEGORY_PERFORMANCE_LIST_RES_EXAMPLE = """
            [
              {
                "categoryName": "음료",
                "totalRevenue": 800000.00,
                "salesVolume": 5000,
                "profitMargin": 0.6
              },
              {
                "categoryName": "베이커리",
                "totalRevenue": 400000.00,
                "salesVolume": 2000,
                "profitMargin": 0.4
              }
            ]
            """;

    public static final String LOW_STOCK_ITEM_LIST_RES_EXAMPLE = """
            [
              {
                "itemId": 201,
                "itemName": "우유",
                "currentStock": 5,
                "minStockThreshold": 10,
                "storeName": "강남점"
              },
              {
                "itemId": 202,
                "itemName": "원두",
                "currentStock": 8,
                "minStockThreshold": 15,
                "storeName": "홍대점"
              }
            ]
            """;

    public static final String SALES_TREND_LIST_RES_EXAMPLE = """
            [
              {
                "date": "2024-01-01",
                "dailySales": 100000.00
              },
              {
                "date": "2024-01-02",
                "dailySales": 120000.00
              }
            ]
            """;

    public static final String ORDER_TREND_LIST_RES_EXAMPLE = """
            [
              {
                "date": "2024-01-01",
                "dailyOrders": 50
              },
              {
                "date": "2024-01-02",
                "dailyOrders": 60
              }
            ]
            """;

    public static final String SALES_HEATMAP_LIST_RES_EXAMPLE = """
            [
              {
                "timeSlot": "09-10",
                "dayOfWeek": "월",
                "salesAmount": 50000.00
              },
              {
                "timeSlot": "10-11",
                "dayOfWeek": "월",
                "salesAmount": 70000.00
              }
            ]
            """;

    public static final String STORE_SALES_PAGE_RES_EXAMPLE = """
            {
              "content": [
                {
                  "storeId": 1,
                  "storeName": "강남점",
                  "totalRevenue": 500000.00,
                  "orderCount": 500
                }
              ],
              "pageable": {
                "pageNumber": 0,
                "pageSize": 20,
                "sort": {
                  "empty": false,
                  "sorted": true,
                  "unsorted": false
                },
                "offset": 0,
                "paged": true,
                "unpaged": false
              },
              "last": true,
              "totalPages": 1,
              "totalElements": 1,
              "size": 20,
              "number": 0,
              "sort": {
                "empty": false,
                "sorted": true,
                "unsorted": false
              },
              "first": true,
              "numberOfElements": 1,
              "empty": false
            }
            """;

    public static final String PRODUCT_SALES_PAGE_RES_EXAMPLE = """
            {
              "content": [
                {
                  "productId": 101,
                  "productName": "아메리카노",
                  "totalRevenue": 150000.00,
                  "salesVolume": 1000
                }
              ],
              "pageable": {
                "pageNumber": 0,
                "pageSize": 50,
                "sort": {
                  "empty": false,
                  "sorted": true,
                  "unsorted": false
                },
                "offset": 0,
                "paged": true,
                "unpaged": false
              },
              "last": true,
              "totalPages": 1,
              "totalElements": 1,
              "size": 50,
              "number": 0,
              "sort": {
                "empty": false,
                "sorted": true,
                "unsorted": false
              },
              "first": true,
              "numberOfElements": 1,
              "empty": false
            }
            """;

    public static final String CUSTOMER_RFM_LIST_RES_EXAMPLE = """
            [
              {
                "customerId": 1,
                "customerName": "김철수",
                "recency": 5,
                "frequency": 10,
                "monetary": 100000.00,
                "rfmScore": "VIP"
              },
              {
                "customerId": 2,
                "customerName": "박영희",
                "recency": 20,
                "frequency": 3,
                "monetary": 30000.00,
                "rfmScore": "일반"
              }
            ]
            """;

    public static final String PRODUCT_ABC_LIST_RES_EXAMPLE = """
            [
              {
                "productId": 101,
                "productName": "아메리카노",
                "salesContribution": 0.30,
                "abcClass": "A"
              },
              {
                "productId": 102,
                "productName": "카페라떼",
                "salesContribution": 0.20,
                "abcClass": "A"
              }
            ]
            """;

    public static final String DAILY_SALES_LIST_RES_EXAMPLE = """
            [
              {
                "date": "2024-01-01",
                "storeId": 1,
                "totalSales": 100000.00,
                "orderCount": 50
              },
              {
                "date": "2024-01-01",
                "storeId": 2,
                "totalSales": 80000.00,
                "orderCount": 40
              }
            ]
            """;

    public static final String DAILY_SALES_GROUPED_LIST_RES_EXAMPLE = """
            [
              {
                "date": "2024-01-01",
                "totalSales": 180000.00,
                "orderCount": 90
              },
              {
                "date": "2024-01-02",
                "totalSales": 200000.00,
                "orderCount": 100
              }
            ]
            """;

    public static final String SALES_CHART_DATA_LIST_RES_EXAMPLE = """
            [
              {
                "date": "2024-01-01",
                "salesAmount": 100000.00
              },
              {
                "date": "2024-01-02",
                "salesAmount": 120000.00
              }
            ]
            """;

    public static final String CUSTOMER_ANALYTICS_LIST_RES_EXAMPLE = """
            [
              {
                "metricName": "신규 고객 수",
                "value": 150
              },
              {
                "metricName": "재방문율",
                "value": 0.65
              }
            ]
            """;

    public static final String AGE_DISTRIBUTION_LIST_RES_EXAMPLE = """
            [
              {
                "ageGroup": "20대",
                "customerCount": 120,
                "percentage": 0.30
              },
              {
                "ageGroup": "30대",
                "customerCount": 150,
                "percentage": 0.37
              }
            ]
            """;

    public static final String CATEGORY_CUSTOMER_SALES_LIST_RES_EXAMPLE = """
            [
              {
                "categoryName": "음료",
                "customerCount": 500,
                "totalSales": 800000.00
              },
              {
                "categoryName": "베이커리",
                "customerCount": 300,
                "totalSales": 400000.00
              }
            ]
            """;
}
