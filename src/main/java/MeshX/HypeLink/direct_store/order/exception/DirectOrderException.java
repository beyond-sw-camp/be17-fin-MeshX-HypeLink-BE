package MeshX.HypeLink.direct_store.order.exception;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.common.exception.ExceptionType;

public class DirectOrderException extends BaseException {
    public DirectOrderException(ExceptionType exceptionType){
        super(exceptionType);
    }
}
