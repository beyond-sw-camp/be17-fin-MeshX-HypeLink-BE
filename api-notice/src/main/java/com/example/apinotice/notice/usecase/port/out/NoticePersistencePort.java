package com.example.apinotice.notice.usecase.port.out;

import com.example.apinotice.notice.adaptor.out.jpa.NoticeEntity;

public interface NoticePersistencePort {
    void create(NoticeEntity entity);
}
