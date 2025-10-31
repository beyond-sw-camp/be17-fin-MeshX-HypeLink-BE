package MeshX.HypeLink.head_office.notice.constants;

public class NoticeSwaggerConstants {

    public static final String NOTICE_CREATE_REQ_EXAMPLE = """
            {
              "title": "공지사항 제목",
              "content": "공지사항 내용입니다."
            }
            """;

    public static final String NOTICE_DETAIL_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "성공",
              "result": {
                "id": 1,
                "title": "공지사항 제목",
                "content": "공지사항 내용입니다.",
                "createdAt": "2024-01-01T10:00:00"
              }
            }
            """;
}
