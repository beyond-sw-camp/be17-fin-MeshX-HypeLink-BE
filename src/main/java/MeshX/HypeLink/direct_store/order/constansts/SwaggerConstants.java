package MeshX.HypeLink.direct_store.order.constansts;

public class SwaggerConstants {
    public static final String DIRECT_ORDER_CREATE_REQUEST = """
            {
              "itemName": "Hype-Tee Bulk",
              "unitPrice": 25000,
              "quantity": 100,
              "deliveryAddress": "HypeLink Gangnam Store",
              "deliveryRequest": "Please deliver by Friday.",
              "orderRequest": "Need 50 L size and 50 M size."
            }
            """;

    public static final String DIRECT_ORDER_UPDATE_REQUEST = """
            {
              "itemName": "Hype-Tee Bulk",
              "unitPrice": 25000,
              "quantity": 120,
              "totalPrice": 3000000,
              "deliveryAddress": "HypeLink Gangnam Store",
              "deliveryRequest": "Urgent delivery needed.",
              "orderRequest": "Updated quantity to 120."
            }
            """;

    public static final String DIRECT_ORDER_INFO_RESPONSE = """
            {
              "itemName": "Hype-Tee Bulk",
              "unitPrice": 25000,
              "quantity": 100,
              "totalPrice": 2500000,
              "deliveryAddress": "HypeLink Gangnam Store",
              "deliveryRequest": "Please deliver by Friday.",
              "orderRequest": "Need 50 L size and 50 M size."
            }
            """;

    public static final String DIRECT_ORDER_INFO_LIST_RESPONSE = """
            {
              "directOrders": [
                {
                  "itemName": "Hype-Tee Bulk",
                  "unitPrice": 25000,
                  "quantity": 100,
                  "totalPrice": 2500000,
                  "deliveryAddress": "HypeLink Gangnam Store",
                  "deliveryRequest": "Please deliver by Friday.",
                  "orderRequest": "Need 50 L size and 50 M size."
                },
                {
                  "itemName": "Link-Pants Bulk",
                  "unitPrice": 40000,
                  "quantity": 50,
                  "totalPrice": 2000000,
                  "deliveryAddress": "HypeLink Hongdae Store",
                  "deliveryRequest": "Standard delivery.",
                  "orderRequest": ""
                }
              ]
            }
            """;
}
