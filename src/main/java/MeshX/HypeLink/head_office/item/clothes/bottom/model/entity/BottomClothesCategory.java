package MeshX.HypeLink.head_office.item.clothes.bottom.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BottomClothesCategory {
    JEANS("청바지"),
    SLACKS("슬랙스"),
    SHORTS("반바지"),
    SKIRT("치마"),
    JOGGER("조거팬츠");

    private final String koreanName;
}
