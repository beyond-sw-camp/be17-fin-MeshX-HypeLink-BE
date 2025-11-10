package org.example.apidirect.common.exception;

public class ItemException extends RuntimeException {
    private final ExceptionType exceptionType;

    public ItemException(ExceptionType exceptionType) {
        super(exceptionType.message());
        this.exceptionType = exceptionType;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }
}
