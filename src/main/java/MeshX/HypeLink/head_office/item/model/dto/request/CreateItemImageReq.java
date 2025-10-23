package MeshX.HypeLink.head_office.item.model.dto.request;

import lombok.Getter;

@Getter
public class CreateItemImageReq {
    private String originalFilename;
    private Long fileSize;
    private String contentType;
    private String s3Key;
    private Integer index;
}
