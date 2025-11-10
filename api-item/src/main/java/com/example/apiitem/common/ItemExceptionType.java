package com.example.apiitem.common;

import MeshX.common.exception.ExceptionType;
import lombok.Builder;

@Builder
public class ItemExceptionType implements ExceptionType {
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
}
