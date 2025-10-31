package MeshX.HypeLink.head_office.item.constants;

public class ItemSwaggerConstants {

    public static final String ITEM_INFO_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "조회 성공",
              "result": {
                "id": 1,
                "itemCode": "ITEM001",
                "koName": "상품명",
                "enName": "Item Name",
                "content": "상품 설명",
                "unitPrice": 50000,
                "amount": 100,
                "company": "제조사",
                "categoryName": "상의"
              }
            }
            """;

    public static final String ITEM_CREATE_REQ_EXAMPLE = """
            {
              "koName": "상품명",
              "enName": "Item Name",
              "content": "상품 설명",
              "unitPrice": 50000,
              "amount": 100,
              "company": "제조사",
              "categoryId": 1
            }
            """;

    public static final String ITEM_UPDATE_SUCCESS_EXAMPLE = """
            {
              "code": "200",
              "message": "수정이 완료되었습니다.",
              "result": "수정이 완료되었습니다."
            }
            """;

    public static final String CATEGORY_INFO_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "요청이 성공했습니다.",
              "result": {
                "categoryInfoResList": [
                  {
                    "id": 1,
                    "name": "상의"
                  },
                  {
                    "id": 2,
                    "name": "하의"
                  },
                  {
                    "id": 3,
                    "name": "신발"
                  }
                ]
              }
            }
            """;

    public static final String SIZE_INFO_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "요청이 성공했습니다.",
              "result": {
                "sizeInfoResList": [
                  {
                    "id": 1,
                    "name": "S"
                  },
                  {
                    "id": 2,
                    "name": "M"
                  },
                  {
                    "id": 3,
                    "name": "L"
                  },
                  {
                    "id": 4,
                    "name": "XL"
                  }
                ]
              }
            }
            """;

    public static final String COLOR_INFO_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "요청이 성공했습니다.",
              "result": {
                "colorInfoResList": [
                  {
                    "id": 1,
                    "name": "블랙"
                  },
                  {
                    "id": 2,
                    "name": "화이트"
                  },
                  {
                    "id": 3,
                    "name": "네이비"
                  }
                ]
              }
            }
            """;

    public static final String ITEM_DETAIL_INFO_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "요청이 성공했습니다.",
              "result": {
                "itemDetailId": 1,
                "itemDetailCode": "ITEM001-S-BLK",
                "itemId": 1,
                "itemCode": "ITEM001",
                "koName": "상품명",
                "enName": "Item Name",
                "content": "상품 설명",
                "unitPrice": 50000,
                "company": "제조사",
                "categoryName": "상의",
                "sizeName": "S",
                "colorName": "블랙",
                "amount": 50
              }
            }
            """;

    public static final String ITEM_DETAILS_INFO_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "요청이 성공했습니다.",
              "result": {
                "itemDetailInfoResList": [
                  {
                    "id": 1,
                    "itemDetailCode": "ITEM001-S-BLK",
                    "sizeName": "S",
                    "colorName": "블랙",
                    "amount": 50
                  },
                  {
                    "id": 2,
                    "itemDetailCode": "ITEM001-M-BLK",
                    "sizeName": "M",
                    "colorName": "블랙",
                    "amount": 30
                  }
                ]
              }
            }
            """;

    public static final String CREATE_ITEM_DETAILS_REQ_EXAMPLE = """
            {
              "itemId": 1,
              "itemDetails": [
                {
                  "sizeId": 1,
                  "colorId": 1,
                  "amount": 50
                },
                {
                  "sizeId": 2,
                  "colorId": 1,
                  "amount": 30
                }
              ]
            }
            """;
}
