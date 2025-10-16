package MeshX.HypeLink.direct_store.payment.constansts;

public class SwaggerConstants {
    public static final String PAYMENT_VALIDATION_REQUEST = """
            {
              "paymentId": "portone-payment-id-12345",
              "orderData": {
                "storeId": 101,
                "memberId": 202,
                "memberName": "John Doe",
                "memberPhone": "010-1234-5678",
                "pointsUsed": 5000,
                "couponDiscount": 5000,
                "items": [
                  {
                    "productId": 303,
                    "productName": "Hype-Tee",
                    "quantity": 2,
                    "unitPrice": 50000,
                    "discountPrice": 5000,
                    "subtotal": 90000
                  }
                ]
              }
            }
            """;

    public static final String PAYMENT_VALIDATION_RESPONSE = """
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
}
