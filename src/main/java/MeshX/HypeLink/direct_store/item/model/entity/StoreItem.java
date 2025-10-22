package MeshX.HypeLink.direct_store.item.model.entity;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.head_office.customer.model.entity.OrderItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class StoreItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String category;
    private String color;
    private String size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "storeItem", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    private Integer amount; // 가격
    private String name; // 이름
    private String content; // 아이템 설명
    private String company; // 회사
    private String itemCode; // 아이템 코드
    private Integer stock; // 재고

    @Builder
    private StoreItem(String category, String color, String size, Integer amount,
                      String name, String content, String company, String itemCode,
                      Integer stock) {
        this.category = category;
        this.color = color;
        this.size = size;
        this.amount = amount;
        this.name = name;
        this.content = content;
        this.company = company;
        this.itemCode = itemCode;
        this.stock = stock;
    }

    public void updateAmount(Integer amount){
        this.amount = amount;
    }
    public void updateName(String name){
        this.name = name;
    }
    public void updateContent(String content){
        this.content = content;
    }
    public void updateCompany(String company){
        this.company = company;
    }
    public void updateItemCode(String itemCode){
        this.itemCode = itemCode;
    }
    public void updateStock(Integer stock){
        this.stock = stock;
    }
}
