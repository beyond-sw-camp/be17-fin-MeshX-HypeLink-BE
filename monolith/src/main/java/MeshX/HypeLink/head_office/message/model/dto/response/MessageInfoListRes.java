package MeshX.HypeLink.head_office.message.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageInfoListRes {
    private String title;
    private String content;

    @Builder
    private MessageInfoListRes(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
