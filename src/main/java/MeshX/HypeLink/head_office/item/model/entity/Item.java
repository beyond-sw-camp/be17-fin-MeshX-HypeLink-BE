package MeshX.HypeLink.head_office.item.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.head_office.customer.model.entity.OrderItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    private Integer amount; // 가격
    private String name; // 이름
    private String content; // 아이템 설명
    private String company; // 회사
    private String itemCode; // 아이템 코드
    private Integer stock; // 재고

    @Builder
    private Item(Category category, Color color, Size size, Integer amount,
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
