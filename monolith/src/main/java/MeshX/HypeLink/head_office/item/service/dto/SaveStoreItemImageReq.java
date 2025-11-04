package MeshX.HypeLink.head_office.item.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveStoreItemImageReq {
    private String originalFilename;
    private String savedPath;
    private String contentType;
    private long fileSize;
    private Integer sortIndex;
}
