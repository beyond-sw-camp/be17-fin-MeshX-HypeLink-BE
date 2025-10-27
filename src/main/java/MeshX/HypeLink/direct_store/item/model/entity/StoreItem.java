package MeshX.HypeLink.direct_store.item.model.entity;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private StoreCategory category;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    // OrderItem과의 관계 제거 - OrderItem은 이제 StoreItemDetail과 연결됨

//    @Column(unique = true, nullable = false)
    private String itemCode; // 아이템 코드
    private Integer unitPrice;       // 단가
    private Integer amount; // 가격
    private String enName; // 이름
    private String koName; // 이름
    private String content; // 아이템 설명
    private String company; // 회사

    @Builder
    private StoreItem(StoreCategory category, Store store, String itemCode,
                      Integer unitPrice, Integer amount, String enName, String koName,
                      String content, String company) {
        this.category = category;
        this.store = store;
        this.itemCode = itemCode;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.enName = enName;
        this.koName = koName;
        this.content = content;
        this.company = company;
    }

    public void updateEnName(String enName) {
        this.enName = enName;
    }

    public void updateKoName(String koName) {
        this.koName = koName;
    }

    public void updateUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void updateCategory(StoreCategory category) {
        this.category = category;
    }

    public void updateAmount(Integer amount){
        this.amount = amount;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public void updateCompany(String company){
        this.company = company;
    }
}
