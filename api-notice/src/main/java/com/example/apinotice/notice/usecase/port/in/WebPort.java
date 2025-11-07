package com.example.apinotice.notice.usecase.port.in;

import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;

public interface WebPort {
    void create(NoticeSaveCommand noticeSaveCommand);
}
