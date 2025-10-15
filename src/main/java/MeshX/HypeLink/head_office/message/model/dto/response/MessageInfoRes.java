package MeshX.HypeLink.head_office.message.model.dto.response;

import MeshX.HypeLink.head_office.message.model.entity.Message;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeInfoRes;
import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.Builder;
import lombok.Getter;
@Getter
public class MessageInfoRes {

    private String title;
    private String contents;

    public static MessageInfoRes toDto(Message entity) {
        return MessageInfoRes.builder()
                .title(entity.getTitle())
                .contents(entity.getContents())
                .build();
    }

    @Builder
    private MessageInfoRes(String title, String contents, Boolean isOpen) {
        this.title = title;
        this.contents = contents;
    }
}
