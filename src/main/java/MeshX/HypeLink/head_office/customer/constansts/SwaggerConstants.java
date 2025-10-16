package MeshX.HypeLink.head_office.customer.constansts;

public class SwaggerConstants {
    public static final String CUSTOMER_SIGNUP_REQUEST = """
            {
              "email": "customer@example.com",
              "password": "password123!",
              "name": "John Doe",
              "phone": "010-1234-5678",
              "birthDate": "1990-01-01"
            }
            """;

    public static final String CUSTOMER_INFO_RESPONSE = """
            {
              "email": "customer@example.com",
              "name": "John Doe",
              "phone": "010-1234-5678",
              "birthday": "1990-01-01"
            }
            """;

    public static final String CUSTOMER_INFO_LIST_RESPONSE = """
            {
              "customerInfoResList": [
                {
                  "email": "customer1@example.com",
                  "name": "John Doe",
                  "phone": "010-1111-2222",
                  "birthday": "1990-01-01"
                },
                {
                  "email": "customer2@example.com",
                  "name": "Jane Smith",
                  "phone": "010-3333-4444",
                  "birthday": "1992-05-10"
                }
              ]
            }
            """;
            
    public static final String UPDATE_PHONE_REQUEST = """
            "010-9876-5432"
            """;

    public static final String UPDATE_PASSWORD_REQUEST = """
            "newPassword123!"
            """;
}
