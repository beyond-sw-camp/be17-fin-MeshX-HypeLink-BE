package MeshX.HypeLink.direct_store.item.clothes.top.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TopClothesCategory {
    TSHIRT("티셔츠"),
    SHIRT("셔츠"),
    KNIT("니트"),
    HOODIE("후드티"),
    BLOUSE("블라우스");

    private final String koreanName;
}
