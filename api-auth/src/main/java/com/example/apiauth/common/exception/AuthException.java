package com.example.apiauth.common.exception;

import MeshX.common.exception.BaseException;

public class AuthException extends BaseException {

    public AuthException(AuthExceptionMessage exceptionType) {
        super(exceptionType);
    }
}
