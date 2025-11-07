package com.example.apinotice.notice.usecase.port;

import MeshX.common.UseCase;
import com.example.apinotice.notice.domain.Notice;
import com.example.apinotice.notice.usecase.port.in.WebPort;
import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;
import com.example.apinotice.notice.usecase.port.out.NoticePersistencePort;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class NoticeUseCase implements WebPort {
    private final NoticePersistencePort noticePersistencePort;


    @Override
    public void create(NoticeSaveCommand noticeSaveCommand) {
        Notice notice = Notice.from(noticeSaveCommand);

        noticePersistencePort.create(notice.toEntity());

    }
}
