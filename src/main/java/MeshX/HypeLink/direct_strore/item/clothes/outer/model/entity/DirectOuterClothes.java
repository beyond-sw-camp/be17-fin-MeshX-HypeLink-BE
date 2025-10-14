package MeshX.HypeLink.direct_strore.item.clothes.outer.model.entity;

import MeshX.HypeLink.direct_strore.item.DirectItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("OUTER_CLOTHES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectOuterClothes extends DirectItem {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OuterClothesCategory category;

    private Boolean hooded;         // 후드 여부
    private Boolean waterproof;     // 방수 기능 여부
    private String size;
    private String gender;
    private String season;

    @Builder
    public DirectOuterClothes(OuterClothesCategory category, Boolean hooded, Boolean waterproof, Integer amount, String name, String content, String company, String itemCode, Integer stock, String gender, String season, String size) {
        super(amount, name, content, company, itemCode, stock);
        this.category = category;
        this.hooded = hooded;
        this.waterproof = waterproof;
        this.size = size;
        this.gender = gender;
        this.season = season;
    }

    public void updateCategory(OuterClothesCategory category){
        this.category = category;
    }

    public void updateHooded(Boolean hooded){
        this.hooded = hooded;
    }

    public void updateWaterproof(Boolean waterproof){
        this.waterproof = waterproof;
    }

    public void updateSize(String size){
        this.size = size;
    }

    public void updateGender(String gender){
        this.gender = gender;
    }

    public void updateSeason(String season){
        this.season = season;
    }

}
