package MeshX.HypeLink.head_office.notice.constansts;

public class NoticeSwaggerConstants {

    // NoticeController Examples
    public static final String NOTICE_CREATE_REQ_EXAMPLE = """
            {
              "title": "시스템 점검 안내",
              "content": "2025년 1월 20일 새벽 2시부터 4시까지 시스템 점검이 있습니다.",
              "isImportant": true,
              "targetRole": "ALL"
            }
            """;

    public static final String NOTICE_UPDATE_REQ_EXAMPLE = """
            {
              "title": "수정된 제목",
              "content": "수정된 내용",
              "isImportant": false
            }
            """;

    public static final String NOTICE_DETAIL_RES_EXAMPLE = """
            {
              "id": 1,
              "title": "시스템 점검 안내",
              "content": "2025년 1월 20일 새벽 2시부터 4시까지 시스템 점검이 있습니다.",
              "isImportant": true,
              "targetRole": "ALL",
              "createdAt": "2025-01-15T10:00:00",
              "updatedAt": "2025-01-15T10:00:00",
              "views": 150
            }
            """;

    public static final String NOTICE_INFO_LIST_RES_EXAMPLE = """
            {
              "notices": [
                {
                  "id": 1,
                  "title": "시스템 점검 안내",
                  "isImportant": true,
                  "createdAt": "2025-01-15T10:00:00",
                  "views": 150
                },
                {
                  "id": 2,
                  "title": "신규 기능 안내",
                  "isImportant": false,
                  "createdAt": "2025-01-14T09:00:00",
                  "views": 80
                }
              ]
            }
            """;

    public static final String NOTICE_LIST_PAGE_RES_EXAMPLE = """
            {
              "content": [
                {
                  "id": 1,
                  "title": "시스템 점검 안내",
                  "isImportant": true,
                  "createdAt": "2025-01-15T10:00:00"
                }
              ],
              "totalPages": 5,
              "totalElements": 50,
              "size": 10,
              "number": 0
            }
            """;

    public static final String NOTICE_CREATE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "생성되었습니다.",
              "result": "생성되었습니다."
            }
            """;

    public static final String NOTICE_DELETE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "성공적으로 삭제 되었습니다.",
              "result": "성공적으로 삭제 되었습니다."
            }
            """;
}
