package MeshX.HypeLink.auth.exception;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.common.exception.ExceptionType;

public class MemberException extends BaseException {

    public MemberException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
