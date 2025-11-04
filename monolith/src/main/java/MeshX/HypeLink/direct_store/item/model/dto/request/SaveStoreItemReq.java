package MeshX.HypeLink.direct_store.item.model.dto.request;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import lombok.Getter;

import java.util.List;

@Getter
public class SaveStoreItemReq {
    private String category;
    private String itemCode; // 아이템 코드
    private Integer unitPrice;       // 단가
    private Integer amount; // 가격
    private String enName; // 이름
    private String koName; // 이름
    private String content; // 아이템 설명
    private String company; // 회사
    private List<SaveStoreItemImageReq> images;
    private List<SaveStoreDetailReq> itemDetails;

    public StoreItem toEntity(Store store, StoreCategory category) {
        return StoreItem.builder()
                .store(store)
                .category(category)
                .itemCode(itemCode)
                .unitPrice(unitPrice)
                .amount(amount)
                .enName(enName)
                .koName(koName)
                .content(content)
                .company(company)
                .build();
    }
}
