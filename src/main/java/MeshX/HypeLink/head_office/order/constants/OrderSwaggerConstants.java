package MeshX.HypeLink.head_office.order.constants;

public class OrderSwaggerConstants {

    public static final String PURCHASE_ORDER_CREATE_REQ_EXAMPLE = """
            {
              "itemId": 1,
              "quantity": 100,
              "storeId": 5
            }
            """;

    public static final String PURCHASE_ORDER_DETAIL_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "조회 성공",
              "result": {
                "id": 1,
                "itemName": "상품명",
                "quantity": 100,
                "storeName": "강남 직영점",
                "status": "PENDING",
                "createdAt": "2024-01-01T10:00:00"
              }
            }
            """;
}
