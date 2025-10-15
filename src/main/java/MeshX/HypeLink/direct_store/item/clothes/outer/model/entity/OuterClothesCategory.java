package MeshX.HypeLink.direct_store.item.clothes.outer.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OuterClothesCategory {
    JACKET("자켓"),
    COAT("코트"),
    CARDIGAN("가디건"),
    PADDING("패딩"),
    WINDBREAKER("바람막이");

    private final String koreanName;
}
