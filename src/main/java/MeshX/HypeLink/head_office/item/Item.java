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

    //@OneToMany(mappedBy = "item")
    //private List<OrderItem> orderItems;

    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;

    protected Item(Integer amount, String name, String content, String company, String itemCode, Integer stock){
        this.amount = amount;
        this.name = name;
        this.content = content;
        this.company = company;
        this.itemCode = itemCode;
        this.stock = stock;
    }

    protected void setCommonFields(Integer amount, String name, String content, String company, String itemCode, Integer stock){
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
