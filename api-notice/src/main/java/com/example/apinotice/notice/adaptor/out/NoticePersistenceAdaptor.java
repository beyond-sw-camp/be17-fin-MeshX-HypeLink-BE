package com.example.apinotice.notice.adaptor.out;

import MeshX.common.PersistenceAdapter;
import com.example.apinotice.notice.adaptor.out.jpa.NoticeEntity;
import com.example.apinotice.notice.adaptor.out.jpa.NoticeRepository;
import com.example.apinotice.notice.adaptor.out.mapper.NoticeMapper;
import com.example.apinotice.notice.common.exception.NoticeException;

import com.example.apinotice.notice.domain.Notice;
import com.example.apinotice.notice.usecase.port.out.NoticePersistencePort;
import lombok.RequiredArgsConstructor;


import static com.example.apinotice.notice.common.exception.NoticeExceptionMessage.NOTICE_NOT_FOUND;


@PersistenceAdapter
@RequiredArgsConstructor
public class NoticePersistenceAdaptor implements NoticePersistencePort {
    private final NoticeRepository noticeRepository;

    @Override
    public void create(Notice notice) {
        noticeRepository.save(NoticeMapper.toEntity(notice));
    }

    @Override
    public Notice findById(Integer id) {
        NoticeEntity entity = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeException(NOTICE_NOT_FOUND));

        return NoticeMapper.toDomain(entity);
    }

}
