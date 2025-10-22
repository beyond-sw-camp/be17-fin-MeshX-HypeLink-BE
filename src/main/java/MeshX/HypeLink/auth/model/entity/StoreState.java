package MeshX.HypeLink.auth.model.entity;

import lombok.Getter;

@Getter
public enum StoreState {
    OPEN("영업 중"),
    CLOSED("영업 종료"),
    TEMP_CLOSED("휴점");

    private final String description;

    StoreState(String description) {
        this.description = description;
    }
}