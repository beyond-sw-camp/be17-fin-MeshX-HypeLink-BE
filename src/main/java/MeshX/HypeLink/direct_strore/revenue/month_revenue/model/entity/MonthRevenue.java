package MeshX.HypeLink.direct_strore.revenue.month_revenue.model.entity;


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
public class MonthRevenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    private LocalDate salesDate;
    private Integer totalRevenue;

    @ManyToOne//(fetch = FetchType.Lazy)
    @JoinColumn(name = "store_idx")
    private StoreMember storeMonthRevenue;

}
