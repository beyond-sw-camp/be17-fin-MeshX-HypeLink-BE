package MeshX.HypeLink.head_office.chat.repository;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.head_office.chat.model.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageJpaRepositoryVerify {
    private final ChatMessageRepository repository;

    public void save(ChatMessage entity) {
        repository.save(entity);
    }

    public Page<ChatMessage> findConversation(Member user1, Member user2, Pageable pageable) {
        return repository.findConversation(user1, user2, pageable);
    }

    public List<ChatMessage> findUnreadMessages(Member receiver, Member sender) {
        return repository.findUnreadMessages(receiver, sender);
    }

    public List<Object[]> countUnreadMessagesByReceiverGroupBySender(Member receiver) {
        return repository.countUnreadMessagesByReceiverGroupBySender(receiver);
    }
}
