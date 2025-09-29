package MeshX.HypeLink.direct_strore.revenue.day_revenue.model.entity;

import MeshX.HypeLink.common.BaseEntity;
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
public class DayRevenue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    private LocalDate salesDate;
    private Integer totalRevenue;
    //품복별 단가 * 수량 = 금액해서 전부 합계가 하루 매출액


    @ManyToOne//(fetch = FetchType.Lazy)
    @JoinColumn(name = "store_idx")
    private StoreMember storeDayRevenue;



}
