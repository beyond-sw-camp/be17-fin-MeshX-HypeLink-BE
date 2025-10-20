package MeshX.HypeLink.head_office.notice.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.image.model.entity.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String contents;
    private Boolean isOpen;

    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();

    @Builder
    private Notice(String title, String contents, Boolean isOpen) {
        this.title = title;
        this.contents = contents;
        this.isOpen = isOpen;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }

    public void changeOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void addImage(Image image) {
        this.imageList.add(image);
        image.setNotice(this);
    }

    public void clearImages() {
        this.imageList.clear();
    }
}
