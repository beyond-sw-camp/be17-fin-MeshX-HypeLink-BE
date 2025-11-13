package com.example.apinotice.notice.usecase.port.out.response;

import com.example.apinotice.notice.config.NoticeS3Config;
import com.example.apinotice.notice.domain.NoticeImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoticeImageInfoDto {
    private Integer id;
    private String originalName;
    private String imageUrl;

    // NoticeImage → NoticeImageInfoDto 변환
    public static NoticeImageInfoDto toDto(NoticeImage img) {
        return NoticeImageInfoDto.builder()
                .id(img.getId())
                .originalName(img.getOriginalFilename())
                .imageUrl(NoticeS3Config.BASE_URL + img.getS3Key()) // ⭐ URL 생성
                .build();
    }
}