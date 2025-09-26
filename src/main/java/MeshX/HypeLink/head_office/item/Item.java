package MeshX.HypeLink.head_office.item;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.head_office.member.model.entity.OrderItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems;

    private Integer amount;
    private String name;
    private String content;
    private String company;
}
