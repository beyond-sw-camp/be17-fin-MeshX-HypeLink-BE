package com.example.apinotice.notice.usecase.port.out;

import com.example.apinotice.notice.adaptor.out.jpa.NoticeEntity;
import com.example.apinotice.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface NoticePersistencePort {
    void create(Notice notice);

    Notice findById(Integer id);

    Page<Notice> findAll(Pageable pageable);
}
