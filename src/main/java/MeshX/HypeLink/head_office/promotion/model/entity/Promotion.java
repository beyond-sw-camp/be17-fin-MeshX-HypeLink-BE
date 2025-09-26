package MeshX.HypeLink.head_office.promotion.model.entity;

import MeshX.HypeLink.head_office.item.Category;
import MeshX.HypeLink.head_office.item.Item;
import MeshX.HypeLink.head_office.store.model.entity.StoreMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String contents;
    private Double discountRate;    // 할인율

    private LocalDate startDate;    // 할인 시작 시정
    private LocalDate endDate;      // 할인 종료 시점

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PromotionType promotionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private StoreMember store;  // 특정 직영점 이벤트

    @Enumerated(EnumType.STRING)
    private Category category;  // 특정 카테고리 이벤트

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Item item;           // 특정 상품 이벤트
}
