package MeshX.HypeLink.head_office.message.constansts;

public class SwaggerConstants {
    public static final String MESSAGE_CREATE_REQUEST = """
            {
              "title": "Important: New Policy Update",
              "contents": "Please be advised of the new company policy regarding..."
            }
            """;

    public static final String MESSAGE_UPDATE_REQUEST = """
            {
              "title": "Update: New Policy Information",
              "contents": "Further clarification on the new company policy..."
            }
            """;

    public static final String MESSAGE_INFO_RESPONSE = """
            {
              "title": "Important: New Policy Update",
              "contents": "Please be advised of the new company policy regarding..."
            }
            """;

    public static final String MESSAGE_INFO_LIST_RESPONSE = """
            {
              "messages": [
                {
                  "title": "Important: New Policy Update",
                  "contents": "Please be advised of the new company policy regarding..."
                },
                {
                  "title": "Reminder: Quarterly Meeting",
                  "contents": "This is a reminder for the quarterly meeting on Friday."
                }
              ]
            }
            """;
}
