package com.example.apinotice.notice.usecase.port.out;

import com.example.apinotice.notice.adaptor.out.jpa.NoticeEntity;
import com.example.apinotice.notice.domain.Notice;


public interface NoticePersistencePort {
    void create(Notice notice);

    NoticeEntity findById(Integer id);
}
