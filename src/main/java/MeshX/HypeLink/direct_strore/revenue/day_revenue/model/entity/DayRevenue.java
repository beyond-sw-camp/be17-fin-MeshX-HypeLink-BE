package MeshX.HypeLink.direct_strore.revenue.day_revenue.model.entity;

import MeshX.HypeLink.common.BaseEntity;
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
public class DayRevenue extends RevenueCommon {
    private Integer totalRevenue;
    //품복별 단가 * 수량 = 금액해서 전부 합계가 하루 매출액


}
