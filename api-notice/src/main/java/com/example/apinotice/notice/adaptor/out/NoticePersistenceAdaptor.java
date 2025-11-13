package com.example.apinotice.notice.adaptor.out;

import MeshX.common.PersistenceAdapter;
import com.example.apinotice.notice.adaptor.out.jpa.NoticeEntity;
import com.example.apinotice.notice.adaptor.out.jpa.NoticeImageEntity;
import com.example.apinotice.notice.adaptor.out.jpa.NoticeImageRepository;
import com.example.apinotice.notice.adaptor.out.jpa.NoticeRepository;
import com.example.apinotice.notice.adaptor.out.mapper.NoticeMapper;
import com.example.apinotice.notice.common.exception.NoticeException;

import com.example.apinotice.notice.domain.Notice;
import com.example.apinotice.notice.domain.NoticeImage;
import com.example.apinotice.notice.usecase.port.out.NoticePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

import static com.example.apinotice.notice.common.exception.NoticeExceptionMessage.NOTICE_NOT_FOUND;


@PersistenceAdapter
@RequiredArgsConstructor
public class NoticePersistenceAdaptor implements NoticePersistencePort {
    private final NoticeRepository noticeRepository;

    @Override
    public void create(Notice notice) {
        NoticeEntity noticeEntity = NoticeMapper.toEntity(notice);

        if (notice.getImages() != null) {
            for (NoticeImage img : notice.getImages()) {
                NoticeImageEntity imgEntity = NoticeImageEntity.builder()
                        .originalFilename(img.getOriginalFilename())
                        .s3Key(img.getS3Key())
                        .contentType(img.getContentType())
                        .fileSize(img.getFileSize())
                        .build();

                noticeEntity.addImageEntity(imgEntity);  // ★ 핵심
            }
        }
        noticeRepository.save(noticeEntity); // cascade로 이미지까지 저장됨
    }

    @Override
    public Notice findById(Integer id) {
        NoticeEntity entity = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeException(NOTICE_NOT_FOUND));

        return NoticeMapper.toDomain(entity);
    }

    @Override
    public Page<Notice> findAll(Pageable pageable){
        Page<NoticeEntity> entity = noticeRepository.findAll(pageable);
        return entity.map(NoticeMapper::toDomain);
    }

    @Override
    public List<Notice> findAll(){
        List<NoticeEntity> entities = noticeRepository.findAll();
        return entities.stream().map(NoticeMapper::toDomain).toList();
    }

    @Override
    public Notice update(Notice notice) {
        NoticeEntity entity = noticeRepository.findById(notice.getId())
                .orElseThrow(() -> new NoticeException(NOTICE_NOT_FOUND));

        entity.updateFields(notice.getTitle(), notice.getContents(), notice.getAuthor(), notice.getIsOpen());

        entity.clearImages();
        if (notice.getImages() != null) {
            for (NoticeImage img : notice.getImages()) {
                NoticeImageEntity imgEntity = NoticeImageEntity.builder()
                        .originalFilename(img.getOriginalFilename())
                        .s3Key(img.getS3Key())
                        .contentType(img.getContentType())
                        .fileSize(img.getFileSize())
                        .build();

                entity.addImageEntity(imgEntity);
            }
        }

        NoticeEntity saved = noticeRepository.save(entity);
        return NoticeMapper.toDomain(saved);
    }
}
