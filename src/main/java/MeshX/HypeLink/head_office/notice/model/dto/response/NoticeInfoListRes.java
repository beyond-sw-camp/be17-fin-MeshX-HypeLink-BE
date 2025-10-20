package MeshX.HypeLink.head_office.notice.model.dto.response;

import MeshX.HypeLink.common.s3.S3UrlBuilder;
import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NoticeInfoListRes {
    private final List<NoticeInfoRes> notices;

    public static NoticeInfoListRes toDto(List<Notice> entity, S3UrlBuilder urlBuilder) {
        return NoticeInfoListRes.builder()
                .notices(entity.stream()
                                .map(notice -> NoticeInfoRes.toDto(notice, urlBuilder))
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Builder
    private NoticeInfoListRes(List<NoticeInfoRes> notices) {
        this.notices = notices;
    }
}
