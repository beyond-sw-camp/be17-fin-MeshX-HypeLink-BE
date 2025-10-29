package MeshX.HypeLink.head_office.promotion.constants;

public class PromotionSwaggerConstants {

    public static final String PROMOTION_CREATE_REQ_EXAMPLE = """
            {
              "title": "프로모션 제목",
              "contents": "프로모션 내용",
              "startDate": "2024-01-01",
              "endDate": "2024-12-31",
              "status": "ACTIVE",
              "couponId": 1
            }
            """;

    public static final String PROMOTION_INFO_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "조회 성공",
              "result": {
                "id": 1,
                "title": "프로모션 제목",
                "contents": "프로모션 내용",
                "startDate": "2024-01-01",
                "endDate": "2024-12-31",
                "status": "ACTIVE",
                "couponId": 1
              }
            }
            """;
}
