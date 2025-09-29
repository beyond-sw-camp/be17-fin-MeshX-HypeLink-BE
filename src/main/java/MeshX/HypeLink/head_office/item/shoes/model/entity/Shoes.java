package MeshX.HypeLink.head_office.item.shoes.model.entity;

import MeshX.HypeLink.head_office.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("CLOTHES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shoes extends Item {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ShoesCategory category;
}
