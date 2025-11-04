package MeshX.HypeLink.auth.exception;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.common.exception.ExceptionType;

public class AuthException extends BaseException {

    public AuthException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
