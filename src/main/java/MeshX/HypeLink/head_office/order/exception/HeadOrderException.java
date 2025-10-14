package MeshX.HypeLink.head_office.order.exception;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.common.exception.ExceptionType;

public class HeadOrderException extends BaseException {
    public HeadOrderException(ExceptionType exceptionType){
        super(exceptionType);
    }
}
