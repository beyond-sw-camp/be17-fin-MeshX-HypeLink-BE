package com.example.apinotice.notice.usecase.port;

import MeshX.common.Page.PageRes;
import MeshX.common.UseCase;
import com.example.apinotice.notice.adaptor.out.jpa.NoticeEntity;
import com.example.apinotice.notice.adaptor.out.mapper.NoticeMapper;
import com.example.apinotice.notice.domain.Notice;
import com.example.apinotice.notice.usecase.port.in.WebPort;
import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;
import com.example.apinotice.notice.usecase.port.out.NoticePersistencePort;
import com.example.apinotice.notice.usecase.port.out.response.NoticeInfoDto;
import com.example.apinotice.notice.usecase.port.out.response.NoticeListInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@UseCase
@RequiredArgsConstructor
public class NoticeUseCase implements WebPort {
    private final NoticePersistencePort noticePersistencePort;


    @Override
    public void create(NoticeSaveCommand noticeSaveCommand) {
        Notice notice =  NoticeMapper.toDomain(noticeSaveCommand);
        noticePersistencePort.create(notice);
    }

    @Override
    public NoticeInfoDto read(Integer id) {
        Notice notice = noticePersistencePort.findById(id);
        return NoticeInfoDto.toDto(notice);
    }

    @Override
    public PageRes<NoticeListInfoDto> readList(Pageable pageable) {
        Page<Notice> notice = noticePersistencePort.findAll(pageable));
        Page<NoticeListInfoDto> page = notice.map(NoticeListInfoDto::toDto);
        return PageRes.toDto(page);
    }


}
