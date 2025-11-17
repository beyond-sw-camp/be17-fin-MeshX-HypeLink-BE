package com.example.apidirect.customer.common;

import MeshX.common.exception.BaseException;
import MeshX.common.exception.ExceptionType;

public class CustomerException extends BaseException {
    public CustomerException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
