package MeshX.HypeLink.head_office.as.constants;

public class HeadOfficeAsSwaggerConstants {

    public static final String AS_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "AS 목록 조회 성공",
              "result": [
                {
                  "id": 1,
                  "title": "제품 A AS 요청",
                  "storeName": "강남 직영점",
                  "status": "PENDING",
                  "createdAt": "2023-10-26T10:00:00"
                },
                {
                  "id": 2,
                  "title": "제품 B 수리 문의",
                  "storeName": "홍대 직영점",
                  "status": "APPROVED",
                  "createdAt": "2023-10-25T14:30:00"
                }
              ]
            }
            """;

    public static final String AS_LIST_PAGING_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "AS 목록 조회 성공",
              "result": {
                "asListResList": [
                  {
                    "id": 1,
                    "title": "제품 A AS 요청",
                    "storeName": "강남 직영점",
                    "status": "PENDING",
                    "createdAt": "2023-10-26T10:00:00"
                  },
                  {
                    "id": 2,
                    "title": "제품 B 수리 문의",
                    "storeName": "홍대 직영점",
                    "status": "APPROVED",
                    "createdAt": "2023-10-25T14:30:00"
                  }
                ],
                "totalPages": 5,
                "totalElements": 75,
                "currentPage": 0,
                "pageSize": 15
              }
            }
            """;

    public static final String AS_DETAIL_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "AS 상세 조회 성공",
              "result": {
                "id": 1,
                "title": "제품 A AS 요청",
                "description": "제품 A의 전원 불량으로 AS 요청합니다.",
                "storeName": "강남 직영점",
                "storeId": 5,
                "status": "PENDING",
                "createdAt": "2023-10-26T10:00:00",
                "comments": [
                  {
                    "id": 1,
                    "memberName": "홍길동",
                    "memberRole": "HEAD_OFFICE",
                    "description": "AS 요청 확인했습니다. 처리 진행하겠습니다.",
                    "createdAt": "2023-10-26T11:00:00"
                  }
                ]
              }
            }
            """;

    public static final String AS_STATUS_UPDATE_REQ_EXAMPLE = """
            {
              "status": "APPROVED"
            }
            """;

    public static final String AS_STATUS_UPDATE_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "AS 상태 변경 성공",
              "result": {
                "id": 1,
                "title": "제품 A AS 요청",
                "description": "제품 A의 전원 불량으로 AS 요청합니다.",
                "storeName": "강남 직영점",
                "storeId": 5,
                "status": "APPROVED",
                "createdAt": "2023-10-26T10:00:00",
                "comments": []
              }
            }
            """;

    public static final String COMMENT_CREATE_REQ_EXAMPLE = """
            {
              "description": "AS 요청 확인했습니다. 처리 진행하겠습니다."
            }
            """;

    public static final String COMMENT_CREATE_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "댓글 작성 성공",
              "result": {
                "id": 1,
                "title": "제품 A AS 요청",
                "description": "제품 A의 전원 불량으로 AS 요청합니다.",
                "storeName": "강남 직영점",
                "storeId": 5,
                "status": "PENDING",
                "createdAt": "2023-10-26T10:00:00",
                "comments": [
                  {
                    "id": 1,
                    "memberName": "홍길동",
                    "memberRole": "HEAD_OFFICE",
                    "description": "AS 요청 확인했습니다. 처리 진행하겠습니다.",
                    "createdAt": "2023-10-26T11:00:00"
                  }
                ]
              }
            }
            """;
}
