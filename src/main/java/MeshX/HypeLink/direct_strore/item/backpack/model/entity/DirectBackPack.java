package MeshX.HypeLink.direct_strore.item.backpack.model.entity;

import MeshX.HypeLink.direct_strore.item.DirectItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("BACKPACK")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectBackPack extends DirectItem {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BackPackCategory category;

}
