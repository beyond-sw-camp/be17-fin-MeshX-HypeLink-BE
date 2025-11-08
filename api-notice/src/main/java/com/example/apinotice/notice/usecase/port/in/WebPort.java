package com.example.apinotice.notice.usecase.port.in;

import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;
import com.example.apinotice.notice.usecase.port.out.response.NoticeInfoDto;

public interface WebPort {
    void create(NoticeSaveCommand noticeSaveCommand);

    NoticeInfoDto read(Integer id);
}
