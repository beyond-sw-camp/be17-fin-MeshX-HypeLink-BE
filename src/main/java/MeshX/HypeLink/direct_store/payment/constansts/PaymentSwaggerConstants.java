package MeshX.HypeLink.direct_store.payment.constansts;

public class PaymentSwaggerConstants {

    public static final String PAYMENT_VALIDATION_REQ_EXAMPLE = """
            {
              "orderId": "order_12345",
              "amount": 15000,
              "paymentKey": "payment_key_abcde"
            }
            """;

    public static final String PAYMENT_VALIDATION_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "검증 성공",
              "result": "검증 성공"
            }
            """;
}
