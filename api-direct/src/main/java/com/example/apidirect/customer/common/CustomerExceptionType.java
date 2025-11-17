package com.example.apidirect.customer.common;

import MeshX.common.exception.ExceptionType;
import lombok.Builder;

@Builder
public class CustomerExceptionType implements ExceptionType {
    private final String title;
    private final String message;

    @Override
    public String title() {
        return title;
    }

    @Override
    public String message() {
        return message;
    }

    // Customer 조회 관련
    public static final CustomerExceptionType CUSTOMER_NOT_FOUND = CustomerExceptionType.builder()
            .title("고객 Error")
            .message("고객 정보를 찾을 수 없습니다")
            .build();

    public static final CustomerExceptionType CUSTOMER_ALREADY_EXISTS = CustomerExceptionType.builder()
            .title("고객 Error")
            .message("이미 등록된 전화번호입니다")
            .build();

    // 본사 API 관련
    public static final CustomerExceptionType HEAD_OFFICE_API_ERROR = CustomerExceptionType.builder()
            .title("본사 API Error")
            .message("본사 서버와 통신에 실패했습니다")
            .build();

    // Validation 관련
    public static final CustomerExceptionType INVALID_PHONE_FORMAT = CustomerExceptionType.builder()
            .title("Validation Error")
            .message("전화번호 형식이 올바르지 않습니다")
            .build();

    public static final CustomerExceptionType INVALID_BIRTH_DATE = CustomerExceptionType.builder()
            .title("Validation Error")
            .message("생년월일이 올바르지 않습니다")
            .build();

    // 동기화 관련
    public static final CustomerExceptionType CUSTOMER_SYNC_FAILED = CustomerExceptionType.builder()
            .title("동기화 Error")
            .message("고객 정보 동기화에 실패했습니다")
            .build();
}
