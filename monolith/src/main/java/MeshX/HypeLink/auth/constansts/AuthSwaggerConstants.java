package MeshX.HypeLink.auth.constansts;

public class AuthSwaggerConstants {

    // AuthController Examples
    public static final String REGISTER_REQ_EXAMPLE = """
            {
              "email": "test@example.com",
              "password": "password123!",
              "name": "Test User",
              "phone": "010-1234-5678"
            }
            """;

    public static final String REGISTER_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "회원가입이 성공하였습니다.",
              "result": "회원가입이 성공하였습니다."
            }
            """;

    public static final String LOGIN_REQ_EXAMPLE = """
            {
              "email": "test@example.com",
              "password": "password123!"
            }
            """;

    public static final String LOGIN_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "로그인 성공",
              "result": {
                "authTokens": {
                  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                },
                "memberId": 1,
                "email": "test@example.com",
                "name": "Test User",
                "role": "USER"
              }
            }
            """;

    public static final String TOKEN_RES_EXAMPLE = """
            {
              "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            }
            """;

    public static final String LOGOUT_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "로그아웃 성공",
              "result": "정상적으로 로그아웃 처리되었습니다."
            }
            """;

    // UserController Examples
    public static final String USER_LIST_RES_EXAMPLE = """
            {
              "userList": [
                {
                  "id": 1,
                  "email": "user1@example.com",
                  "name": "User One",
                  "role": "USER"
                },
                {
                  "id": 2,
                  "email": "user2@example.com",
                  "name": "User Two",
                  "role": "MANAGER"
                }
              ]
            }
            """;

    public static final String DRIVER_LIST_RES_EXAMPLE = """
            [
              {
                "id": 1,
                "name": "Driver One",
                "phone": "010-1111-2222"
              },
              {
                "id": 2,
                "name": "Driver Two",
                "phone": "010-3333-4444"
              }
            ]
            """;

    public static final String STORE_WITH_POS_LIST_RES_EXAMPLE = """
            [
              {
                "storeId": 1,
                "storeName": "Store A",
                "address": "Seoul",
                "posList": [
                  {
                    "posId": 101,
                    "posName": "POS A-1"
                  },
                  {
                    "posId": 102,
                    "posName": "POS A-2"
                  }
                ]
              },
              {
                "storeId": 2,
                "storeName": "Store B",
                "address": "Busan",
                "posList": []
              }
            ]
            """;

    public static final String STORE_LIST_RES_EXAMPLE = """
            [
              {
                "storeId": 1,
                "storeName": "Store A",
                "address": "Seoul"
              },
              {
                "storeId": 2,
                "storeName": "Store B",
                "address": "Busan"
              }
            ]
            """;

    public static final String STORE_WITH_POS_RES_EXAMPLE = """
            {
              "storeId": 1,
              "storeName": "My Store",
              "address": "Seoul",
              "posList": [
                {
                  "posId": 101,
                  "posName": "My POS 1"
                }
              ]
            }
            """;

    public static final String MY_STORE_ID_RES_EXAMPLE = """
            1
            """;

    public static final String STORE_INFO_RES_EXAMPLE = """
            {
              "storeId": 1,
              "storeName": "Updated Store Name",
              "address": "Updated Address",
              "phone": "02-1234-5678",
              "ownerName": "Updated Owner"
            }
            """;

    public static final String STORE_STATE_REQ_EXAMPLE = """
            {
              "state": "OPEN"
            }
            """;

    public static final String USER_READ_RES_EXAMPLE = """
            {
              "id": 1,
              "email": "user@example.com",
              "name": "User Name",
              "role": "USER",
              "phone": "010-1234-5678"
            }
            """;

    public static final String UPDATE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "성공적으로 변경하였습니다.",
              "result": "성공적으로 변경하였습니다."
            }
            """;

    public static final String DELETE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "사용자가 성공적으로 삭제되었습니다.",
              "result": "사용자가 성공적으로 삭제되었습니다."
            }
            """;

    public static final String STORE_ADD_INFO_LIST_RES_EXAMPLE = """
            {
              "storeAddInfoList": [
                {
                  "storeId": 1,
                  "storeName": "Store A",
                  "address": "Seoul"
                },
                {
                  "storeId": 2,
                  "storeName": "Store B",
                  "address": "Busan"
                }
              ]
            }
            """;

    // InternalSyncController Examples
    public static final String MEMBER_SYNC_LIST_RES_EXAMPLE = """
            [
              {
                "memberId": 1,
                "email": "user1@example.com",
                "name": "User One",
                "role": "USER",
                "phone": "010-1234-5678"
              },
              {
                "memberId": 2,
                "email": "user2@example.com",
                "name": "User Two",
                "role": "MANAGER",
                "phone": "010-2345-6789"
              }
            ]
            """;

    public static final String STORE_SYNC_LIST_RES_EXAMPLE = """
            [
              {
                "storeId": 1,
                "storeName": "Store A",
                "address": "Seoul",
                "phone": "02-1111-2222",
                "ownerName": "Owner A"
              },
              {
                "storeId": 2,
                "storeName": "Store B",
                "address": "Busan",
                "phone": "051-3333-4444",
                "ownerName": "Owner B"
              }
            ]
            """;

    public static final String POS_SYNC_LIST_RES_EXAMPLE = """
            [
              {
                "posId": 101,
                "posName": "POS A-1",
                "storeId": 1
              },
              {
                "posId": 102,
                "posName": "POS A-2",
                "storeId": 1
              }
            ]
            """;

    public static final String DRIVER_SYNC_LIST_RES_EXAMPLE = """
            [
              {
                "driverId": 1,
                "name": "Driver One",
                "phone": "010-1111-2222",
                "vehicleNumber": "12가3456"
              },
              {
                "driverId": 2,
                "name": "Driver Two",
                "phone": "010-3333-4444",
                "vehicleNumber": "78나9012"
              }
            ]
            """;

    public static final String SYNC_SUCCESS_RES_EXAMPLE = """
            {
              "message": "Successfully synced all items to store"
            }
            """;
}
