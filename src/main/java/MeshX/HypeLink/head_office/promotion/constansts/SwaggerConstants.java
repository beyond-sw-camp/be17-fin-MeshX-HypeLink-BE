package MeshX.HypeLink.head_office.promotion.constansts;

public class SwaggerConstants {
    public static final String PROMOTION_CREATE_REQUEST = """
            {
              "promotionType": "CATEGORY",
              "category": "TOP_CLOTHES",
              "storeId": null,
              "itemId": null,
              "title": "Summer T-Shirt Sale",
              "contents": "All top clothes are 20% off for this summer!",
              "discountRate": 20.0,
              "startDate": "2025-06-01",
              "endDate": "2025-08-31"
            }
            """;

    public static final String PROMOTION_UPDATE_REQUEST = """
            {
              "promotionType": "CATEGORY",
              "category": "TOP_CLOTHES",
              "title": "Extended Summer T-Shirt Sale",
              "contents": "All top clothes are now 25% off!",
              "discountRate": 25.0,
              "startDate": "2025-06-01",
              "endDate": "2025-09-15"
            }
            """;

    public static final String PROMOTION_INFO_LIST_RESPONSE = """
            {
              "promotions": [
                {
                  "promotionType": "CATEGORY",
                  "category": "TOP_CLOTHES",
                  "title": "Summer T-Shirt Sale",
                  "contents": "All top clothes are 20% off for this summer!",
                  "discountRate": 20.0,
                  "startDate": "2025-06-01",
                  "endDate": "2025-08-31"
                },
                {
                  "promotionType": "ALL",
                  "category": null,
                  "title": "Grand Opening Event",
                  "contents": "10% off on all items.",
                  "discountRate": 10.0,
                  "startDate": "2025-01-01",
                  "endDate": "2025-01-31"
                }
              ]
            }
            """;

    public static final String PROMOTION_PAGE_RESPONSE = """
            {
              "content": [
                {
                  "promotionType": "CATEGORY",
                  "category": "TOP_CLOTHES",
                  "title": "Summer T-Shirt Sale",
                  "contents": "All top clothes are 20% off for this summer!",
                  "discountRate": 20.0,
                  "startDate": "2025-06-01",
                  "endDate": "2025-08-31"
                }
              ],
              "page": 0,
              "size": 1,
              "totalElements": 1,
              "totalPages": 1,
              "first": true,
              "last": true
            }
            """;
            
    public static final String PROMOTION_INFO_RESPONSE = """
            {
              "promotionType": "CATEGORY",
              "category": "TOP_CLOTHES",
              "title": "Summer T-Shirt Sale",
              "contents": "All top clothes are 20% off for this summer!",
              "discountRate": 20.0,
              "startDate": "2025-06-01",
              "endDate": "2025-08-31"
            }
            """;
}
