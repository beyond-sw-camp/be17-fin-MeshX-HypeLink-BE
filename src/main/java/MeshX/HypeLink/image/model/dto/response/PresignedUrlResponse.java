package MeshX.HypeLink.image.model.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PresignedUrlResponse {

    private String uploadUrl;
    private String s3Key;
    private Integer expiresIn;

    @Builder
    private PresignedUrlResponse(String uploadUrl, String s3Key, Integer expiresIn) {
        this.uploadUrl = uploadUrl;
        this.s3Key = s3Key;
        this.expiresIn = expiresIn;
    }
}
