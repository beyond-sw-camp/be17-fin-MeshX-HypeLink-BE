package MeshX.HypeLink.head_office.coupon.constansts;

public class CouponSwaggerConstants {

    // CouponController Examples
    public static final String COUPON_CREATE_REQ_EXAMPLE = """
            {
              "name": "신규 가입 쿠폰",
              "description": "신규 가입 고객에게 제공되는 할인 쿠폰",
              "discountType": "PERCENTAGE",
              "discountValue": 10,
              "minPurchaseAmount": 50000,
              "maxDiscountAmount": 10000,
              "validFrom": "2025-01-01T00:00:00",
              "validUntil": "2025-12-31T23:59:59"
            }
            """;

    public static final String COUPON_UPDATE_REQ_EXAMPLE = """
            {
              "name": "수정된 쿠폰명",
              "description": "수정된 설명",
              "discountValue": 15,
              "minPurchaseAmount": 30000
            }
            """;

    public static final String COUPON_INFO_RES_EXAMPLE = """
            {
              "id": 1,
              "name": "신규 가입 쿠폰",
              "description": "신규 가입 고객에게 제공되는 할인 쿠폰",
              "discountType": "PERCENTAGE",
              "discountValue": 10,
              "minPurchaseAmount": 50000,
              "maxDiscountAmount": 10000,
              "validFrom": "2025-01-01T00:00:00",
              "validUntil": "2025-12-31T23:59:59",
              "isActive": true
            }
            """;

    public static final String COUPON_INFO_LIST_RES_EXAMPLE = """
            {
              "coupons": [
                {
                  "id": 1,
                  "name": "신규 가입 쿠폰",
                  "discountType": "PERCENTAGE",
                  "discountValue": 10,
                  "isActive": true
                },
                {
                  "id": 2,
                  "name": "VIP 쿠폰",
                  "discountType": "FIXED_AMOUNT",
                  "discountValue": 5000,
                  "isActive": true
                }
              ],
              "totalPages": 5,
              "totalElements": 50,
              "size": 10,
              "number": 0
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

    public static final String COUPON_UPDATE_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "쿠폰 수정 완료",
              "result": {
                "id": 1,
                "name": "수정된 쿠폰명",
                "discountValue": 15
              }
            }
            """;
}
