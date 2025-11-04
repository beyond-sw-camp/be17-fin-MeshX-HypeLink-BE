package MeshX.HypeLink.auth.service;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

//@Primary
@Slf4j
@Repository
@RequiredArgsConstructor
public class DatabaseTokenStore implements TokenStore {

    private final MemberJpaRepositoryVerify memberJpaRepositoryVerify;

    @Override
    public void saveRefreshToken(String email, String refreshToken) {
        Member member = memberJpaRepositoryVerify.findByEmail(email);
        member.updateRefreshToken(refreshToken);
        memberJpaRepositoryVerify.save(member);
    }

    @Override
    public Optional<String> getRefreshToken(String email) {
        Member member = memberJpaRepositoryVerify.findByEmail(email);
        return Optional.ofNullable(member.getRefreshToken());
    }

    @Override
    public void deleteRefreshToken(String email) {
        Member member = memberJpaRepositoryVerify.findByEmail(email);
        member.updateRefreshToken(null);
        memberJpaRepositoryVerify.save(member);
    }

    @Override
    public void blacklistToken(String token, Duration timeToLive) {
        log.info("DB에서는 작동X");
    }

    @Override
    public boolean isBlacklisted(String token) {
        log.info("DB에서는 작동X");
        return false;
    }
}