package MeshX.HypeLink.direct_store.constansts;

public class DirectStoreSwaggerConstants {

    // PaymentController Examples
    public static final String PAYMENT_VALIDATION_REQ_EXAMPLE = """
            {
              "paymentId": "payment_123",
              "amount": 50000,
              "itemDetailCodes": ["DETAIL001", "DETAIL002"]
            }
            """;

    public static final String PAYMENT_VALIDATION_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "검증 성공",
              "result": "검증 성공 "
            }
            """;

    // StoreItemController Examples
    public static final String SAVE_STORE_ITEM_LIST_REQ_EXAMPLE = """
            {
              "storeId": 1,
              "items": [
                {
                  "itemCode": "ITEM001",
                  "itemDetailCode": "DETAIL001",
                  "stock": 100
                },
                {
                  "itemCode": "ITEM002",
                  "itemDetailCode": "DETAIL002",
                  "stock": 50
                }
              ]
            }
            """;

    public static final String STORE_ITEM_DETAILS_INFO_RES_EXAMPLE = """
            {
              "content": [
                {
                  "itemDetailCode": "DETAIL001",
                  "itemCode": "ITEM001",
                  "koName": "테스트 상품",
                  "enName": "Test Item",
                  "stock": 100,
                  "unitPrice": 50000,
                  "category": "Electronics"
                }
              ],
              "totalPages": 10,
              "totalElements": 100,
              "size": 10,
              "number": 0
            }
            """;

    public static final String STORE_ITEM_DETAIL_INFO_RES_EXAMPLE = """
            {
              "itemDetailCode": "DETAIL001",
              "itemCode": "ITEM001",
              "koName": "테스트 상품",
              "enName": "Test Item",
              "stock": 100,
              "unitPrice": 50000,
              "category": "Electronics",
              "color": "Red",
              "size": "L"
            }
            """;

    public static final String UPDATE_STORE_ITEM_DETAIL_REQ_EXAMPLE = """
            {
              "itemDetailCode": "DETAIL001",
              "stock": 150
            }
            """;

    public static final String STORE_ITEM_DETAIL_RES_EXAMPLE = """
            {
              "itemDetailCode": "DETAIL001",
              "itemCode": "ITEM001",
              "koName": "테스트 상품",
              "enName": "Test Item",
              "stock": 100,
              "unitPrice": 50000
            }
            """;

    public static final String STORE_ITEM_DETAIL_PAGE_RES_EXAMPLE = """
            {
              "content": [
                {
                  "itemDetailCode": "DETAIL001",
                  "itemCode": "ITEM001",
                  "koName": "테스트 상품",
                  "stock": 100
                }
              ],
              "totalPages": 5,
              "totalElements": 50,
              "size": 10,
              "number": 0
            }
            """;

    public static final String SYNC_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "본사 아이템 리스트와 동기화가 완료되었습니다.",
              "result": "본사 아이템 리스트와 동기화가 완료되었습니다."
            }
            """;

    public static final String UPDATE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "재고 업데이트가 성공했습니다.",
              "result": "재고 업데이트가 성공했습니다."
            }
            """;

    // StoreCategoryController Examples
    public static final String SAVE_STORE_CATEGORIES_REQ_EXAMPLE = """
            {
              "storeId": 1,
              "categories": [
                {
                  "name": "Electronics"
                },
                {
                  "name": "Fashion"
                }
              ]
            }
            """;

    public static final String STORE_CATEGORY_INFO_LIST_RES_EXAMPLE = """
            {
              "categories": [
                {
                  "id": 1,
                  "name": "Electronics"
                },
                {
                  "id": 2,
                  "name": "Fashion"
                }
              ]
            }
            """;

    public static final String CATEGORY_SAVE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "카테고리를 저장하였습니다.",
              "result": "카테고리를 저장하였습니다."
            }
            """;
}
