package MeshX.HypeLink.direct_strore.item.clothes.top.model.entity;

import MeshX.HypeLink.direct_strore.item.DirectItem;
import MeshX.HypeLink.direct_strore.item.clothes.outer.model.entity.OuterClothesCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("TOP_CLOTHES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectTopClothes extends DirectItem {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TopClothesCategory category;

    private Boolean longSleeve;     // 긴팔 여부
    private String neckline;        // 라운드넥, 브이넥 등
    private String size;
    private String gender;
    private String season;

    @Builder
    public DirectTopClothes(TopClothesCategory category, Boolean longSleeve, String neckline, Integer amount, String name, String content, String company, String itemCode, Integer stock, String gender, String season, String size) {
        super(amount, name, content, company, itemCode, stock);
        this.category = category;
        this.longSleeve = longSleeve;
        this.neckline = neckline;
        this.size = size;
        this.gender = gender;
        this.season = season;
    }

    public void updateCategory(TopClothesCategory category){
        this.category = category;
    }

    public void updateLongSleeve(Boolean longSleeve){
        this.longSleeve = longSleeve;
    }

    public void updateNeckLine(String neckline){
        this.neckline = neckline;
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
