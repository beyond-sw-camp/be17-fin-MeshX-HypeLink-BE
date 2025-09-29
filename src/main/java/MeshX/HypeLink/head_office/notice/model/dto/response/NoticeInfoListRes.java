package MeshX.HypeLink.head_office.notice.model.dto.response;

import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class NoticeInfoListRes {
    private final List<NoticeInfoRes> notices;

    public static NoticeInfoListRes toDto(List<Notice> entity) {
        return NoticeInfoListRes.builder()
                .notices(entity.stream()
                                .map(NoticeInfoRes::toDto)
                                .toList()
                )
                .build();
    }

    @Builder
    private NoticeInfoListRes(List<NoticeInfoRes> notices) {
        this.notices = notices;
    }
}
