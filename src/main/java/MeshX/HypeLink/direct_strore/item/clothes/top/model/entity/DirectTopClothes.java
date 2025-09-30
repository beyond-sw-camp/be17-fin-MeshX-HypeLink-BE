package MeshX.HypeLink.direct_strore.item.clothes.top.model.entity;

import MeshX.HypeLink.direct_strore.item.DirectItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("TOP_CLOTHES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectTopClothes extends DirectItem {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TopClothesCategory category;
}
