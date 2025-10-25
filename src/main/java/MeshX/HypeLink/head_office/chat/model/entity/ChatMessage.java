package MeshX.HypeLink.head_office.chat.model.entity;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Boolean isRead = false;

    @Builder
    public ChatMessage(Member sender, Member receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
