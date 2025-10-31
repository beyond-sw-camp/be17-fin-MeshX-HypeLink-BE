package MeshX.HypeLink.head_office.shipment.constants;

public class ShipmentSwaggerConstants {

    public static final String SHIPMENT_ASSIGNMENT_REQ_EXAMPLE = """
            {
              "driverId": 1,
              "parcelIds": [1, 2, 3]
            }
            """;

    public static final String SHIPMENT_INFO_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "조회 성공",
              "result": [
                {
                  "id": 1,
                  "trackingNumber": "TRACK001",
                  "status": "PENDING",
                  "storeName": "강남 직영점",
                  "driverName": null
                },
                {
                  "id": 2,
                  "trackingNumber": "TRACK002",
                  "status": "ASSIGNED",
                  "storeName": "홍대 직영점",
                  "driverName": "홍길동"
                }
              ]
            }
            """;
}
