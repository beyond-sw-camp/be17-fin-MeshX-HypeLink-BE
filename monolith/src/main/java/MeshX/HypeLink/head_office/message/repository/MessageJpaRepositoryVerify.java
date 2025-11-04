package MeshX.HypeLink.head_office.message.repository;

import MeshX.HypeLink.head_office.message.exception.MessageException;
import MeshX.HypeLink.head_office.message.model.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.message.exception.MessageExceptionType.MESSAGE_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class MessageJpaRepositoryVerify {
    private final MessageRepository repository;

    public void createMessage(Message entity) {
        repository.save(entity);
    }

    public List<Message> readAll() {
        List<Message> result = repository.findAll();
        if (!result.isEmpty()) {
            return result;
        }
        throw new MessageException(MESSAGE_NOT_FOUND);
    }

    public Message read(Integer id) {
        Optional<Message> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new MessageException(MESSAGE_NOT_FOUND);
    }

    public Message update(Message entity) {
        return repository.save(entity);
    }

    public void delete(Message entity) {
        repository.delete(entity);
    }

}
