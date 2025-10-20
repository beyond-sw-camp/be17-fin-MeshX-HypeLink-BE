package MeshX.HypeLink.image.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String originalFilename;

    private String savedPath;

    private String contentType;

    private long fileSize;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @Builder
    public Image(Integer id, String originalFilename, String savedPath, String contentType, long fileSize, ImageType imageType) {
        this.id = id;
        this.originalFilename = originalFilename;
        this.savedPath = savedPath;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.imageType = imageType;
    }
}
