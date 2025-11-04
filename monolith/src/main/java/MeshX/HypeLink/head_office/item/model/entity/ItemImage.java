package MeshX.HypeLink.head_office.item.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.image.model.entity.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;
    private Integer sortIndex;

    @Builder
    private ItemImage(Item item, Image image, Integer sortIndex) {
        this.item = item;
        this.image = image;
        this.sortIndex = sortIndex;
    }

    public void updateIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }
}
