package com.example.apinotice.notice.common.exception;

import MeshX.common.exception.BaseException;

public class NoticeException extends BaseException {

    public NoticeException(NoticeExceptionMessage exceptionType) {
        super(exceptionType);
    }
}
