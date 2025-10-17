package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemSearchRes {
    private Integer id;
    private Integer amount; // 가격
    private String enName; // 이름
    private String koName; // 이름
    private String company; // 회사
    private String itemCode; // 아이템 코드

    public static ItemSearchRes toDto(Item item) {
        return ItemSearchRes.builder()
                .id(item.getId())
                .amount(item.getAmount())
                .enName(item.getEnName())
                .koName(item.getKoName())
                .company(item.getCompany())
                .itemCode(item.getItemCode())
                .build();
    }
}
