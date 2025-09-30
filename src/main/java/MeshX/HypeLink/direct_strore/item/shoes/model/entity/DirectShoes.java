package MeshX.HypeLink.direct_strore.item.shoes.model.entity;

import MeshX.HypeLink.direct_strore.item.DirectItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("CLOTHES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectShoes extends DirectItem {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ShoesCategory category;
}
