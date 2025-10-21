package MeshX.HypeLink.head_office.notice.model.dto.response;

import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import MeshX.HypeLink.image.model.entity.Image;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class NoticeInfoListRes {
    private final List<NoticeInfoRes> notices;

    public static NoticeInfoListRes toDto(List<Notice> entity, Function<Image, String> urlGenerator) {
        return NoticeInfoListRes.builder()
                .notices(entity.stream()
                                .map(notice -> NoticeInfoRes.toDto(notice, urlGenerator))
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Builder
    private NoticeInfoListRes(List<NoticeInfoRes> notices) {
        this.notices = notices;
    }
}
