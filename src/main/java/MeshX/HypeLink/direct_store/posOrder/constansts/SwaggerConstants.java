package MeshX.HypeLink.direct_store.posOrder.constansts;

public class SwaggerConstants {
    public static final String POS_ORDER_DETAIL_RESPONSE = """
            {
              "id": 1,
              "orderNumber": "PO20251016-0001",
              "storeId": 101,
              "memberId": 202,
              "memberName": "John Doe",
              "memberPhone": "010-1234-5678",
              "totalAmount": 100000,
              "discountAmount": 10000,
              "pointsUsed": 5000,
              "couponDiscount": 5000,
              "finalAmount": 90000,
              "status": "PAID",
              "items": [
                {
                  "id": 1,
                  "productId": 303,
                  "productName": "Hype-Tee",
                  "quantity": 2,
                  "unitPrice": 50000,
                  "discountPrice": 5000,
                  "subtotal": 90000
                }
              ],
              "createdAt": "2025-10-16T14:30:00Z"
            }
            """;

    public static final String POS_ORDER_INFO_LIST_RESPONSE = """
            [
              {
                "id": 1,
                "orderNumber": "PO20251016-0001",
                "storeId": 101,
                "memberName": "John Doe",
                "memberPhone": "010-1234-5678",
                "finalAmount": 90000,
                "status": "PAID",
                "createdAt": "2025-10-16T14:30:00Z"
              },
              {
                "id": 2,
                "orderNumber": "PO20251016-0002",
                "storeId": 101,
                "memberName": "Jane Smith",
                "memberPhone": "010-9876-5432",
                "finalAmount": 75000,
                "status": "COMPLETED",
                "createdAt": "2025-10-16T15:00:00Z"
              }
            ]
            """;
}
