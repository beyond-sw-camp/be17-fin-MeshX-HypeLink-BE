package MeshX.HypeLink.head_office.order.exception;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.common.exception.ExceptionType;

public class PurchaseOrderException extends BaseException {
    public PurchaseOrderException(ExceptionType exceptionType){
        super(exceptionType);
    }
}
