package MeshX.HypeLink.head_office.notice.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
