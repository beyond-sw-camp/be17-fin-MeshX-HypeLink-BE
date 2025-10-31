package MeshX.HypeLink.direct_store.item.constansts;

public class ItemSwaggerConstants {

    // StoreCategoryController Examples
    public static final String SAVE_STORE_CATEGORIES_REQ_EXAMPLE = """
            {
              "categoryList": [
                {
                  "categoryName": "음료",
                  "categoryCode": "DRINK"
                },
                {
                  "categoryName": "과자",
                  "categoryCode": "SNACK"
                }
              ]
            }
            """;

    public static final String SAVE_CATEGORY_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "카테고리를 저장하였습니다.",
              "result": "카테고리를 저장하였습니다."
            }
            """;

    public static final String STORE_CATEGORY_INFO_LIST_RES_EXAMPLE = """
            {
              "categoryInfoList": [
                {
                  "categoryId": 1,
                  "categoryName": "음료",
                  "categoryCode": "DRINK"
                },
                {
                  "categoryId": 2,
                  "categoryName": "과자",
                  "categoryCode": "SNACK"
                }
              ]
            }
            """;

    // StoreItemController Examples
    public static final String SAVE_STORE_ITEM_LIST_REQ_EXAMPLE = """
            {
              "storeId": 1,
              "itemSaveList": [
                {
                  "itemCode": "ITEM001",
                  "itemName": "콜라",
                  "itemEngName": "Coke",
                  "barcode": "1234567890123",
                  "price": 1500,
                  "stock": 100,
                  "categoryCode": "DRINK"
                },
                {
                  "itemCode": "ITEM002",
                  "itemName": "새우깡",
                  "itemEngName": "Shrimp Cracker",
                  "barcode": "9876543210987",
                  "price": 1200,
                  "stock": 50,
                  "categoryCode": "SNACK"
                }
              ]
            }
            """;

    public static final String SAVE_ITEM_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "본사 아이템 리스트와 동기화가 완료되었습니다.",
              "result": "본사 아이템 리스트와 동기화가 완료되었습니다."
            }
            """;

    public static final String STORE_ITEM_DETAILS_INFO_RES_PAGE_EXAMPLE = """
            {
              "content": [
                {
                  "itemDetailId": 1,
                  "itemCode": "ITEM001",
                  "itemName": "콜라",
                  "itemEngName": "Coke",
                  "barcode": "1234567890123",
                  "price": 1500,
                  "stock": 100,
                  "categoryName": "음료"
                },
                {
                  "itemDetailId": 2,
                  "itemCode": "ITEM002",
                  "itemName": "새우깡",
                  "itemEngName": "Shrimp Cracker",
                  "barcode": "9876543210987",
                  "price": 1200,
                  "stock": 50,
                  "categoryName": "과자"
                }
              ],
              "pageable": {
                "pageNumber": 0,
                "pageSize": 20,
                "sort": {
                  "empty": true,
                  "sorted": false,
                  "unsorted": true
                },
                "offset": 0,
                "paged": true,
                "unpaged": false
              },
              "last": true,
              "totalPages": 1,
              "totalElements": 2,
              "size": 20,
              "number": 0,
              "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
              },
              "first": true,
              "numberOfElements": 2,
              "empty": false
            }
            """;

    public static final String STORE_ITEM_DETAIL_INFO_RES_EXAMPLE = """
            {
              "itemDetailId": 1,
              "itemCode": "ITEM001",
              "itemName": "콜라",
              "itemEngName": "Coke",
              "barcode": "1234567890123",
              "price": 1500,
              "stock": 100,
              "categoryName": "음료"
            }
            """;

    public static final String UPDATE_STORE_ITEM_DETAIL_REQ_EXAMPLE = """
            {
              "itemDetailId": 1,
              "stock": 120
            }
            """;

    public static final String UPDATE_STOCK_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "재고 업데이트가 성공했습니다.",
              "result": "재고 업데이트가 성공했습니다."
            }
            """;

    public static final String STORE_ITEM_DETAIL_RES_PAGE_EXAMPLE = """
            {
              "content": [
                {
                  "itemDetailId": 1,
                  "itemCode": "ITEM001",
                  "itemName": "콜라",
                  "itemEngName": "Coke",
                  "barcode": "1234567890123",
                  "price": 1500,
                  "stock": 100,
                  "categoryName": "음료"
                }
              ],
              "pageable": {
                "pageNumber": 0,
                "pageSize": 50,
                "sort": {
                  "empty": true,
                  "sorted": false,
                  "unsorted": true
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
                "empty": true,
                "sorted": false,
                "unsorted": true
              },
              "first": true,
              "numberOfElements": 1,
              "empty": false
            }
            """;

    public static final String STORE_ITEM_DETAIL_RES_EXAMPLE = """
            {
              "itemDetailId": 1,
              "itemCode": "ITEM001",
              "itemName": "콜라",
              "itemEngName": "Coke",
              "barcode": "1234567890123",
              "price": 1500,
              "stock": 100,
              "categoryName": "음료"
            }
            """;
}
