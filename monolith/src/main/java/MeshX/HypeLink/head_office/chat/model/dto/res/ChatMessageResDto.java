package MeshX.HypeLink.head_office.chat.model.dto.res;

import MeshX.HypeLink.head_office.chat.model.entity.ChatMessage;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageResDto {
    private Integer messageId;
    private String senderEmail;
    private String senderName;
    private String receiverEmail;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isRead;

    public static ChatMessageResDto fromEntity(ChatMessage entity) {
        return ChatMessageResDto.builder()
                .messageId(entity.getId())
                .senderEmail(entity.getSender().getEmail())
                .senderName(entity.getSender().getName())
                .receiverEmail(entity.getReceiver().getEmail())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .isRead(entity.getIsRead())
                .build();
    }
}
