package com.example.apidirect.constants;

public class DirectSwaggerConstants {
    // Customer API Examples
    public static final String CUSTOMER_SIGNUP_REQ_EXAMPLE = """
            {
              "name": "홍길동",
              "phone": "010-1234-5678",
              "ageGroup": "20s",
              "gender": "MALE"
            }
            """;

    public static final String CUSTOMER_SIGNUP_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "고객 가입 성공",
              "result": {
                "id": 1,
                "name": "홍길동",
                "phone": "010-1234-5678",
                "ageGroup": "20s",
                "gender": "MALE",
                "createdAt": "2025-01-18T10:30:00"
              }
            }
            """;

    public static final String CUSTOMER_INFO_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "고객 정보 조회 성공",
              "result": {
                "id": 1,
                "name": "홍길동",
                "phone": "010-1234-5678",
                "ageGroup": "20s",
                "gender": "MALE",
                "totalPurchaseAmount": 150000,
                "visitCount": 5
              }
            }
            """;

    public static final String CUSTOMER_WITH_COUPONS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "고객 쿠폰 정보 조회 성공",
              "result": {
                "id": 1,
                "name": "홍길동",
                "phone": "010-1234-5678",
                "ageGroup": "20s",
                "gender": "MALE",
                "availableCoupons": [
                  {
                    "couponId": 101,
                    "couponName": "신규가입 10% 할인",
                    "discountRate": 10,
                    "expiryDate": "2025-12-31"
                  },
                  {
                    "couponId": 102,
                    "couponName": "VIP 회원 20% 할인",
                    "discountRate": 20,
                    "expiryDate": "2025-12-31"
                  }
                ]
              }
            }
            """;

    public static final String CUSTOMER_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "고객 목록 조회 성공",
              "result": {
                "content": [
                  {
                    "id": 1,
                    "name": "홍길동",
                    "phone": "010-1234-5678",
                    "ageGroup": "20s",
                    "gender": "MALE"
                  },
                  {
                    "id": 2,
                    "name": "김철수",
                    "phone": "010-2345-6789",
                    "ageGroup": "30s",
                    "gender": "MALE"
                  }
                ],
                "totalElements": 100,
                "totalPages": 10,
                "currentPage": 0,
                "size": 10
              }
            }
            """;

    public static final String CUSTOMER_UPDATE_REQ_EXAMPLE = """
            {
              "id": 1,
              "name": "홍길동",
              "ageGroup": "30s"
            }
            """;

    public static final String CUSTOMER_UPDATE_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "고객 정보 수정 성공",
              "result": {
                "id": 1,
                "name": "홍길동",
                "phone": "010-1234-5678",
                "ageGroup": "30s",
                "gender": "MALE"
              }
            }
            """;

    // Store Item API Examples (매장 의류 재고 관리)
    public static final String STORE_ITEM_CREATE_REQ_EXAMPLE = """
            {
              "storeId": 1,
              "items": [
                {
                  "itemCode": "TS2025001",
                  "itemName": "베이직 반팔 티셔츠",
                  "category": "상의",
                  "price": 29000,
                  "details": [
                    {
                      "detailCode": "TS2025001-BK-M",
                      "color": "블랙",
                      "size": "M",
                      "stock": 50
                    },
                    {
                      "detailCode": "TS2025001-WH-L",
                      "color": "화이트",
                      "size": "L",
                      "stock": 30
                    }
                  ]
                }
              ]
            }
            """;

    public static final String STORE_ITEM_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "매장 상품 목록 조회 성공",
              "result": {
                "content": [
                  {
                    "itemDetailCode": "TS2025001-BK-M",
                    "itemCode": "TS2025001",
                    "itemName": "베이직 반팔 티셔츠",
                    "category": "상의",
                    "color": "블랙",
                    "size": "M",
                    "price": 29000,
                    "stock": 50,
                    "barcode": "8801234567890"
                  },
                  {
                    "itemDetailCode": "JN2025001-NV-L",
                    "itemCode": "JN2025001",
                    "itemName": "슬림핏 청바지",
                    "category": "하의",
                    "color": "네이비",
                    "size": "L",
                    "price": 59000,
                    "stock": 25,
                    "barcode": "8801234567891"
                  }
                ],
                "totalElements": 150,
                "totalPages": 15,
                "currentPage": 0,
                "size": 10
              }
            }
            """;

    public static final String STORE_ITEM_DETAIL_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "상품 상세 정보 조회 성공",
              "result": {
                "itemDetailCode": "TS2025001-BK-M",
                "itemCode": "TS2025001",
                "itemName": "베이직 반팔 티셔츠",
                "category": "상의",
                "color": "블랙",
                "size": "M",
                "price": 29000,
                "stock": 50,
                "barcode": "8801234567890",
                "imageUrl": "https://storage.example.com/items/TS2025001-BK-M.jpg"
              }
            }
            """;

    public static final String STORE_ITEM_UPDATE_REQ_EXAMPLE = """
            {
              "itemDetailCode": "TS2025001-BK-M",
              "updateStock": 45
            }
            """;

    public static final String STORE_ITEM_UPDATE_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "성공",
              "result": "성공"
            }
            """;

    public static final String STORE_ITEM_LOW_STOCK_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "재고 부족 상품 조회 성공",
              "result": {
                "content": [
                  {
                    "itemDetailCode": "JK2025001-GY-XL",
                    "itemCode": "JK2025001",
                    "itemName": "후드 집업 자켓",
                    "category": "아우터",
                    "color": "그레이",
                    "size": "XL",
                    "stock": 3,
                    "minStockAlert": 10
                  }
                ],
                "totalElements": 5,
                "totalPages": 1,
                "currentPage": 0
              }
            }
            """;

    public static final String STORE_ITEM_SEARCH_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "상품 검색 성공",
              "result": {
                "content": [
                  {
                    "itemDetailCode": "TS2025001-BK-M",
                    "itemName": "베이직 반팔 티셔츠",
                    "color": "블랙",
                    "size": "M",
                    "stock": 50
                  }
                ],
                "totalElements": 1
              }
            }
            """;

    // Category API Examples
    public static final String CATEGORY_SAVE_REQ_EXAMPLE = """
            {
              "storeId": 1,
              "categories": [
                {
                  "categoryName": "상의",
                  "categoryCode": "TOP"
                },
                {
                  "categoryName": "하의",
                  "categoryCode": "BOTTOM"
                },
                {
                  "categoryName": "아우터",
                  "categoryCode": "OUTER"
                },
                {
                  "categoryName": "원피스",
                  "categoryCode": "DRESS"
                }
              ]
            }
            """;

    public static final String CATEGORY_SAVE_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "카테고리를 저장하였습니다.",
              "result": "카테고리를 저장하였습니다."
            }
            """;

    // Payment API Examples
    public static final String PAYMENT_VALIDATE_REQ_EXAMPLE = """
            {
              "paymentId": "pay_20250118103045",
              "storeId": 1,
              "amount": 88000,
              "paymentMethod": "CARD",
              "items": [
                {
                  "itemDetailCode": "TS2025001-BK-M",
                  "quantity": 1,
                  "price": 29000
                },
                {
                  "itemDetailCode": "JN2025001-NV-L",
                  "quantity": 1,
                  "price": 59000
                }
              ]
            }
            """;

    public static final String PAYMENT_VALIDATE_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "검증 성공",
              "result": "검증 성공"
            }
            """;

    // Receipt API Examples
    public static final String RECEIPT_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "영수증 목록 조회 성공",
              "result": {
                "content": [
                  {
                    "receiptId": 1,
                    "customerName": "홍길동",
                    "customerPhone": "010-1234-5678",
                    "totalAmount": 88000,
                    "discountAmount": 8800,
                    "finalAmount": 79200,
                    "paymentMethod": "CARD",
                    "items": [
                      {
                        "itemName": "베이직 반팔 티셔츠",
                        "color": "블랙",
                        "size": "M",
                        "quantity": 1,
                        "price": 29000
                      },
                      {
                        "itemName": "슬림핏 청바지",
                        "color": "네이비",
                        "size": "L",
                        "quantity": 1,
                        "price": 59000
                      }
                    ],
                    "createdAt": "2025-01-18T10:30:45"
                  },
                  {
                    "receiptId": 2,
                    "customerName": "김철수",
                    "customerPhone": "010-2345-6789",
                    "totalAmount": 129000,
                    "discountAmount": 0,
                    "finalAmount": 129000,
                    "paymentMethod": "CASH",
                    "items": [
                      {
                        "itemName": "후드 집업 자켓",
                        "color": "그레이",
                        "size": "XL",
                        "quantity": 1,
                        "price": 129000
                      }
                    ],
                    "createdAt": "2025-01-18T11:15:20"
                  }
                ],
                "totalElements": 200,
                "totalPages": 20,
                "currentPage": 0,
                "size": 10
              }
            }
            """;
}
