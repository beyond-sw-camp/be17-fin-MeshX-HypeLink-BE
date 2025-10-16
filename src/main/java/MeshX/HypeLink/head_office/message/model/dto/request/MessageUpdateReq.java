package MeshX.HypeLink.head_office.message.model.dto.request;

import MeshX.HypeLink.head_office.message.model.entity.Message;
import lombok.Getter;

@Getter
public class MessageUpdateReq {
    private String title;
    private String contents;

    public Message toEntity(){
        return Message.builder()
                .title(title)
                .contents(contents)
                .build();

    }
}
