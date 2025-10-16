package MeshX.HypeLink.auth.constansts;

public class SwaggerConstants {
    public static final String REGISTER_REQUEST = """
            {
              "email": "manager@example.com",
              "password": "password123!",
              "name": "John Manager",
              "role": "MANAGER"
            }
            """;

    public static final String LOGIN_REQUEST = """
            {
              "email": "manager@example.com",
              "password": "password123!"
            }
            """;

    public static final String TOKEN_RESPONSE = """
            {
              "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyQGV4YW1wbGUuY29tIiwicm9sZSI6Ik1BTkFHRVIiLCJpYXQiOjE2NjY2NjY2NjYsImV4cCI6MTY2NjY3MDI2Nn0.xxxxxxxxxxxxxxxxxxxxxxxxxxx"
            }
            """;
}
