package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ItemDetailInfoRes {
    private String enName;
    private String koName;
    private String category;

    private List<ItemStockRes> itemStockList; // 사이즈 및 색깔별로 재고 현황
    private Integer amount; // 가격
    private Integer unitPrice; // 원가
    private String content; // 아이템 설명
    private String company; // 회사
    private String itemCode; // 아이템 코드

    public static ItemDetailInfoRes toDto(List<Item> items) {
        return ItemDetailInfoRes.builder()
                .enName(items.get(0).getEnName())
                .koName(items.get(0).getKoName())
                .category(items.get(0).getCategory().getCategory())
                .itemStockList(items.stream().map(ItemStockRes::toDto).toList())
                .amount(items.get(0).getAmount())
                .unitPrice(items.get(0).getUnitPrice())
                .content(items.get(0).getContent())
                .company(items.get(0).getCompany())
                .itemCode(items.get(0).getItemCode())
                .build();
    }
}
