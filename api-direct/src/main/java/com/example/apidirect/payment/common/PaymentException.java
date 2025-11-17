package com.example.apidirect.payment.common;

import MeshX.common.exception.BaseException;
import MeshX.common.exception.ExceptionType;

public class PaymentException extends BaseException {
    public PaymentException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
