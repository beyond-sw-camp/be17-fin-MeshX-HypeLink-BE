package MeshX.HypeLink.head_office.item.clothes.bottom.model.entity;

import MeshX.HypeLink.head_office.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("BOTTOM_CLOTHES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BottomClothes extends Item {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BottomClothesCategory category;

    private Integer waist;          // 허리 사이즈
    private Integer length;         // 바지 길이
    private String size;
    private String gender;
    private String season;

    @Builder
    public BottomClothes(BottomClothesCategory category, Integer waist, Integer length, Integer amount, String name, String content, String company, String itemCode, Integer stock, String gender, String season, String size) {
        super(amount, name, content, company, itemCode, stock);
        this.category = category;
        this.waist = waist;
        this.length = length;
        this.size = size;
        this.gender = gender;
        this.season = season;
    }

    public void updateCategory(BottomClothesCategory category){
        this.category = category;
    }

    public void updateWaist(Integer waist){
        this.waist = waist;
    }

    public void updateLength(Integer length){
        this.length = length;
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
