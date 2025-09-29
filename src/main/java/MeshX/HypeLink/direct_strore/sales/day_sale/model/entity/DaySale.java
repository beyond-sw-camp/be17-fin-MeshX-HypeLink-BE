package MeshX.HypeLink.direct_strore.sales.day_sale.model.entity;

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
public class DaySale extends SaleCommon {
    private Integer quantity;

}
