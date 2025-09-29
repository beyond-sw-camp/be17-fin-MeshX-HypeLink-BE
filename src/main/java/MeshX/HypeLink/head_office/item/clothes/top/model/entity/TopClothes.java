package MeshX.HypeLink.head_office.item.clothes.top.model.entity;

import MeshX.HypeLink.head_office.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("TOP_CLOTHES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopClothes extends Item {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TopClothesCategory category;
}
