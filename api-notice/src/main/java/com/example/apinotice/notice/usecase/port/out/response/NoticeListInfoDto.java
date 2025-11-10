package com.example.apinotice.notice.usecase.port.out.response;


import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
public class NoticeListInfoDto {
    private final List<NoticeListInfoDto> notices;


//    public static NoticeListInfoDto toDto(List<Notice> domain) {
//        return NoticeListInfoDto.builder()
//                .notices(domain.stream()
//                        .map(NoticeListInfoDto::toDto)
//                        .collect(Collectors.toList())
//                )
//                .build();
//    }

    @Builder
    private NoticeListInfoDto(List<NoticeListInfoDto> notices) {
        this.notices = notices;
    }
}
