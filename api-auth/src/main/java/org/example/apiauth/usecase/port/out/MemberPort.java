package org.example.apiauth.usecase.port.out;

import org.example.apiauth.domain.model.Member;
import java.util.Optional;

public interface MemberPort {

    Optional<Member> findByEmail(String email);

    Optional<Member> findById(Integer id);

    Member save(Member member);

    boolean existsByEmail(String email);
}
