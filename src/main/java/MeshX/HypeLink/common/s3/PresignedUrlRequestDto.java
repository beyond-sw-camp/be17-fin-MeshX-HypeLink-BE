package MeshX.HypeLink.common.s3;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PresignedUrlRequestDto {
    String originalFilename;
    Long fileSize;
    String contentType;
}
