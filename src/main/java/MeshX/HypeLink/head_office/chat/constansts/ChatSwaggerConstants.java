package MeshX.HypeLink.head_office.chat.constansts;

public class ChatSwaggerConstants {

    public static final String CHAT_MESSAGE_REQ_EXAMPLE = """
            {
              "receiverId": 2,
              "message": "안녕하세요, 메시지 테스트입니다."
            }
            """;

    public static final String CHAT_MESSAGE_RES_EXAMPLE = """
            {
              "id": 1,
              "senderId": 1,
              "receiverId": 2,
              "message": "안녕하세요, 메시지 테스트입니다.",
              "timestamp": "2023-10-27T10:00:00",
              "read": false
            }
            """;

    public static final String CHAT_MESSAGE_PAGE_RES_EXAMPLE = """
            {
              "content": [
                {
                  "id": 1,
                  "senderId": 1,
                  "receiverId": 2,
                  "message": "안녕하세요, 메시지 테스트입니다.",
                  "timestamp": "2023-10-27T10:00:00",
                  "read": true
                },
                {
                  "id": 2,
                  "senderId": 2,
                  "receiverId": 1,
                  "message": "네, 안녕하세요!",
                  "timestamp": "2023-10-27T10:01:00",
                  "read": true
                }
              ],
              "pageable": {
                "pageNumber": 0,
                "pageSize": 10,
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
              "size": 10,
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

    public static final String MESSAGE_USER_LIST_RES_EXAMPLE = """
            [
              {
                "id": 2,
                "email": "user2@example.com",
                "name": "User Two",
                "lastMessage": "네, 안녕하세요!",
                "lastMessageTime": "2023-10-27T10:01:00",
                "unreadCount": 0
              },
              {
                "id": 3,
                "email": "user3@example.com",
                "name": "User Three",
                "lastMessage": "확인했습니다.",
                "lastMessageTime": "2023-10-26T15:00:00",
                "unreadCount": 1
              }
            ]
            """;

    public static final String MESSAGE_READ_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "메시지 읽음 처리 성공",
              "result": null
            }
            """;
}
