package MeshX.HypeLink.direct_store.item.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreItemImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String originalFilename;
    private String savedPath;
    private String contentType;
    private long fileSize;
    private Integer sortIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private StoreItem item;

    @Builder
    private StoreItemImage(String originalFilename, String savedPath, String contentType, long fileSize,
                           Integer sortIndex, StoreItem item) {
        this.originalFilename = originalFilename;
        this.savedPath = savedPath;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.sortIndex = sortIndex;
        this.item = item;
    }

    @Transient
    public String getCompositeKey() {
        if (item == null || item.getStore() == null) return null;
        return item.getStore().getId() + "-" + originalFilename;
    }

    public void updateIndex(Integer index) {
        this.sortIndex = index;
    }
}
