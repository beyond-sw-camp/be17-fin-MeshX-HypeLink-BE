package MeshX.HypeLink.auth.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {
    private final ExceptionType exceptionType;

    public TokenException(ExceptionType exceptionType) {
        super(exceptionType.message());
        this.exceptionType = exceptionType;
    }
}
