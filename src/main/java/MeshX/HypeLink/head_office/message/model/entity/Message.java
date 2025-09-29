package MeshX.HypeLink.head_office.message.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.head_office.store.model.entity.StoreMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String contents;

    @Enumerated(EnumType.STRING)
    private MessageState messageState;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private StoreMember sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private StoreMember receiver;
}
