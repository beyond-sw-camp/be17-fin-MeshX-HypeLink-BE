package com.example.apinotice.notice.usecase.port;

import MeshX.common.UseCase;
import com.example.apinotice.notice.adaptor.out.jpa.NoticeEntity;
import com.example.apinotice.notice.adaptor.out.mapper.NoticeMapper;
import com.example.apinotice.notice.domain.Notice;
import com.example.apinotice.notice.usecase.port.in.WebPort;
import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;
import com.example.apinotice.notice.usecase.port.out.NoticePersistencePort;
import com.example.apinotice.notice.usecase.port.out.response.NoticeInfoDto;
import lombok.RequiredArgsConstructor;

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
        Notice notice = NoticeMapper.toDomain(noticePersistencePort.findById(id));
        return NoticeInfoDto.toDto(notice);

    }
}
