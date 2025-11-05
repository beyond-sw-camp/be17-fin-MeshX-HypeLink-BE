package org.example.apiauth.common.exception;

import MeshX.common.exception.ExceptionType;
import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {
    private final ExceptionType exceptionType;

    public TokenException(ExceptionType exceptionType) {
        super(exceptionType.message());
        this.exceptionType = exceptionType;
    }
}
