package MeshX.HypeLink.direct_strore.sales.ages_sale.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.direct_strore.sales.SaleCommon;
import MeshX.HypeLink.head_office.store.model.entity.StoreMember;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class AgesSale extends SaleCommon {
    @Enumerated(EnumType.STRING)
    private AgeGroup age;

    private Integer quantity;

}
