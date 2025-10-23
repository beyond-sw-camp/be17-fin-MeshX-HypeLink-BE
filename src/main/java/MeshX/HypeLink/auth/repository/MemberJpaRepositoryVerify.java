package MeshX.HypeLink.auth.repository;

import MeshX.HypeLink.auth.exception.AuthException;
import MeshX.HypeLink.auth.exception.MemberException;
import MeshX.HypeLink.auth.exception.MemberExceptionMessage;
import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.MemberRole;
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

    public Member findById(Integer id) {
        Optional<Member> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new AuthException(USER_NAME_NOT_FOUND);
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

    public void delete(Member member) {
        repository.delete(member);
    }

    public long countByRole(MemberRole role) {
        return repository.countByRole(role);
    }

    public void verifyNotLastAdmin() {
        if (repository.countByRole(MemberRole.ADMIN) <= 1) {
            throw new MemberException(MemberExceptionMessage.CANNOT_DELETE_LAST_ADMIN);
        }
    }
}