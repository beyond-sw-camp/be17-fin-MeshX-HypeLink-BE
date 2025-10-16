package MeshX.HypeLink.direct_store.payment.exception;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.common.exception.ExceptionType;

public class PaymentException extends BaseException {
    public PaymentException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}