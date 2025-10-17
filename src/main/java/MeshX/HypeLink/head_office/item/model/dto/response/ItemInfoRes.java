package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemInfoRes {
    private Integer id;
    private String category;
    private String color;
    private String size;
    private Integer amount; // 가격
    private String enName; // 이름
    private String koName; // 이름
    private String content; // 아이템 설명
    private String company; // 회사
    private String itemCode; // 아이템 코드
    private String itemDetailCode; // 아이템 코드 + 색상 + 사이즈
    private Integer stock; // 재고

    public static ItemInfoRes toDto(Item item) {
        return ItemInfoRes.builder()
                .id(item.getId())
                .category(item.getCategory().getCategory())
                .color(item.getColor().getColorCode())
                .size(item.getSize().getSize())
                .amount(item.getAmount())
                .enName(item.getEnName())
                .koName(item.getKoName())
                .content(item.getContent())
                .company(item.getCompany())
                .itemCode(item.getItemCode())
                .itemDetailCode(item.getItemDetailCode())
                .stock(item.getStock())
                .build();
    }
}
