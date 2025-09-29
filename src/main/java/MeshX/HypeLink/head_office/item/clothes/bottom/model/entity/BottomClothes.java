package MeshX.HypeLink.head_office.item.clothes.bottom.model.entity;

import MeshX.HypeLink.head_office.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("BOTTOM_CLOTHES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BottomClothes extends Item {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BottomClothesCategory category;
}
