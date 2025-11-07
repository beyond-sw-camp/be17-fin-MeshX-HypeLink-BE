package com.example.apinotice.notice.adaptor.out;

import MeshX.common.PersistenceAdapter;
import com.example.apinotice.notice.adaptor.out.jpa.NoticeEntity;
import com.example.apinotice.notice.adaptor.out.jpa.NoticeRepository;
import com.example.apinotice.notice.usecase.port.out.NoticePersistencePort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class NoticePersistenceAdaptor implements NoticePersistencePort {
    private final NoticeRepository noticeRepository;

    @Override
    public void create(NoticeEntity entity) {
        noticeRepository.save(entity);
    }

}
