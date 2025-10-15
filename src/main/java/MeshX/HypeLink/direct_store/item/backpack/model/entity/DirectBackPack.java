package MeshX.HypeLink.direct_store.item.backpack.model.entity;

import MeshX.HypeLink.direct_store.item.DirectItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("BACKPACK")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectBackPack extends DirectItem {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BackPackCategory category;

    private Integer capacity; //용량
    private Boolean waterproof; // 방수여부

    @Builder
    public DirectBackPack(BackPackCategory category, Integer capacity, Boolean waterproof, Integer amount, String name, String content, String company, String itemCode, Integer stock){
        super(amount, name, content, company, itemCode, stock);
        this.category = category;
        this.capacity = capacity;
        this.waterproof = waterproof;
    }


    public void updateCategory(BackPackCategory category){
        this.category = category;
    }

    public void updateCapacity(Integer capacity){
        this.capacity = capacity;
    }

    public void updateWaterproof(Boolean waterproof){
        this.waterproof = waterproof;
    }

}
