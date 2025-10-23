package MeshX.HypeLink.direct_store.item.model.dto.request;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemImage;
import lombok.Getter;

@Getter
public class SaveStoreItemImageReq {
    private String originalFilename;
    private String savedPath;
    private String contentType;
    private long fileSize;
    private Integer sortIndex;

    public StoreItemImage toEntity(StoreItem item) {
        return StoreItemImage.builder()
                .originalFilename(originalFilename)
                .savedPath(savedPath)
                .contentType(contentType)
                .fileSize(fileSize)
                .sortIndex(sortIndex)
                .item(item)
                .build();
    }
}
