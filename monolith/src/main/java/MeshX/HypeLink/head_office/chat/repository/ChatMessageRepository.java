package MeshX.HypeLink.head_office.chat.repository;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.head_office.chat.model.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    @Query(value = "SELECT m FROM ChatMessage m " +
           "WHERE (m.sender = :user1 AND m.receiver = :user2) OR (m.sender = :user2 AND m.receiver = :user1)",
           countQuery = "SELECT count(m) FROM ChatMessage m WHERE (m.sender = :user1 AND m.receiver = :user2) OR (m.sender = :user2 AND m.receiver = :user1)")
    Page<ChatMessage> findConversation(@Param("user1") Member user1, @Param("user2") Member user2, Pageable pageable);

    @Query("SELECT m FROM ChatMessage m WHERE m.receiver = :receiver AND m.sender = :sender AND m.isRead = false")
    List<ChatMessage> findUnreadMessages(@Param("receiver") Member receiver, @Param("sender") Member sender);

    @Query("SELECT m.sender.id, COUNT(m) FROM ChatMessage m WHERE m.receiver = :receiver AND m.isRead = false GROUP BY m.sender.id")
    List<Object[]> countUnreadMessagesByReceiverGroupBySender(@Param("receiver") Member receiver);
}
