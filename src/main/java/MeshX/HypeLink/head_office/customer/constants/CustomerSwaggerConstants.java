package MeshX.HypeLink.head_office.customer.constants;

public class CustomerSwaggerConstants {

    public static final String CUSTOMER_INFO_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "조회 성공",
              "result": {
                "customerId": 1,
                "name": "홍길동",
                "phone": "010-1234-5678",
                "birthday": "1990-01-01",
                "customerCoupons": [
                  {
                    "id": 1,
                    "couponId": 5,
                    "couponName": "신규 회원 할인 쿠폰",
                    "couponType": "PERCENTAGE",
                    "couponValue": 10,
                    "issuedDate": "2024-01-01",
                    "expirationDate": "2024-12-31",
                    "isUsed": false,
                    "usedDate": null
                  }
                ],
                "customerReceiptList": [
                  {
                    "id": 1,
                    "merchantUid": "ORDER_20240101_123456",
                    "totalAmount": 50000,
                    "couponDiscount": 5000,
                    "finalAmount": 45000,
                    "memberName": "홍길동",
                    "memberPhone": "010-1234-5678",
                    "status": "PAID",
                    "paidAt": "2024-01-01T10:30:00",
                    "cancelledAt": null,
                    "items": [],
                    "paymentMethod": "card"
                  }
                ]
              }
            }
            """;

    public static final String CUSTOMER_INFO_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "조회 성공",
              "result": {
                "customerInfoResList": [
                  {
                    "customerId": 1,
                    "name": "홍길동",
                    "phone": "010-1234-5678",
                    "birthday": "1990-01-01",
                    "customerCoupons": [],
                    "customerReceiptList": []
                  },
                  {
                    "customerId": 2,
                    "name": "김영희",
                    "phone": "010-9876-5432",
                    "birthday": "1995-05-15",
                    "customerCoupons": [],
                    "customerReceiptList": []
                  }
                ],
                "totalPages": 10,
                "totalElements": 95,
                "currentPage": 0,
                "pageSize": 10
              }
            }
            """;

    public static final String CUSTOMER_SIGNUP_REQ_EXAMPLE = """
            {
              "name": "홍길동",
              "phone": "010-1234-5678",
              "birthDate": "1990-01-01"
            }
            """;

    public static final String CUSTOMER_SIGNUP_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "가입이 완료되었습니다.",
              "result": "가입이 완료되었습니다."
            }
            """;

    public static final String CUSTOMER_UPDATE_REQ_EXAMPLE = """
            {
              "id": 1,
              "phone": "010-9999-8888"
            }
            """;

    public static final String CUSTOMER_UPDATE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "수정 완료",
              "result": {
                "customerId": 1,
                "name": "홍길동",
                "phone": "010-9999-8888",
                "birthday": "1990-01-01",
                "customerCoupons": [],
                "customerReceiptList": []
              }
            }
            """;

    public static final String COUPON_ISSUE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "쿠폰이 발급되었습니다.",
              "result": "쿠폰이 발급되었습니다."
            }
            """;

    public static final String RECEIPT_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "조회 성공",
              "result": {
                "receipts": [
                  {
                    "id": 1,
                    "merchantUid": "ORDER_20240101_123456",
                    "totalAmount": 50000,
                    "couponDiscount": 5000,
                    "finalAmount": 45000,
                    "memberName": "홍길동",
                    "memberPhone": "010-1234-5678",
                    "status": "PAID",
                    "paidAt": "2024-01-01T10:30:00",
                    "cancelledAt": null,
                    "items": [
                      {
                        "id": 1,
                        "productName": "상품 A",
                        "quantity": 2,
                        "price": 25000
                      }
                    ],
                    "paymentMethod": "card"
                  }
                ],
                "totalCount": 1
              }
            }
            """;

    public static final String RECEIPT_LIST_PAGING_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "조회 성공",
              "result": {
                "receipts": [
                  {
                    "id": 1,
                    "merchantUid": "ORDER_20240101_123456",
                    "totalAmount": 50000,
                    "couponDiscount": 5000,
                    "finalAmount": 45000,
                    "memberName": "홍길동",
                    "memberPhone": "010-1234-5678",
                    "status": "PAID",
                    "paidAt": "2024-01-01T10:30:00",
                    "cancelledAt": null,
                    "items": [],
                    "paymentMethod": "card"
                  }
                ],
                "totalPages": 5,
                "totalElements": 50,
                "currentPage": 0,
                "pageSize": 10
              }
            }
            """;
}
