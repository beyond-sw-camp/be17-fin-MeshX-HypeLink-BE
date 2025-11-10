package com.example.apinotice.notice.usecase.port.in;

import MeshX.common.Page.PageRes;
import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;
import com.example.apinotice.notice.usecase.port.out.response.NoticeInfoDto;
import com.example.apinotice.notice.usecase.port.out.response.NoticeListInfoDto;
import org.springframework.data.domain.Pageable;

public interface WebPort {
    void create(NoticeSaveCommand noticeSaveCommand);

    NoticeInfoDto read(Integer id);

//    PageRes<NoticeListInfoDto> readList(Pageable pageable);
}
