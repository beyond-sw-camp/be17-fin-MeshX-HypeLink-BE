package com.example.apinotice.notice.usecase.port;

import MeshX.common.Page.PageRes;
import MeshX.common.UseCase;
import com.example.apinotice.notice.adaptor.out.jpa.NoticeEntity;
import com.example.apinotice.notice.adaptor.out.mapper.NoticeMapper;
import com.example.apinotice.notice.domain.Notice;
import com.example.apinotice.notice.usecase.port.in.WebPort;
import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;
import com.example.apinotice.notice.usecase.port.in.request.NoticeUpdateCommand;
import com.example.apinotice.notice.usecase.port.out.NoticePersistencePort;
import com.example.apinotice.notice.usecase.port.out.response.NoticeInfoDto;
import com.example.apinotice.notice.usecase.port.out.response.NoticeListInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
        Page<Notice> noticePage = noticePersistencePort.findAll(pageable);
        // 람다 변환: Notice → NoticeListInfoDto
        Page<NoticeListInfoDto> dtoPage = noticePage.map(notice ->
                NoticeListInfoDto.builder()
                        .notices(List.of(NoticeInfoDto.toDto(notice))) // 한 건짜리 리스트로 래핑
                        .build()
        );
        // PageRes로 변환
        return PageRes.toDto(dtoPage);
    }

    @Override
    public NoticeListInfoDto readList(){
        List<Notice> noticeList = noticePersistencePort.findAll();
        return NoticeListInfoDto.toDto(noticeList);
    }

    @Override
    public NoticeInfoDto update(Integer id, NoticeUpdateCommand  noticeUpdateCommand) {
        Notice notice = noticePersistencePort.findById(id);
        if (noticeUpdateCommand.getTitle() != null && !noticeUpdateCommand.getTitle().isBlank()) {
            notice.updateTitle(noticeUpdateCommand.getTitle());
        }
        if (noticeUpdateCommand.getContents() != null && !noticeUpdateCommand.getContents().isBlank()) {
            notice.updateContents(noticeUpdateCommand.getContents());
        }
        if (noticeUpdateCommand.getAuthor() != null && !noticeUpdateCommand.getAuthor().isBlank()) {
            notice.updateAuthor(noticeUpdateCommand.getAuthor());
        }

        if (noticeUpdateCommand.getIsOpen() != null) {
            notice.changeOpen();
        }


        Notice updated = noticePersistencePort.update(notice);
        return NoticeInfoDto.toDto(updated);
    }

}
