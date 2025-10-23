package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemAndItemDetailInfoRes {
    private Integer id;
    private String category;
    private String color;
    private String colorCode;
    private String size;
    private Integer amount; // 가격
    private Integer unitPrice; // 원가
    private String enName; // 이름
    private String koName; // 이름
    private String content; // 아이템 설명
    private String company; // 회사
    private String itemCode; // 아이템 코드
    private String itemDetailCode; // 아이템 코드 + 색상 + 사이즈
    private Integer stock; // 재고

    public static ItemAndItemDetailInfoRes toDto(ItemDetail itemDetail) {
        return ItemAndItemDetailInfoRes.builder()
                .id(itemDetail.getId())
                .category(itemDetail.getItem().getCategory().getCategory())
                .color(itemDetail.getColor().getColorName())
                .colorCode(itemDetail.getColor().getColorCode())
                .size(itemDetail.getSize().getSize())
                .amount(itemDetail.getItem().getAmount())
                .unitPrice(itemDetail.getItem().getUnitPrice())
                .enName(itemDetail.getItem().getEnName())
                .koName(itemDetail.getItem().getKoName())
                .content(itemDetail.getItem().getContent())
                .company(itemDetail.getItem().getCompany())
                .itemCode(itemDetail.getItem().getItemCode())
                .itemDetailCode(itemDetail.getItemDetailCode())
                .stock(itemDetail.getStock())
                .build();
    }
}
