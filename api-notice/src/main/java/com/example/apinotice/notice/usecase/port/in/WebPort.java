package com.example.apinotice.notice.usecase.port.in;

import MeshX.common.Page.PageRes;
import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;
import com.example.apinotice.notice.usecase.port.in.request.NoticeUpdateCommand;
import com.example.apinotice.notice.usecase.port.out.response.NoticeInfoDto;
import com.example.apinotice.notice.usecase.port.out.response.NoticeListInfoDto;
import com.example.apinotice.notice.usecase.port.out.response.NoticePageListInfoDto;
import org.springframework.data.domain.Pageable;

public interface WebPort {
    void create(NoticeSaveCommand noticeSaveCommand);

    NoticeInfoDto read(Integer id);

    PageRes<NoticePageListInfoDto> readList(Pageable pageable);

    NoticeListInfoDto readList();

    NoticeInfoDto update(Integer id, NoticeUpdateCommand updatecommand);
}
