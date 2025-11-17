package com.example.apidirect.auth.adapter.in.kafka;

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

    // description(한글 설명)으로 Enum 찾기
    public static StoreState fromDescription(String description) {
        for (StoreState state : values()) {
            if (state.getDescription().equals(description)) {
                return state;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 상태입니다: " + description);
    }
}