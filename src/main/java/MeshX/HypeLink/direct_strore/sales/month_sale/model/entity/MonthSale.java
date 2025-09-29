package MeshX.HypeLink.direct_strore.sales.month_sale.model.entity;

import MeshX.HypeLink.head_office.store.model.entity.StoreMember;
import jakarta.persistence.*;
import org.apache.catalina.StoreManager;

import java.time.LocalDate;

public class MonthSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    private LocalDate salesDate;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private StoreMember storeMonthSale;
}
