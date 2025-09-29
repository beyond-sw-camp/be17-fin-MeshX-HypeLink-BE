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
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "item_type")
public abstract class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems;

    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
}
