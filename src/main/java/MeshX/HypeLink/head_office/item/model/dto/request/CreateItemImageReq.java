package MeshX.HypeLink.head_office.item.model.dto.request;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.model.entity.ItemImage;
import MeshX.HypeLink.image.model.entity.Image;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class CreateItemImageReq {
    private Long id;  // ← 이미지 ID 추가
    private String originalFilename;
    private Long fileSize;
    private String contentType;
    private String s3Key;
    private Integer index;

    public Image toEntity() {
        return Image.builder()
                .contentType(contentType)
                .fileSize(fileSize)
                .originalFilename(originalFilename)
                .savedPath(s3Key)
                .build();
    }

    public ItemImage toItemImage(Item item, Image image) {
        return ItemImage.builder()
                .item(item)
                .image(image)
                .sortIndex(index)
                .build();
    }
}
