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
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String contents;
    private String author;
    private Boolean isOpen;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeImages> noticeImages = new ArrayList<>();

    @Builder
    private Notice(String title, String contents, Boolean isOpen, String author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.isOpen = isOpen;
    }

    //== Update Methods ==//
    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }

    public void changeOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void updateAuthor(String author) {
        this.author = author;
    }

    //== Relationship Management Methods ==//
    public void clearImages() {
        this.noticeImages.clear();
    }

    public void addNoticeImage(NoticeImages noticeImage) {
        this.noticeImages.add(noticeImage);
    }

    //== Helper Method for DTO ==//
    public List<Image> getImages() {
        return this.noticeImages.stream()
                .map(NoticeImages::getImage)
                .collect(Collectors.toList());
    }
}