package MeshX.HypeLink.auth.service;

import MeshX.HypeLink.auth.model.dto.AuthTokens;
import MeshX.HypeLink.auth.model.dto.LoginReqDto;
import MeshX.HypeLink.auth.model.dto.RegisterReqDto;
import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.repository.MemberRepository;
import MeshX.HypeLink.auth.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public void register(RegisterReqDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        Member member = requestDto.toEntity(encodedPassword);
        memberRepository.save(member);
    }

    @Transactional
    public AuthTokens login(LoginReqDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("사용자에게 권한이 없습니다."));

        // 1. Access Token 및 Refresh Token 생성
        String accessToken = jwtUtils.createAccessToken(authentication.getName(), role);
        String refreshToken = jwtUtils.createRefreshToken(authentication.getName());

        // 2. DB에 Refresh Token 저장
        Member member = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        member.updateRefreshToken(refreshToken);

        return new AuthTokens(accessToken, refreshToken);
    }

    @Transactional
    public AuthTokens reissueTokens(String refreshToken) {
        // 1. Refresh Token 유효성 검증
        jwtUtils.validateToken(refreshToken);

        // 2. 토큰에서 사용자 이메일 추출
        Authentication authentication = jwtUtils.getAuthentication(refreshToken);
        String email = authentication.getName();

        // 3. DB의 토큰과 일치하는지 확인
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        if (!refreshToken.equals(member.getRefreshToken())) {
            throw new RuntimeException("유효하지 않은 Refresh Token 입니다.");
        }

        // 4. 새로운 토큰 생성 (Rotation)
        String newRole = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("사용자에게 권한이 없습니다."));

        String newAccessToken = jwtUtils.createAccessToken(email, newRole);
        String newRefreshToken = jwtUtils.createRefreshToken(email);

        // 5. DB에 새로운 Refresh Token 저장
        member.updateRefreshToken(newRefreshToken);

        return new AuthTokens(newAccessToken, newRefreshToken);
    }
}