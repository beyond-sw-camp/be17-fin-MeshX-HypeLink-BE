package MeshX.HypeLink.head_office.message.exception;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.common.exception.ExceptionType;

public class MessageException extends BaseException {
    public MessageException(ExceptionType message) {
        super(message);
    }
}
