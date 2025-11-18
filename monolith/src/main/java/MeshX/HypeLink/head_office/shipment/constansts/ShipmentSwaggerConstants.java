package MeshX.HypeLink.head_office.shipment.constansts;

public class ShipmentSwaggerConstants {

    // ShipmentController Examples
    public static final String SHIPMENT_ASSIGNMENT_REQ_EXAMPLE = """
            {
              "parcelId": 1,
              "driverId": 10,
              "assignedAt": "2025-01-15T10:00:00"
            }
            """;

    public static final String SHIPMENT_ASSIGNMENT_SUCCESS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "성공적으로 배정 되었습니다.",
              "result": "성공적으로 배정 되었습니다."
            }
            """;

    public static final String SHIPMENT_INFO_LIST_RES_EXAMPLE = """
            [
              {
                "parcelId": 1,
                "trackingNumber": "TRACK001",
                "status": "PENDING",
                "fromAddress": "Seoul",
                "toAddress": "Busan",
                "driverId": null,
                "driverName": null
              },
              {
                "parcelId": 2,
                "trackingNumber": "TRACK002",
                "status": "ASSIGNED",
                "fromAddress": "Seoul",
                "toAddress": "Daegu",
                "driverId": 10,
                "driverName": "Driver Kim"
              }
            ]
            """;

    public static final String HAS_ACTIVE_SHIPMENTS_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "success",
              "result": true
            }
            """;
}
