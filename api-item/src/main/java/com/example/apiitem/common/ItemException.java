package com.example.apiitem.common;

import MeshX.common.exception.BaseException;
import MeshX.common.exception.ExceptionType;

public class ItemException extends BaseException {
    public ItemException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
