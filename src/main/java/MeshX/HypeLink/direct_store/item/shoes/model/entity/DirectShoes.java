package MeshX.HypeLink.direct_store.item.shoes.model.entity;

import MeshX.HypeLink.direct_store.item.DirectItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("CLOTHES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectShoes extends DirectItem {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ShoesCategory category;

    private Integer size; //사이즈
    private Boolean waterproof; // 방수여부

    @Builder
    public DirectShoes(ShoesCategory category, Integer size, Boolean waterproof, Integer amount, String name, String content, String company, String itemCode, Integer stock){
        super(amount, name, content, company, itemCode, stock);
        this.category = category;
        this.size = size;
        this.waterproof = waterproof;
    }


    public void updateCategory(ShoesCategory category){
        this.category = category;
    }

    public void updateSize(Integer size){
       this.size = size;
    }

    public void updateWaterproof(Boolean waterproof){
        this.waterproof = waterproof;
    }

}
