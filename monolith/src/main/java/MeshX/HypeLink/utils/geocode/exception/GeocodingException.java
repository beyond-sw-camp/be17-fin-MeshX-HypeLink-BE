package MeshX.HypeLink.utils.geocode.exception;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.common.exception.ExceptionType;

public class GeocodingException extends BaseException {
    public GeocodingException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
