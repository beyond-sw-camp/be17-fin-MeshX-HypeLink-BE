package MeshX.HypeLink.head_office.message.constants;

public class MessageSwaggerConstants {

    public static final String MESSAGE_CREATE_REQ_EXAMPLE = """
            {
              "title": "메시지 제목",
              "content": "메시지 내용입니다."
            }
            """;

    public static final String MESSAGE_INFO_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "성공",
              "result": {
                "id": 1,
                "title": "메시지 제목",
                "content": "메시지 내용입니다.",
                "isRead": false,
                "createdAt": "2024-01-01T10:00:00"
              }
            }
            """;
}
