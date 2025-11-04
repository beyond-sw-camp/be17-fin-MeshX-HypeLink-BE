package MeshX.HypeLink.head_office.message.repository;

import MeshX.HypeLink.head_office.message.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
