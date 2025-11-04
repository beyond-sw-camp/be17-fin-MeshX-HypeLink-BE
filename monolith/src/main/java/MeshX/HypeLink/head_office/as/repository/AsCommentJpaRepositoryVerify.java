package MeshX.HypeLink.head_office.as.repository;

import MeshX.HypeLink.head_office.as.exception.AsException;
import MeshX.HypeLink.head_office.as.model.entity.AsComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static MeshX.HypeLink.head_office.as.exception.AsExceptionMessage.AS_COMMENT_NOT_FOUNT;

@Repository
@RequiredArgsConstructor
public class AsCommentJpaRepositoryVerify {
    private final AsCommentRepository repository;

    public AsComment findById(Integer id) {
        Optional<AsComment> asComment = repository.findById(id);
        if (asComment.isPresent()) {
            return asComment.get();
        }
        throw new AsException(AS_COMMENT_NOT_FOUNT);
    }

    public void save(AsComment asComment) {
        repository.save(asComment);
    }

}
