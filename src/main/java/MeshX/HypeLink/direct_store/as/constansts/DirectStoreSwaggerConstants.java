package MeshX.HypeLink.direct_store.as.constansts;

public class DirectStoreSwaggerConstants {

    public static final String AS_CREATE_REQ_EXAMPLE = """
            {
              "title": "AS 신청 제목",
              "content": "AS 신청 내용입니다. 상세하게 작성해주세요.",
              "productName": "제품명",
              "purchaseDate": "2023-01-01",
              "contactNumber": "010-1234-5678"
            }
            """;

    public static final String AS_CREATE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "AS 신청이 완료되었습니다.",
              "result": "AS 신청이 완료되었습니다."
            }
            """;

    public static final String MY_AS_LIST_RES_EXAMPLE = """
            [
              {
                "id": 1,
                "title": "제품 A AS 요청",
                "status": "접수",
                "requestDate": "2023-10-26T10:00:00"
              },
              {
                "id": 2,
                "title": "제품 B 수리 문의",
                "status": "처리중",
                "requestDate": "2023-10-25T14:30:00"
              }
            ]
            """;

    public static final String MY_AS_DETAIL_RES_EXAMPLE = """
            {
              "id": 1,
              "title": "제품 A AS 요청",
              "content": "제품 A의 전원 불량으로 AS 요청합니다.",
              "productName": "제품 A",
              "purchaseDate": "2023-01-01",
              "contactNumber": "010-1234-5678",
              "status": "접수",
              "requestDate": "2023-10-26T10:00:00",
              "responseContent": null
            }
            """;

    public static final String AS_UPDATE_REQ_EXAMPLE = """
            {
              "title": "AS 신청 제목 수정",
              "content": "AS 신청 내용 수정입니다. 더 상세하게 작성해주세요.",
              "contactNumber": "010-9876-5432"
            }
            """;

    public static final String AS_UPDATE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "AS 요청이 성공적으로 업데이트되었습니다.",
              "result": {
                "id": 1,
                "title": "AS 신청 제목 수정",
                "content": "AS 신청 내용 수정입니다. 더 상세하게 작성해주세요.",
                "productName": "제품 A",
                "purchaseDate": "2023-01-01",
                "contactNumber": "010-9876-5432",
                "status": "접수",
                "requestDate": "2023-10-26T10:00:00",
                "responseContent": null
              }
            }
            """;

    public static final String AS_DELETE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "AS 요청이 삭제되었습니다.",
              "result": "AS 요청이 삭제되었습니다."
            }
            """;
}
