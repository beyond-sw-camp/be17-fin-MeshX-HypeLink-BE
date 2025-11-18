package com.example.apiitem.constants;

public class ItemSwaggerConstants {
    // Item API Examples (본사 상품 관리)
    public static final String ITEM_CREATE_REQ_EXAMPLE = """
            {
              "itemCode": "TS2025001",
              "koName": "베이직 반팔 티셔츠",
              "enName": "Basic Short Sleeve T-Shirt",
              "content": "부드러운 면 소재의 베이직한 반팔 티셔츠입니다.",
              "company": "MeshX Fashion",
              "unitPrice": 29000,
              "amount": 1000,
              "categoryId": 1,
              "images": [
                {
                  "imageUrl": "https://storage.example.com/items/TS2025001-main.jpg",
                  "imageOrder": 1
                },
                {
                  "imageUrl": "https://storage.example.com/items/TS2025001-detail.jpg",
                  "imageOrder": 2
                }
              ]
            }
            """;

    public static final String ITEM_CREATE_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "상품이 등록되었습니다.",
              "result": "result"
            }
            """;

    public static final String ITEM_INFO_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "상품 정보 조회 성공",
              "result": {
                "id": 1,
                "itemCode": "TS2025001",
                "koName": "베이직 반팔 티셔츠",
                "enName": "Basic Short Sleeve T-Shirt",
                "content": "부드러운 면 소재의 베이직한 반팔 티셔츠입니다.",
                "company": "MeshX Fashion",
                "unitPrice": 29000,
                "amount": 1000,
                "category": {
                  "id": 1,
                  "categoryName": "상의",
                  "categoryCode": "TOP"
                },
                "images": [
                  {
                    "id": 1,
                    "imageUrl": "https://storage.example.com/items/TS2025001-main.jpg",
                    "imageOrder": 1
                  },
                  {
                    "id": 2,
                    "imageUrl": "https://storage.example.com/items/TS2025001-detail.jpg",
                    "imageOrder": 2
                  }
                ],
                "createdAt": "2025-01-18T10:00:00",
                "updatedAt": "2025-01-18T10:00:00"
              }
            }
            """;

    public static final String ITEM_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "상품 목록 조회 성공",
              "result": {
                "content": [
                  {
                    "id": 1,
                    "itemCode": "TS2025001",
                    "koName": "베이직 반팔 티셔츠",
                    "enName": "Basic Short Sleeve T-Shirt",
                    "unitPrice": 29000,
                    "amount": 1000,
                    "categoryName": "상의"
                  },
                  {
                    "id": 2,
                    "itemCode": "JN2025001",
                    "koName": "슬림핏 청바지",
                    "enName": "Slim Fit Jeans",
                    "unitPrice": 59000,
                    "amount": 500,
                    "categoryName": "하의"
                  },
                  {
                    "id": 3,
                    "itemCode": "JK2025001",
                    "koName": "후드 집업 자켓",
                    "enName": "Hood Zip-up Jacket",
                    "unitPrice": 129000,
                    "amount": 300,
                    "categoryName": "아우터"
                  }
                ],
                "totalElements": 150,
                "totalPages": 15,
                "currentPage": 0,
                "size": 10
              }
            }
            """;

    public static final String ITEM_UPDATE_CONTENT_REQ_EXAMPLE = """
            {
              "itemCode": "TS2025001",
              "content": "프리미엄 면 소재를 사용한 고급 반팔 티셔츠입니다. 착용감이 우수합니다."
            }
            """;

    public static final String ITEM_UPDATE_NAME_REQ_EXAMPLE = """
            {
              "itemCode": "TS2025001",
              "koName": "프리미엄 반팔 티셔츠"
            }
            """;

    public static final String ITEM_UPDATE_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "수정이 완료되었습니다.",
              "result": "수정이 완료되었습니다."
            }
            """;

    public static final String ITEM_VALIDATE_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "저장이 완료되었습니다.",
              "result": "저장이 완료되었습니다."
            }
            """;

    // Item Detail API Examples (상품 상세 - 색상/사이즈 조합)
    public static final String ITEM_DETAIL_CREATE_REQ_EXAMPLE = """
            {
              "itemId": 1,
              "details": [
                {
                  "itemDetailCode": "TS2025001-BK-S",
                  "colorId": 1,
                  "sizeId": 1,
                  "additionalPrice": 0
                },
                {
                  "itemDetailCode": "TS2025001-BK-M",
                  "colorId": 1,
                  "sizeId": 2,
                  "additionalPrice": 0
                },
                {
                  "itemDetailCode": "TS2025001-WH-L",
                  "colorId": 2,
                  "sizeId": 3,
                  "additionalPrice": 0
                },
                {
                  "itemDetailCode": "TS2025001-NV-XL",
                  "colorId": 3,
                  "sizeId": 4,
                  "additionalPrice": 2000
                }
              ]
            }
            """;

    public static final String ITEM_DETAIL_CREATE_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "수정이 완료되었습니다.",
              "result": "수정이 완료되었습니다."
            }
            """;

    public static final String ITEM_DETAIL_INFO_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "상품 상세 정보 조회 성공",
              "result": {
                "itemDetail": {
                  "id": 1,
                  "itemDetailCode": "TS2025001-BK-M",
                  "color": {
                    "id": 1,
                    "colorName": "블랙",
                    "colorCode": "BLACK",
                    "hexCode": "#000000"
                  },
                  "size": {
                    "id": 2,
                    "sizeName": "M",
                    "sizeCode": "M"
                  },
                  "additionalPrice": 0
                },
                "item": {
                  "id": 1,
                  "itemCode": "TS2025001",
                  "koName": "베이직 반팔 티셔츠",
                  "enName": "Basic Short Sleeve T-Shirt",
                  "unitPrice": 29000,
                  "categoryName": "상의"
                }
              }
            }
            """;

    public static final String ITEM_DETAIL_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "상품 상세 목록 조회 성공",
              "result": {
                "details": [
                  {
                    "id": 1,
                    "itemDetailCode": "TS2025001-BK-S",
                    "colorName": "블랙",
                    "sizeName": "S",
                    "additionalPrice": 0
                  },
                  {
                    "id": 2,
                    "itemDetailCode": "TS2025001-BK-M",
                    "colorName": "블랙",
                    "sizeName": "M",
                    "additionalPrice": 0
                  },
                  {
                    "id": 3,
                    "itemDetailCode": "TS2025001-WH-L",
                    "colorName": "화이트",
                    "sizeName": "L",
                    "additionalPrice": 0
                  },
                  {
                    "id": 4,
                    "itemDetailCode": "TS2025001-NV-XL",
                    "colorName": "네이비",
                    "sizeName": "XL",
                    "additionalPrice": 2000
                  }
                ],
                "totalCount": 4
              }
            }
            """;

    // Category API Examples
    public static final String CATEGORY_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "카테고리 목록 조회 성공",
              "result": {
                "categories": [
                  {
                    "id": 1,
                    "categoryName": "상의",
                    "categoryCode": "TOP"
                  },
                  {
                    "id": 2,
                    "categoryName": "하의",
                    "categoryCode": "BOTTOM"
                  },
                  {
                    "id": 3,
                    "categoryName": "아우터",
                    "categoryCode": "OUTER"
                  },
                  {
                    "id": 4,
                    "categoryName": "원피스",
                    "categoryCode": "DRESS"
                  },
                  {
                    "id": 5,
                    "categoryName": "악세서리",
                    "categoryCode": "ACCESSORY"
                  }
                ]
              }
            }
            """;

    // Color API Examples
    public static final String COLOR_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "색상 목록 조회 성공",
              "result": {
                "colors": [
                  {
                    "id": 1,
                    "colorName": "블랙",
                    "colorCode": "BLACK",
                    "hexCode": "#000000"
                  },
                  {
                    "id": 2,
                    "colorName": "화이트",
                    "colorCode": "WHITE",
                    "hexCode": "#FFFFFF"
                  },
                  {
                    "id": 3,
                    "colorName": "네이비",
                    "colorCode": "NAVY",
                    "hexCode": "#000080"
                  },
                  {
                    "id": 4,
                    "colorName": "그레이",
                    "colorCode": "GRAY",
                    "hexCode": "#808080"
                  },
                  {
                    "id": 5,
                    "colorName": "베이지",
                    "colorCode": "BEIGE",
                    "hexCode": "#F5F5DC"
                  }
                ]
              }
            }
            """;

    // Size API Examples
    public static final String SIZE_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "사이즈 목록 조회 성공",
              "result": {
                "sizes": [
                  {
                    "id": 1,
                    "sizeName": "S",
                    "sizeCode": "S"
                  },
                  {
                    "id": 2,
                    "sizeName": "M",
                    "sizeCode": "M"
                  },
                  {
                    "id": 3,
                    "sizeName": "L",
                    "sizeCode": "L"
                  },
                  {
                    "id": 4,
                    "sizeName": "XL",
                    "sizeCode": "XL"
                  },
                  {
                    "id": 5,
                    "sizeName": "XXL",
                    "sizeCode": "XXL"
                  },
                  {
                    "id": 6,
                    "sizeName": "FREE",
                    "sizeCode": "FREE"
                  }
                ]
              }
            }
            """;
}
