package MeshX.HypeLink.head_office.item.clothes.outer.model.entity;

import MeshX.HypeLink.head_office.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("OUTER_CLOTHES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OuterClothes extends Item {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OuterClothesCategory category;
}
