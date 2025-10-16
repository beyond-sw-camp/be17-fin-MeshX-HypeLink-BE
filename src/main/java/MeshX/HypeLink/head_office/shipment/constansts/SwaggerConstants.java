package MeshX.HypeLink.head_office.shipment.constansts;

public class SwaggerConstants {
    public static final String CREATE_PARCEL_REQUEST = """
            {
              "items": [
                { "id": 1 },
                { "id": 2 }
              ],
              "trackingNumber": "HN123456789KR"
            }
            """;

    public static final String PARCEL_RESPONSE = """
            {
              "id": 1,
              "trackingNumber": "HN123456789KR",
              "parcelItems": [
                { "id": 1, "item": { "id": 101, "name": "Hype-Tee" } },
                { "id": 2, "item": { "id": 202, "name": "Link-Pants" } }
              ],
              "shipment": null,
              "createdAt": "2025-10-16T10:00:00Z",
              "updatedAt": "2025-10-16T10:00:00Z"
            }
            """;

    public static final String CONNECTING_REQUEST = """
            {
              "trackingNumber": ["HN123456789KR"],
              "driver": { "id": 1 },
              "toStore": { "id": 2 },
              "fromStore": { "id": 1 }
            }
            """;

    public static final String SHIPMENT_RESPONSE = """
            {
              "id": 1,
              "parcels": [
                {
                  "id": 1,
                  "trackingNumber": "HN123456789KR",
                  "parcelItems": [
                    { "id": 1, "item": { "id": 101, "name": "Hype-Tee" } }
                  ]
                }
              ],
              "toStore": {
                "id": 2,
                "address": "Seoul, Gangnam-gu",
                "storeNumber": "S002"
              },
              "fromStore": {
                "id": 1,
                "address": "Seoul, Jung-gu",
                "storeNumber": "S001"
              },
              "driver": {
                "id": 1,
                "name": "John Driver",
                "carNumber": "123가4567"
              },
              "shipmentStatus": "IN_PROGRESS",
              "createdAt": "2025-10-16T11:00:00Z",
              "updatedAt": "2025-10-16T11:00:00Z"
            }
            """;
}
