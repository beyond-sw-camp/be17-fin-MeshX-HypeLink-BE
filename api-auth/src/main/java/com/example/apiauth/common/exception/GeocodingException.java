package com.example.apiauth.common.exception;

import MeshX.common.exception.BaseException;

public class GeocodingException extends BaseException {
    public GeocodingException(GeocodingExceptionMessage exceptionType) {
        super(exceptionType);
    }
}
