package MeshX.HypeLink.head_office.notice.constansts;

public class SwaggerConstants {
    public static final String NOTICE_CREATE_REQUEST = """
            {
              "title": "System Maintenance Announcement",
              "contents": "There will be a scheduled system maintenance on Sunday at 2 AM."
            }
            """;

    public static final String NOTICE_UPDATE_REQUEST = """
            {
              "title": "Update: System Maintenance Announcement",
              "contents": "The scheduled system maintenance has been postponed to 4 AM.",
              "isOpen": true
            }
            """;

    public static final String NOTICE_INFO_RESPONSE = """
            {
              "title": "System Maintenance Announcement",
              "contents": "There will be a scheduled system maintenance on Sunday at 2 AM.",
              "isOpen": true
            }
            """;

    public static final String NOTICE_INFO_LIST_RESPONSE = """
            {
              "notices": [
                {
                  "title": "System Maintenance Announcement",
                  "contents": "There will be a scheduled system maintenance on Sunday at 2 AM.",
                  "isOpen": true
                },
                {
                  "title": "New Feature Update",
                  "contents": "Introducing the new dashboard feature.",
                  "isOpen": true
                }
              ]
            }
            """;
}
