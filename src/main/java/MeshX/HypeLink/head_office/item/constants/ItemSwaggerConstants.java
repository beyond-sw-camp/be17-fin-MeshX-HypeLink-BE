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
}
