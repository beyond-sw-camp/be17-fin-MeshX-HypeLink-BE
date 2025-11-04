package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.image.model.dto.response.ImageUploadResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ItemImageInfoRes extends ImageUploadResponse {
    private Integer index;
    private String originalFilename;
}
