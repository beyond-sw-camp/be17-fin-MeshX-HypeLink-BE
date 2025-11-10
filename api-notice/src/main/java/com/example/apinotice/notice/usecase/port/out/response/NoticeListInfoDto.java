package com.example.apinotice.notice.usecase.port.out.response;


import com.example.apinotice.notice.domain.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Builder
public class NoticeListInfoDto {
    private List<NoticeInfoDto> notices;

    public static NoticeListInfoDto toDto(List<Notice> domainList) {
        List<NoticeInfoDto> dtoList = domainList.stream()
                .map(NoticeInfoDto::toDto)
                .toList();

        return NoticeListInfoDto.builder()
                .notices(dtoList)
                .build();
    }
}

