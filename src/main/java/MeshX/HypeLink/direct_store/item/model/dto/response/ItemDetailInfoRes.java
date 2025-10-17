package MeshX.HypeLink.direct_store.item.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemDetailInfoRes {
    private Integer id;
    private String name;
    private String category;

    private List<ItemStockRes> itemStockList; // 사이즈 및 색깔별로 재고 현황
    private Integer amount; // 가격
    private String content; // 아이템 설명
    private String company; // 회사
    private String itemCode; // 아이템 코드

    @Builder
    private ItemDetailInfoRes(Integer id, String name, String category, List<ItemStockRes> itemStockList,
                              Integer amount, String content, String company, String itemCode) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.itemStockList = itemStockList;
        this.amount = amount;
        this.content = content;
        this.company = company;
        this.itemCode = itemCode;
    }
}
