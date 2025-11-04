package MeshX.HypeLink.head_office.as.model.entity;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.common.BaseEntity;
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
@Table(name = "`as`")
public class As extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title; // AS 게시글 제목

    @Column(columnDefinition = "TEXT")
    private String description; // AS 게시글 내용

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AsStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "as")
    private List<AsComment> comments = new ArrayList<>();

    @Builder
    public As(String title, String description, AsStatus status, Store store) {
        this.title = title;
        this.description = description;
        this.status = status != null ? status : AsStatus.PENDING;
        this.store = store;
    }

    public void updateStatus(AsStatus newStatus) {
        this.status = newStatus;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateDescription(String description) {
        this.description = description;
    }
}
