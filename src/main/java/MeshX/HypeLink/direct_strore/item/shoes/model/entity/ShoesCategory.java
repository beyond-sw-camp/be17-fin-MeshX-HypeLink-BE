package MeshX.HypeLink.direct_strore.item.shoes.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShoesCategory {
    SNEAKERS("스니커즈"),
    BOOTS("부츠"),
    SLIPPERS("슬리퍼");

    private final String koreanName;
}
