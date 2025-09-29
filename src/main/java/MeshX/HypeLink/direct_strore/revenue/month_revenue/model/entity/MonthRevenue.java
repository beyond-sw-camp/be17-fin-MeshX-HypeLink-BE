package MeshX.HypeLink.direct_strore.revenue.month_revenue.model.entity;


import MeshX.HypeLink.direct_strore.revenue.RevenueCommon;
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
public class MonthRevenue extends RevenueCommon {
    private Integer totalRevenue;

}
