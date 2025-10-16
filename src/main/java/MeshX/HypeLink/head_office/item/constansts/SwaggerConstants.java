package MeshX.HypeLink.head_office.item.constansts;

public class SwaggerConstants {
    public static final String CREATE_ITEM_REQUEST = """
            {
              "name": "Hype-Tee",
              "amount": 35000,
              "category": "T-SHIRT",
              "content": "A very hyped T-shirt.",
              "company": "HypeLink Originals",
              "itemDetailList": [
                {
                  "size": "L",
                  "color": "Black",
                  "stock": 100,
                  "itemCode": "HT-L-BLK"
                },
                {
                  "size": "M",
                  "color": "White",
                  "stock": 150,
                  "itemCode": "HT-M-WHT"
                }
              ]
            }
            """;

    public static final String ITEM_DETAIL_INFO_RESPONSE = """
            {
              "id": 1,
              "name": "Hype-Tee",
              "category": "T-SHIRT",
              "itemStockList": [
                {
                  "color": "Black",
                  "size": "L",
                  "stock": 99
                },
                {
                  "color": "White",
                  "size": "M",
                  "stock": 150
                }
              ],
              "amount": 35000,
              "content": "A very hyped T-shirt.",
              "company": "HypeLink Originals",
              "itemCode": "HT-L-BLK"
            }
            """;
            
    public static final String UPDATE_ITEM_CONTENT_REQUEST = """
            {
                "itemCode": "HT-L-BLK",
                "content": "An even more hyped T-shirt with new description."
            }
            """;

    public static final String UPDATE_ITEM_STOCK_REQUEST = """
            {
                "itemCode": "HT-L-BLK",
                "stock": 50
            }
            """;
            
    public static final String ITEM_UPDATE_INFO_RESPONSE = """
            {
                "id": 1,
                "amount": 35000,
                "name": "Hype-Tee",
                "company": "HypeLink Originals",
                "itemCode": "HT-L-BLK",
                "stock": "50",
                "size": "L",
                "color": "Black"
            }
            """;

    public static final String ITEM_INFO_LIST_RESPONSE = """
            {
                "itemInfoList": [
                    {
                        "id": 1,
                        "name": "Hype-Tee",
                        "category": "T-SHIRT",
                        "amount": 35000,
                        "company": "HypeLink Originals",
                        "itemCode": "HT-L-BLK"
                    },
                    {
                        "id": 2,
                        "name": "Link-Pants",
                        "category": "PANTS",
                        "amount": 55000,
                        "company": "HypeLink Originals",
                        "itemCode": "LP-M-BLK"
                    }
                ]
            }
            """;
}
