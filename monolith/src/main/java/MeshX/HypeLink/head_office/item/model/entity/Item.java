package MeshX.HypeLink.head_office.item.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<ItemDetail> itemDetails;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    @BatchSize(size = 40)
    private List<ItemImage> itemImages;

    @Column(unique = true, nullable = false)
    private String itemCode; // 아이템 코드
    private Integer unitPrice;       // 단가
    private Integer amount; // 가격
    private String enName; // 이름
    private String koName; // 이름
    private String content; // 아이템 설명
    private String company; // 회사

    @Builder
    private Item(Category category, List<ItemDetail> itemDetails, List<ItemImage> itemImages, Integer unitPrice,
                 Integer amount, String enName, String koName, String content, String company,
                 String itemCode) {
        this.category = category;
        this.itemDetails = itemDetails;
        this.itemImages = itemImages;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.enName = enName;
        this.koName = koName;
        this.content = content;
        this.company = company;
        this.itemCode = itemCode;
    }

    public void updateEnName(String enName) {
        this.enName = enName;
    }

    public void updateKoName(String koName) {
        this.koName = koName;
    }

    public void updateAmount(Integer amount){
        this.amount = amount;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public void updateUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void updateCompany(String company){
        this.company = company;
    }

    public void updateCategory(Category category){
        this.category = category;
    }
}
