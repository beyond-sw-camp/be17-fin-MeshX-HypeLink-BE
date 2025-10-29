package MeshX.HypeLink.image.constants;

public class ImageSwaggerConstants {

    public static final String PRESIGNED_URL_REQ_EXAMPLE = """
            {
              "fileName": "example.jpg",
              "fileType": "image/jpeg"
            }
            """;

    public static final String PRESIGNED_URL_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "성공",
              "result": {
                "presignedUrl": "https://s3.amazonaws.com/bucket/path/to/file?signature=...",
                "fileUrl": "https://s3.amazonaws.com/bucket/path/to/file",
                "expiresIn": 3600
              }
            }
            """;
}
