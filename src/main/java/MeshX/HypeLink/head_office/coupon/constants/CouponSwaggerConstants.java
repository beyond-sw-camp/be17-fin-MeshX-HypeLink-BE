package MeshX.HypeLink.head_office.coupon.constants;

public class CouponSwaggerConstants {

    public static final String COUPON_CREATE_REQ_EXAMPLE = """
            {
              "name": "신규 회원 할인 쿠폰",
              "couponType": "PERCENTAGE",
              "value": 10,
              "period": "2024-01-01~2024-12-31"
            }
            """;

    public static final String COUPON_CREATE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "쿠폰 생성 완료",
              "result": "쿠폰 생성 완료"
            }
            """;

    public static final String COUPON_DELETE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "쿠폰 삭제 완료",
              "result": "쿠폰 삭제 완료"
            }
            """;

    public static final String COUPON_INFO_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "검색 완료",
              "result": {
                "id": 1,
                "name": "신규 회원 할인 쿠폰",
                "type": "PERCENTAGE",
                "value": 10,
                "period": "2024-01-01~2024-12-31"
              }
            }
            """;

    public static final String COUPON_INFO_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "조회 성공",
              "result": [
                {
                  "id": 1,
                  "name": "신규 회원 할인 쿠폰",
                  "type": "PERCENTAGE",
                  "value": 10,
                  "period": "2024-01-01~2024-12-31"
                },
                {
                  "id": 2,
                  "name": "VIP 회원 쿠폰",
                  "type": "FIXED",
                  "value": 5000,
                  "period": "2024-01-01~2024-06-30"
                }
              ]
            }
            """;

    public static final String COUPON_INFO_LIST_PAGING_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "조회 성공",
              "result": {
                "couponInfoResList": [
                  {
                    "id": 1,
                    "name": "신규 회원 할인 쿠폰",
                    "type": "PERCENTAGE",
                    "value": 10,
                    "period": "2024-01-01~2024-12-31"
                  },
                  {
                    "id": 2,
                    "name": "VIP 회원 쿠폰",
                    "type": "FIXED",
                    "value": 5000,
                    "period": "2024-01-01~2024-06-30"
                  }
                ],
                "totalPages": 3,
                "totalElements": 45,
                "currentPage": 0,
                "pageSize": 20
              }
            }
            """;
}
