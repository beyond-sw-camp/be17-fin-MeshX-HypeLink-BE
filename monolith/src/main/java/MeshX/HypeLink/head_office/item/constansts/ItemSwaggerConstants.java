package MeshX.HypeLink.head_office.item.constansts;

public class ItemSwaggerConstants {

    // ItemController Examples
    public static final String ITEM_INFO_RES_EXAMPLE = """
            {
              "id": 1,
              "itemCode": "ITEM001",
              "koName": "테스트 상품",
              "enName": "Test Item",
              "company": "Test Company",
              "category": "Electronics",
              "unitPrice": 50000,
              "amount": 100,
              "contents": "상품 설명",
              "images": ["image1.jpg", "image2.jpg"]
            }
            """;

    public static final String ITEM_PAGE_RES_EXAMPLE = """
            {
              "content": [
                {
                  "id": 1,
                  "itemCode": "ITEM001",
                  "koName": "테스트 상품",
                  "enName": "Test Item",
                  "company": "Test Company",
                  "category": "Electronics",
                  "unitPrice": 50000,
                  "amount": 100
                },
                {
                  "id": 2,
                  "itemCode": "ITEM002",
                  "koName": "테스트 상품2",
                  "enName": "Test Item 2",
                  "company": "Test Company",
                  "category": "Fashion",
                  "unitPrice": 30000,
                  "amount": 50
                }
              ],
              "totalPages": 10,
              "totalElements": 100,
              "size": 10,
              "number": 0
            }
            """;

    public static final String CREATE_ITEM_REQ_EXAMPLE = """
            {
              "itemCode": "ITEM001",
              "koName": "테스트 상품",
              "enName": "Test Item",
              "company": "Test Company",
              "categoryId": 1,
              "unitPrice": 50000,
              "amount": 100,
              "contents": "상품 설명",
              "images": ["image1.jpg", "image2.jpg"]
            }
            """;

    public static final String SAVE_ITEM_REQ_EXAMPLE = """
            {
              "itemCode": "ITEM001",
              "koName": "테스트 상품",
              "enName": "Test Item",
              "company": "Test Company",
              "categoryId": 1,
              "unitPrice": 50000,
              "amount": 100
            }
            """;

    public static final String UPDATE_ITEM_CONTENT_REQ_EXAMPLE = """
            {
              "itemCode": "ITEM001",
              "contents": "수정된 상품 설명"
            }
            """;

    public static final String UPDATE_ITEM_EN_NAME_REQ_EXAMPLE = """
            {
              "itemCode": "ITEM001",
              "enName": "Updated Item Name"
            }
            """;

    public static final String UPDATE_ITEM_KO_NAME_REQ_EXAMPLE = """
            {
              "itemCode": "ITEM001",
              "koName": "수정된 상품명"
            }
            """;

    public static final String UPDATE_ITEM_AMOUNT_REQ_EXAMPLE = """
            {
              "itemCode": "ITEM001",
              "amount": 200
            }
            """;

    public static final String UPDATE_ITEM_UNIT_PRICE_REQ_EXAMPLE = """
            {
              "itemCode": "ITEM001",
              "unitPrice": 60000
            }
            """;

    public static final String UPDATE_ITEM_COMPANY_REQ_EXAMPLE = """
            {
              "itemCode": "ITEM001",
              "company": "New Company"
            }
            """;

    public static final String UPDATE_ITEM_CATEGORY_REQ_EXAMPLE = """
            {
              "itemCode": "ITEM001",
              "categoryId": 2
            }
            """;

    public static final String UPDATE_ITEM_IMAGES_REQ_EXAMPLE = """
            {
              "itemCode": "ITEM001",
              "images": ["new_image1.jpg", "new_image2.jpg", "new_image3.jpg"]
            }
            """;

    public static final String UPDATE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "수정이 완료되었습니다.",
              "result": "수정이 완료되었습니다."
            }
            """;

    public static final String CREATE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "상품이 성공적으로 생성되었습니다.",
              "result": "result"
            }
            """;

    public static final String SYNC_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "동기화가 완료되었습니다.",
              "result": "동기화가 완료되었습니다."
            }
            """;

    public static final String VALIDATE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "아이템이 존재합니다.",
              "result": "아이템이 존재합니다."
            }
            """;

    // CategoryController Examples
    public static final String CATEGORY_INFO_LIST_RES_EXAMPLE = """
            {
              "categoryList": [
                {
                  "id": 1,
                  "name": "Electronics"
                },
                {
                  "id": 2,
                  "name": "Fashion"
                },
                {
                  "id": 3,
                  "name": "Food"
                }
              ]
            }
            """;

    // ColorController Examples
    public static final String COLOR_INFO_LIST_RES_EXAMPLE = """
            {
              "colorList": [
                {
                  "id": 1,
                  "name": "Red",
                  "code": "#FF0000"
                },
                {
                  "id": 2,
                  "name": "Blue",
                  "code": "#0000FF"
                },
                {
                  "id": 3,
                  "name": "Green",
                  "code": "#00FF00"
                }
              ]
            }
            """;

    // SizeController Examples
    public static final String SIZE_INFO_LIST_RES_EXAMPLE = """
            {
              "sizeList": [
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
            """;
}
