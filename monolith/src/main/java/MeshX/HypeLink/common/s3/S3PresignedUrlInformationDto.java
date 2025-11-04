package MeshX.HypeLink.common.s3;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class S3PresignedUrlInformationDto {
    private String uploadUrl;
    private Integer expiresIn;


}