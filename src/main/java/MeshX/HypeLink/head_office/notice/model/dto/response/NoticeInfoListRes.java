package MeshX.HypeLink.head_office.notice.model.dto.response;

import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NoticeInfoListRes {
    private final List<NoticeListResponse> notices;

    public static NoticeInfoListRes toDto(List<Notice> entity) {
        return NoticeInfoListRes.builder()
                .notices(entity.stream()
                                .map(NoticeListResponse::toDto)
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Builder
    private NoticeInfoListRes(List<NoticeListResponse> notices) {
        this.notices = notices;
    }
}