package com.example.apiauth.common.exception;

import MeshX.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GeocodingExceptionMessage implements ExceptionType {
    GEOCODING_FAILED("주소 변환 실패", "주소를 좌표로 변환하는 중 오류가 발생했습니다."),
    NO_RESULTS_FOUND("검색 결과 없음", "해당 주소에 대한 좌표를 찾을 수 없습니다.");

    private final String title;
    private final String messages;

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public String message() {
        return this.messages;
    }
}
