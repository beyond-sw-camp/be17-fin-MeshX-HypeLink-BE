package MeshX.HypeLink.image.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.head_office.notice.model.entity.Notice;
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

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @Builder
    public Image(Integer id, String originalFilename, String savedPath, String contentType, long fileSize) {
        this.id = id;
        this.originalFilename = originalFilename;
        this.savedPath = savedPath;
        this.contentType = contentType;
        this.fileSize = fileSize;
    }
    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
