package MeshX.HypeLink.head_office.sales.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.head_office.item.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategorySalesSummary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private LocalDate summaryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SummaryType summaryType; // DAILY, MONTHLY

    @Column(nullable = false)
    private Long salesCount;

    @Column(nullable = false)
    private Long salesAmount;
}
