package MeshX.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final ExceptionType exceptionType;

    public BaseException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }
}
