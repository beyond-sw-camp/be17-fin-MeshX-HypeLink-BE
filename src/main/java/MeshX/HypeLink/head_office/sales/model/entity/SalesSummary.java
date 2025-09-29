package MeshX.HypeLink.head_office.sales.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesSummary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate summaryDate; // 하루/한달 기준일

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SummaryType summaryType; // DAILY, MONTHLY

    @Column(nullable = false)
    private Long totalSalesCount;

    @Column(nullable = false)
    private Long totalSalesAmount;
}
