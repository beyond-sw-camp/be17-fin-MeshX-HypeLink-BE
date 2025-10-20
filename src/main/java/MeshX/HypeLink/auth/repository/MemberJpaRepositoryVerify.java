package MeshX.HypeLink.auth.repository;

import MeshX.HypeLink.auth.exception.AuthException;
import MeshX.HypeLink.auth.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.auth.exception.AuthExceptionMessage.EMAIL_ALREADY_EXISTS;
import static MeshX.HypeLink.auth.exception.AuthExceptionMessage.USER_NAME_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryVerify {
    private final MemberRepository repository;

    public void existsByEmail(String email) {
        boolean result = repository.existsByEmail(email);
        if (result) {
            throw new AuthException(EMAIL_ALREADY_EXISTS);
        }
    }


    public Member findByEmail(String email) {
        Optional<Member> optional = repository.findByEmail(email);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new AuthException(USER_NAME_NOT_FOUND);
    }

    public void save(Member member) {
        repository.save(member);
    }

    public List<Member> findAll() {
        return repository.findAll();
    }
}