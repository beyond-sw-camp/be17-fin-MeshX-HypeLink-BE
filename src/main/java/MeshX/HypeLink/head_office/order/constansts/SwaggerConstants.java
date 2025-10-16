package MeshX.HypeLink.head_office.order.constansts;

public class SwaggerConstants {
    public static final String HEAD_ORDER_CREATE_REQUEST = """
            {
              "itemName": "Raw Fabric Rolls",
              "unitPrice": 500000,
              "quantity": 10,
              "deliveryAddress": "HypeLink Main Warehouse",
              "deliveryRequest": "Deliver to loading bay 3.",
              "orderRequest": "Need certificate of origin."
            }
            """;

    public static final String HEAD_ORDER_UPDATE_REQUEST = """
            {
              "itemName": "Raw Fabric Rolls",
              "unitPrice": 500000,
              "quantity": 12,
              "totalPrice": 6000000,
              "deliveryAddress": "HypeLink Main Warehouse",
              "deliveryRequest": "Urgent: Deliver to loading bay 1.",
              "orderRequest": "Need certificate of origin."
            }
            """;

    public static final String HEAD_ORDER_INFO_RESPONSE = """
            {
              "itemName": "Raw Fabric Rolls",
              "unitPrice": 500000,
              "quantity": 10,
              "totalPrice": 5000000,
              "deliveryAddress": "HypeLink Main Warehouse",
              "deliveryRequest": "Deliver to loading bay 3.",
              "orderRequest": "Need certificate of origin."
            }
            """;

    public static final String HEAD_ORDER_INFO_LIST_RESPONSE = """
            {
              "headOrders": [
                {
                  "itemName": "Raw Fabric Rolls",
                  "unitPrice": 500000,
                  "quantity": 10,
                  "totalPrice": 5000000,
                  "deliveryAddress": "HypeLink Main Warehouse",
                  "deliveryRequest": "Deliver to loading bay 3.",
                  "orderRequest": "Need certificate of origin."
                },
                {
                  "itemName": "Zippers and Buttons",
                  "unitPrice": 10000,
                  "quantity": 500,
                  "totalPrice": 5000000,
                  "deliveryAddress": "HypeLink Main Warehouse",
                  "deliveryRequest": "Standard delivery.",
                  "orderRequest": ""
                }
              ]
            }
            """;
}
