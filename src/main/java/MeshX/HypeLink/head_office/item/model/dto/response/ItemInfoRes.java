package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ItemInfoRes {
    private Integer id;
    private String itemCode; // 아이템 코드
    private String enName;
    private String koName;
    private String category;
    private Integer amount; // 가격
    private Integer unitPrice; // 원가
    private String content; // 아이템 설명
    private String company; // 회사

    public static ItemInfoRes toDto(Item item) {
        return ItemInfoRes.builder()
                .id(item.getId())
                .enName(item.getEnName())
                .koName(item.getKoName())
                .category(item.getCategory().getCategory())
                .amount(item.getAmount())
                .unitPrice(item.getUnitPrice())
                .content(item.getContent())
                .company(item.getCompany())
                .itemCode(item.getItemCode())
                .build();
    }
}
