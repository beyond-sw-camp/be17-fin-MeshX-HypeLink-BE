package MeshX.HypeLink.head_office.notice.model.entity;

import MeshX.HypeLink.image.model.entity.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @Builder
    private NoticeImages(Image image, Notice notice) {
        this.image = image;
        this.notice = notice;
    }

    public static NoticeImages of(Notice notice, Image image) {
        return NoticeImages.builder()
                .notice(notice)
                .image(image)
                .build();
    }
}