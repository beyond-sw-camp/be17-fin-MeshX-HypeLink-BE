package MeshX.HypeLink.direct_strore.sales.month_sale.model.entity;

import MeshX.HypeLink.direct_strore.sales.SaleCommon;
import MeshX.HypeLink.head_office.store.model.entity.StoreMember;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.StoreManager;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class MonthSale extends SaleCommon {
    private Integer quantity;

}
