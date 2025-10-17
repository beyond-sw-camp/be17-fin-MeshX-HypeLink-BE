package MeshX.HypeLink.head_office.item.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemInfoRes {
    private Integer id;
    private Integer amount; // 가격
    private String name; // 이름
    private String company; // 회사
    private String itemCode; // 아이템 코드

    @Builder
    private ItemInfoRes(Integer id, Integer amount, String name, String company, String itemCode) {
        this.id = id;
        this.amount = amount;
        this.name = name;
        this.company = company;
        this.itemCode = itemCode;
    }
}
