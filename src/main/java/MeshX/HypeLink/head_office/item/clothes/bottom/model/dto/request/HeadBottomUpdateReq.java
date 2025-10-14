package MeshX.HypeLink.head_office.item.clothes.bottom.model.dto.request;


import MeshX.HypeLink.head_office.item.clothes.bottom.model.entity.BottomClothesCategory;
import lombok.Getter;

@Getter
public class HeadBottomUpdateReq {
    private BottomClothesCategory category;
    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;
    private Integer waist;          // 허리 사이즈
    private Integer length;         // 바지 길이
    private String size;
    private String gender;
    private String season;
}
