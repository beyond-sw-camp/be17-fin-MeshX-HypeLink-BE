package com.example.apinotice.notice.adaptor.out.mapper;

import com.example.apinotice.notice.adaptor.out.jpa.NoticeEntity;
import com.example.apinotice.notice.domain.Notice;
import com.example.apinotice.notice.domain.NoticeImage;
import com.example.apinotice.notice.usecase.port.in.request.NoticeImageCreateCommand;
import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoticeMapper {
    private NoticeMapper(){}

    public static NoticeEntity toEntity(Notice notice){
       return NoticeEntity.builder()
               .id(notice.getId())
               .title(notice.getTitle())
               .contents(notice.getContents())
               .author(notice.getAuthor())
               .isOpen(notice.getIsOpen())
               .build();
    }

    public static Notice toDomain(NoticeEntity noticeEntity){
        return Notice.builder()
                .id(noticeEntity.getId())
                .title(noticeEntity.getTitle())
                .contents((noticeEntity.getContents()))
                .author(noticeEntity.getAuthor())
                .isOpen(noticeEntity.getIsOpen())
                .createdAt(noticeEntity.getCreatedAt())   // BaseEntity 에서 상속
                .updatedAt(noticeEntity.getUpdatedAt())   // BaseEntity 에서 상속
                .images(
                        noticeEntity.getImages() == null ? null :
                                noticeEntity.getImages().stream()
                                        .map(img -> NoticeImage.builder()
                                                .id(img.getId())
                                                .originalFilename(img.getOriginalFilename())
                                                .s3Key(img.getS3Key())
                                                .contentType(img.getContentType())
                                                .fileSize(img.getFileSize())
                                                .build()
                                        )
                                        .toList()
                )
                .build();
    }

    public static Notice toDomain(NoticeSaveCommand command){

        List<NoticeImage> imageList = new ArrayList<>();

        if (command.getImages() != null && !command.getImages().isEmpty()) {
            imageList = command.getImages().stream()
                    .map(NoticeMapper::toDomain)
                    .collect(Collectors.toList());
        }

        return Notice.builder()
                .title(command.getTitle())
                .contents(command.getContents())
                .author(command.getAuthor())
                .isOpen(false)
                .images(imageList) // 항상 빈 리스트라도 들어감
                .build();
    }
    public static NoticeImage toDomain(NoticeImageCreateCommand cmd) {
        return NoticeImage.builder()
                .originalFilename(cmd.getOriginalFilename())
                .s3Key(cmd.getS3Key())
                .contentType(cmd.getContentType())
                .fileSize(cmd.getFileSize())
                .build();
    }
}
