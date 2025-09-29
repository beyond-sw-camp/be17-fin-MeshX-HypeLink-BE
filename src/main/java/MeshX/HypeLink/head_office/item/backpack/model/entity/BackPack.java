package MeshX.HypeLink.head_office.item.backpack.model.entity;

import MeshX.HypeLink.head_office.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("BACKPACK")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BackPack extends Item {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BackPackCategory category;

}
