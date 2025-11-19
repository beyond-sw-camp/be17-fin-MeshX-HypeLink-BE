package MeshX.HypeLink.common;

import MeshX.HypeLink.auth.model.entity.*;
import MeshX.HypeLink.auth.repository.DriverJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.PosJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.common.exception.BaseException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BaseMember {
    private final MemberJpaRepositoryVerify memberRepository;
    private final StoreJpaRepositoryVerify storeRepository;
    private final PosJpaRepositoryVerify posRepository;
    private final DriverJpaRepositoryVerify driverRepository;
    private final PasswordEncoder encoder;

    @PostConstruct
    @Transactional
    public void init() {
        boolean check = checkDataExists();
        if(!check) {
            initMembers();
            initStoresAndPoses();
            initDrivers();
        }
    }

    private boolean checkDataExists() {
        try {
            memberRepository.findByEmail("hq@company.com");
            log.info("✅ 베이스 데이터 이미 존재");
            return true;
        } catch (BaseException e) {
            log.info("📋 베이스 데이터 미존재 - 생성 시작");
        }
        return false;
    }

    private void initMembers() {
        List<Member> members = new ArrayList<>();

        // ============================================
        // 1. 본사 관리자 (비밀번호: 1234)
        // ============================================
        members.add(Member.builder()
                .email("hq@company.com")
                .password(encoder.encode("1234"))
                .name("본사관리자")
                .phone("010-1111-1111")
                .address("서울특별시 강남구 테헤란로 1")
                .role(MemberRole.ADMIN)
                .region(Region.SEOUL_GYEONGGI)
                .refreshToken(null)
                .build());

        members.add(Member.builder()
                .email("manager@hypelink.com")
                .password(encoder.encode("1234"))
                .name("이지은")
                .phone("010-2345-6789")
                .address("서울특별시 강남구 역삼동 456")
                .role(MemberRole.MANAGER)
                .region(Region.SEOUL_GYEONGGI)
                .refreshToken(null)
                .build());

        // ============================================
        // 2. 매장별 지점장 + 기사 (10개 매장)
        // ============================================
        // 강남점
        members.add(Member.builder().email("gangnam@hypelink.com").password(encoder.encode("1234")).name("강남점_지점장").phone("010-8794-9606").address("서울특별시 강남구 강남대로 396").role(MemberRole.BRANCH_MANAGER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("driver.gangnam@hypelink.com").password(encoder.encode("1234")).name("강남점_기사").phone("010-5277-9716").address("서울특별시 강남구 강남대로 396").role(MemberRole.DRIVER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());

        // 홍대점
        members.add(Member.builder().email("hongdae@hypelink.com").password(encoder.encode("1234")).name("홍대점_지점장").phone("010-7758-3944").address("서울특별시 마포구 양화로 160").role(MemberRole.BRANCH_MANAGER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("driver.hongdae@hypelink.com").password(encoder.encode("1234")).name("홍대점_기사").phone("010-6711-7313").address("서울특별시 마포구 양화로 160").role(MemberRole.DRIVER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());

        // 잠실점
        members.add(Member.builder().email("jamsil@hypelink.com").password(encoder.encode("1234")).name("잠실점_지점장").phone("010-9627-9090").address("서울특별시 송파구 올림픽로 240").role(MemberRole.BRANCH_MANAGER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("driver.jamsil@hypelink.com").password(encoder.encode("1234")).name("잠실점_기사").phone("010-7922-8809").address("서울특별시 송파구 올림픽로 240").role(MemberRole.DRIVER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());

        // 분당점
        members.add(Member.builder().email("bundang@hypelink.com").password(encoder.encode("1234")).name("분당점_지점장").phone("010-3858-7084").address("경기도 성남시 분당구 황새울로 360").role(MemberRole.BRANCH_MANAGER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("driver.bundang@hypelink.com").password(encoder.encode("1234")).name("분당점_기사").phone("010-4526-8452").address("경기도 성남시 분당구 황새울로 360").role(MemberRole.DRIVER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());

        // 인천점
        members.add(Member.builder().email("incheon@hypelink.com").password(encoder.encode("1234")).name("인천점_지점장").phone("010-1322-5427").address("인천광역시 남동구 인주대로 593").role(MemberRole.BRANCH_MANAGER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("driver.incheon@hypelink.com").password(encoder.encode("1234")).name("인천점_기사").phone("010-3263-1885").address("인천광역시 남동구 인주대로 593").role(MemberRole.DRIVER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());

        // 부산 서면점
        members.add(Member.builder().email("busan.seomyeon@hypelink.com").password(encoder.encode("1234")).name("부산 서면점_지점장").phone("010-4560-2078").address("부산광역시 부산진구 서면로 68").role(MemberRole.BRANCH_MANAGER).region(Region.GYEONGSANG).refreshToken(null).build());
        members.add(Member.builder().email("driver.busan.seomyeon@hypelink.com").password(encoder.encode("1234")).name("부산 서면점_기사").phone("010-6474-1621").address("부산광역시 부산진구 서면로 68").role(MemberRole.DRIVER).region(Region.GYEONGSANG).refreshToken(null).build());

        // 대구 동성로점
        members.add(Member.builder().email("daegu.dongseongro@hypelink.com").password(encoder.encode("1234")).name("대구 동성로점_지점장").phone("010-5051-6932").address("대구광역시 중구 동성로2길 81").role(MemberRole.BRANCH_MANAGER).region(Region.GYEONGSANG).refreshToken(null).build());
        members.add(Member.builder().email("driver.daegu.dongseongro@hypelink.com").password(encoder.encode("1234")).name("대구 동성로점_기사").phone("010-3329-1187").address("대구광역시 중구 동성로2길 81").role(MemberRole.DRIVER).region(Region.GYEONGSANG).refreshToken(null).build());

        // 광주 충장로점
        members.add(Member.builder().email("gwangju.chungjangro@hypelink.com").password(encoder.encode("1234")).name("광주 충장로점_지점장").phone("010-7972-6622").address("광주광역시 동구 금남로5가 127").role(MemberRole.BRANCH_MANAGER).region(Region.JEOLLA).refreshToken(null).build());
        members.add(Member.builder().email("driver.gwangju.chungjangro@hypelink.com").password(encoder.encode("1234")).name("광주 충장로점_기사").phone("010-2358-1651").address("광주광역시 동구 금남로5가 127").role(MemberRole.DRIVER).region(Region.JEOLLA).refreshToken(null).build());

        // 대전 둔산점
        members.add(Member.builder().email("daejeon.dunsan@hypelink.com").password(encoder.encode("1234")).name("대전 둔산점_지점장").phone("010-3201-4623").address("대전광역시 서구 둔산로 100").role(MemberRole.BRANCH_MANAGER).region(Region.CHUNGCHEONG).refreshToken(null).build());
        members.add(Member.builder().email("driver.daejeon.dunsan@hypelink.com").password(encoder.encode("1234")).name("대전 둔산점_기사").phone("010-4459-2303").address("대전광역시 서구 둔산로 100").role(MemberRole.DRIVER).region(Region.CHUNGCHEONG).refreshToken(null).build());

        // 제주점
        members.add(Member.builder().email("jeju@hypelink.com").password(encoder.encode("1234")).name("제주점_지점장").phone("010-4659-7543").address("제주특별자치도 제주시 노형로 200").role(MemberRole.BRANCH_MANAGER).region(Region.JEJU).refreshToken(null).build());
        members.add(Member.builder().email("driver.jeju@hypelink.com").password(encoder.encode("1234")).name("제주점_기사").phone("010-2522-6296").address("제주특별자치도 제주시 노형로 200").role(MemberRole.DRIVER).region(Region.JEJU).refreshToken(null).build());

        // ============================================
        // 3. POS MEMBER (39명)
        // ============================================
        // 강남점 POS (3개)
        members.add(Member.builder().email("pos.gangnam.01@hypelink.com").password(encoder.encode("1234")).name("강남점_POS1").phone("010-3001-0001").address("서울특별시 강남구 강남대로 396").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("pos.gangnam.02@hypelink.com").password(encoder.encode("1234")).name("강남점_POS2").phone("010-3001-0002").address("서울특별시 강남구 강남대로 396").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("pos.gangnam.03@hypelink.com").password(encoder.encode("1234")).name("강남점_POS3").phone("010-3001-0003").address("서울특별시 강남구 강남대로 396").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());

        // 홍대점 POS (3개)
        members.add(Member.builder().email("pos.hongdae.01@hypelink.com").password(encoder.encode("1234")).name("홍대점_POS1").phone("010-3002-0001").address("서울특별시 마포구 양화로 160").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("pos.hongdae.02@hypelink.com").password(encoder.encode("1234")).name("홍대점_POS2").phone("010-3002-0002").address("서울특별시 마포구 양화로 160").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("pos.hongdae.03@hypelink.com").password(encoder.encode("1234")).name("홍대점_POS3").phone("010-3002-0003").address("서울특별시 마포구 양화로 160").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());

        // 잠실점 POS (5개)
        members.add(Member.builder().email("pos.jamsil.01@hypelink.com").password(encoder.encode("1234")).name("잠실점_POS1").phone("010-3003-0001").address("서울특별시 송파구 올림픽로 240").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("pos.jamsil.02@hypelink.com").password(encoder.encode("1234")).name("잠실점_POS2").phone("010-3003-0002").address("서울특별시 송파구 올림픽로 240").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("pos.jamsil.03@hypelink.com").password(encoder.encode("1234")).name("잠실점_POS3").phone("010-3003-0003").address("서울특별시 송파구 올림픽로 240").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("pos.jamsil.04@hypelink.com").password(encoder.encode("1234")).name("잠실점_POS4").phone("010-3003-0004").address("서울특별시 송파구 올림픽로 240").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("pos.jamsil.05@hypelink.com").password(encoder.encode("1234")).name("잠실점_POS5").phone("010-3003-0005").address("서울특별시 송파구 올림픽로 240").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());

        // 분당점 POS (3개)
        members.add(Member.builder().email("pos.bundang.01@hypelink.com").password(encoder.encode("1234")).name("분당점_POS1").phone("010-3004-0001").address("경기도 성남시 분당구 황새울로 360").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("pos.bundang.02@hypelink.com").password(encoder.encode("1234")).name("분당점_POS2").phone("010-3004-0002").address("경기도 성남시 분당구 황새울로 360").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("pos.bundang.03@hypelink.com").password(encoder.encode("1234")).name("분당점_POS3").phone("010-3004-0003").address("경기도 성남시 분당구 황새울로 360").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());

        // 인천점 POS (3개)
        members.add(Member.builder().email("pos.incheon.01@hypelink.com").password(encoder.encode("1234")).name("인천점_POS1").phone("010-3005-0001").address("인천광역시 남동구 인주대로 593").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("pos.incheon.02@hypelink.com").password(encoder.encode("1234")).name("인천점_POS2").phone("010-3005-0002").address("인천광역시 남동구 인주대로 593").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());
        members.add(Member.builder().email("pos.incheon.03@hypelink.com").password(encoder.encode("1234")).name("인천점_POS3").phone("010-3005-0003").address("인천광역시 남동구 인주대로 593").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build());

        // 부산 서면점 POS (5개)
        members.add(Member.builder().email("pos.busan.seomyeon.01@hypelink.com").password(encoder.encode("1234")).name("부산서면점_POS1").phone("010-3006-0001").address("부산광역시 부산진구 서면로 68").role(MemberRole.POS_MEMBER).region(Region.GYEONGSANG).refreshToken(null).build());
        members.add(Member.builder().email("pos.busan.seomyeon.02@hypelink.com").password(encoder.encode("1234")).name("부산서면점_POS2").phone("010-3006-0002").address("부산광역시 부산진구 서면로 68").role(MemberRole.POS_MEMBER).region(Region.GYEONGSANG).refreshToken(null).build());
        members.add(Member.builder().email("pos.busan.seomyeon.03@hypelink.com").password(encoder.encode("1234")).name("부산서면점_POS3").phone("010-3006-0003").address("부산광역시 부산진구 서면로 68").role(MemberRole.POS_MEMBER).region(Region.GYEONGSANG).refreshToken(null).build());
        members.add(Member.builder().email("pos.busan.seomyeon.04@hypelink.com").password(encoder.encode("1234")).name("부산서면점_POS4").phone("010-3006-0004").address("부산광역시 부산진구 서면로 68").role(MemberRole.POS_MEMBER).region(Region.GYEONGSANG).refreshToken(null).build());
        members.add(Member.builder().email("pos.busan.seomyeon.05@hypelink.com").password(encoder.encode("1234")).name("부산서면점_POS5").phone("010-3006-0005").address("부산광역시 부산진구 서면로 68").role(MemberRole.POS_MEMBER).region(Region.GYEONGSANG).refreshToken(null).build());

        // 대구 동성로점 POS (5개)
        members.add(Member.builder().email("pos.daegu.dongseongro.01@hypelink.com").password(encoder.encode("1234")).name("대구동성로점_POS1").phone("010-3007-0001").address("대구광역시 중구 동성로2길 81").role(MemberRole.POS_MEMBER).region(Region.GYEONGSANG).refreshToken(null).build());
        members.add(Member.builder().email("pos.daegu.dongseongro.02@hypelink.com").password(encoder.encode("1234")).name("대구동성로점_POS2").phone("010-3007-0002").address("대구광역시 중구 동성로2길 81").role(MemberRole.POS_MEMBER).region(Region.GYEONGSANG).refreshToken(null).build());
        members.add(Member.builder().email("pos.daegu.dongseongro.03@hypelink.com").password(encoder.encode("1234")).name("대구동성로점_POS3").phone("010-3007-0003").address("대구광역시 중구 동성로2길 81").role(MemberRole.POS_MEMBER).region(Region.GYEONGSANG).refreshToken(null).build());
        members.add(Member.builder().email("pos.daegu.dongseongro.04@hypelink.com").password(encoder.encode("1234")).name("대구동성로점_POS4").phone("010-3007-0004").address("대구광역시 중구 동성로2길 81").role(MemberRole.POS_MEMBER).region(Region.GYEONGSANG).refreshToken(null).build());
        members.add(Member.builder().email("pos.daegu.dongseongro.05@hypelink.com").password(encoder.encode("1234")).name("대구동성로점_POS5").phone("010-3007-0005").address("대구광역시 중구 동성로2길 81").role(MemberRole.POS_MEMBER).region(Region.GYEONGSANG).refreshToken(null).build());

        // 광주 충장로점 POS (3개)
        members.add(Member.builder().email("pos.gwangju.chungjangro.01@hypelink.com").password(encoder.encode("1234")).name("광주충장로점_POS1").phone("010-3008-0001").address("광주광역시 동구 금남로5가 127").role(MemberRole.POS_MEMBER).region(Region.JEOLLA).refreshToken(null).build());
        members.add(Member.builder().email("pos.gwangju.chungjangro.02@hypelink.com").password(encoder.encode("1234")).name("광주충장로점_POS2").phone("010-3008-0002").address("광주광역시 동구 금남로5가 127").role(MemberRole.POS_MEMBER).region(Region.JEOLLA).refreshToken(null).build());
        members.add(Member.builder().email("pos.gwangju.chungjangro.03@hypelink.com").password(encoder.encode("1234")).name("광주충장로점_POS3").phone("010-3008-0003").address("광주광역시 동구 금남로5가 127").role(MemberRole.POS_MEMBER).region(Region.JEOLLA).refreshToken(null).build());

        // 대전 둔산점 POS (4개)
        members.add(Member.builder().email("pos.daejeon.dunsan.01@hypelink.com").password(encoder.encode("1234")).name("대전둔산점_POS1").phone("010-3009-0001").address("대전광역시 서구 둔산로 100").role(MemberRole.POS_MEMBER).region(Region.CHUNGCHEONG).refreshToken(null).build());
        members.add(Member.builder().email("pos.daejeon.dunsan.02@hypelink.com").password(encoder.encode("1234")).name("대전둔산점_POS2").phone("010-3009-0002").address("대전광역시 서구 둔산로 100").role(MemberRole.POS_MEMBER).region(Region.CHUNGCHEONG).refreshToken(null).build());
        members.add(Member.builder().email("pos.daejeon.dunsan.03@hypelink.com").password(encoder.encode("1234")).name("대전둔산점_POS3").phone("010-3009-0003").address("대전광역시 서구 둔산로 100").role(MemberRole.POS_MEMBER).region(Region.CHUNGCHEONG).refreshToken(null).build());
        members.add(Member.builder().email("pos.daejeon.dunsan.04@hypelink.com").password(encoder.encode("1234")).name("대전둔산점_POS4").phone("010-3009-0004").address("대전광역시 서구 둔산로 100").role(MemberRole.POS_MEMBER).region(Region.CHUNGCHEONG).refreshToken(null).build());

        // 제주점 POS (4개)
        members.add(Member.builder().email("pos.jeju.01@hypelink.com").password(encoder.encode("1234")).name("제주점_POS1").phone("010-3010-0001").address("제주특별자치도 제주시 노형로 200").role(MemberRole.POS_MEMBER).region(Region.JEJU).refreshToken(null).build());
        members.add(Member.builder().email("pos.jeju.02@hypelink.com").password(encoder.encode("1234")).name("제주점_POS2").phone("010-3010-0002").address("제주특별자치도 제주시 노형로 200").role(MemberRole.POS_MEMBER).region(Region.JEJU).refreshToken(null).build());
        members.add(Member.builder().email("pos.jeju.03@hypelink.com").password(encoder.encode("1234")).name("제주점_POS3").phone("010-3010-0003").address("제주특별자치도 제주시 노형로 200").role(MemberRole.POS_MEMBER).region(Region.JEJU).refreshToken(null).build());
        members.add(Member.builder().email("pos.jeju.04@hypelink.com").password(encoder.encode("1234")).name("제주점_POS4").phone("010-3010-0004").address("제주특별자치도 제주시 노형로 200").role(MemberRole.POS_MEMBER).region(Region.JEJU).refreshToken(null).build());

        // 모든 멤버 저장
        for (Member member : members) {
            memberRepository.save(member);
        }
        log.info("✅ Member 데이터 생성 완료 (총 {}명)", members.size());
    }

    private void initStoresAndPoses() {
        // 매장별 지점장 조회
        Member owner1 = memberRepository.findByEmail("gangnam@hypelink.com");
        Member owner2 = memberRepository.findByEmail("hongdae@hypelink.com");
        Member owner3 = memberRepository.findByEmail("jamsil@hypelink.com");
        Member owner4 = memberRepository.findByEmail("bundang@hypelink.com");
        Member owner5 = memberRepository.findByEmail("incheon@hypelink.com");
        Member owner6 = memberRepository.findByEmail("busan.seomyeon@hypelink.com");
        Member owner7 = memberRepository.findByEmail("daegu.dongseongro@hypelink.com");
        Member owner8 = memberRepository.findByEmail("gwangju.chungjangro@hypelink.com");
        Member owner9 = memberRepository.findByEmail("daejeon.dunsan@hypelink.com");
        Member owner10 = memberRepository.findByEmail("jeju@hypelink.com");

        // ============================================
        // 매장 생성
        // ============================================
        List<Store> stores = new ArrayList<>();
        stores.add(Store.builder().lat(37.4979).lon(127.0276).posCount(3).storeNumber("STR-2023-001").member(owner1).storeState(StoreState.OPEN).build());
        stores.add(Store.builder().lat(37.5563).lon(126.9233).posCount(3).storeNumber("STR-2023-002").member(owner2).storeState(StoreState.OPEN).build());
        stores.add(Store.builder().lat(37.5130).lon(127.1025).posCount(5).storeNumber("STR-2023-003").member(owner3).storeState(StoreState.OPEN).build());
        stores.add(Store.builder().lat(37.3595).lon(127.1052).posCount(3).storeNumber("STR-2023-004").member(owner4).storeState(StoreState.OPEN).build());
        stores.add(Store.builder().lat(37.4563).lon(126.7052).posCount(3).storeNumber("STR-2023-005").member(owner5).storeState(StoreState.OPEN).build());
        stores.add(Store.builder().lat(35.1581).lon(129.0595).posCount(5).storeNumber("STR-2023-006").member(owner6).storeState(StoreState.OPEN).build());
        stores.add(Store.builder().lat(35.8714).lon(128.6014).posCount(5).storeNumber("STR-2023-007").member(owner7).storeState(StoreState.OPEN).build());
        stores.add(Store.builder().lat(35.1595).lon(126.8526).posCount(3).storeNumber("STR-2023-008").member(owner8).storeState(StoreState.OPEN).build());
        stores.add(Store.builder().lat(36.3504).lon(127.3845).posCount(4).storeNumber("STR-2023-009").member(owner9).storeState(StoreState.OPEN).build());
        stores.add(Store.builder().lat(33.4996).lon(126.5312).posCount(4).storeNumber("STR-2023-010").member(owner10).storeState(StoreState.OPEN).build());

        for (Store store : stores) {
            storeRepository.save(store);
        }
        log.info("✅ Store 데이터 생성 완료 (총 {}개)", stores.size());

        // ============================================
        // POS 생성 (39개)
        // ============================================
        List<POS> posList = new ArrayList<>();

        // 강남점 POS (3개)
        Member pos1_1 = memberRepository.findByEmail("pos.gangnam.01@hypelink.com");
        Member pos1_2 = memberRepository.findByEmail("pos.gangnam.02@hypelink.com");
        Member pos1_3 = memberRepository.findByEmail("pos.gangnam.03@hypelink.com");
        posList.add(POS.builder().posCode("STR001_01").healthCheck(true).store(stores.get(0)).member(pos1_1).build());
        posList.add(POS.builder().posCode("STR001_02").healthCheck(true).store(stores.get(0)).member(pos1_2).build());
        posList.add(POS.builder().posCode("STR001_03").healthCheck(true).store(stores.get(0)).member(pos1_3).build());

        // 홍대점 POS (3개)
        Member pos2_1 = memberRepository.findByEmail("pos.hongdae.01@hypelink.com");
        Member pos2_2 = memberRepository.findByEmail("pos.hongdae.02@hypelink.com");
        Member pos2_3 = memberRepository.findByEmail("pos.hongdae.03@hypelink.com");
        posList.add(POS.builder().posCode("STR002_01").healthCheck(false).store(stores.get(1)).member(pos2_1).build());
        posList.add(POS.builder().posCode("STR002_02").healthCheck(true).store(stores.get(1)).member(pos2_2).build());
        posList.add(POS.builder().posCode("STR002_03").healthCheck(true).store(stores.get(1)).member(pos2_3).build());

        // 잠실점 POS (5개)
        Member pos3_1 = memberRepository.findByEmail("pos.jamsil.01@hypelink.com");
        Member pos3_2 = memberRepository.findByEmail("pos.jamsil.02@hypelink.com");
        Member pos3_3 = memberRepository.findByEmail("pos.jamsil.03@hypelink.com");
        Member pos3_4 = memberRepository.findByEmail("pos.jamsil.04@hypelink.com");
        Member pos3_5 = memberRepository.findByEmail("pos.jamsil.05@hypelink.com");
        posList.add(POS.builder().posCode("STR003_01").healthCheck(false).store(stores.get(2)).member(pos3_1).build());
        posList.add(POS.builder().posCode("STR003_02").healthCheck(false).store(stores.get(2)).member(pos3_2).build());
        posList.add(POS.builder().posCode("STR003_03").healthCheck(true).store(stores.get(2)).member(pos3_3).build());
        posList.add(POS.builder().posCode("STR003_04").healthCheck(true).store(stores.get(2)).member(pos3_4).build());
        posList.add(POS.builder().posCode("STR003_05").healthCheck(false).store(stores.get(2)).member(pos3_5).build());

        // 분당점 POS (3개)
        Member pos4_1 = memberRepository.findByEmail("pos.bundang.01@hypelink.com");
        Member pos4_2 = memberRepository.findByEmail("pos.bundang.02@hypelink.com");
        Member pos4_3 = memberRepository.findByEmail("pos.bundang.03@hypelink.com");
        posList.add(POS.builder().posCode("STR004_01").healthCheck(false).store(stores.get(3)).member(pos4_1).build());
        posList.add(POS.builder().posCode("STR004_02").healthCheck(false).store(stores.get(3)).member(pos4_2).build());
        posList.add(POS.builder().posCode("STR004_03").healthCheck(true).store(stores.get(3)).member(pos4_3).build());

        // 인천점 POS (3개)
        Member pos5_1 = memberRepository.findByEmail("pos.incheon.01@hypelink.com");
        Member pos5_2 = memberRepository.findByEmail("pos.incheon.02@hypelink.com");
        Member pos5_3 = memberRepository.findByEmail("pos.incheon.03@hypelink.com");
        posList.add(POS.builder().posCode("STR005_01").healthCheck(false).store(stores.get(4)).member(pos5_1).build());
        posList.add(POS.builder().posCode("STR005_02").healthCheck(false).store(stores.get(4)).member(pos5_2).build());
        posList.add(POS.builder().posCode("STR005_03").healthCheck(false).store(stores.get(4)).member(pos5_3).build());

        // 부산 서면점 POS (5개)
        Member pos6_1 = memberRepository.findByEmail("pos.busan.seomyeon.01@hypelink.com");
        Member pos6_2 = memberRepository.findByEmail("pos.busan.seomyeon.02@hypelink.com");
        Member pos6_3 = memberRepository.findByEmail("pos.busan.seomyeon.03@hypelink.com");
        Member pos6_4 = memberRepository.findByEmail("pos.busan.seomyeon.04@hypelink.com");
        Member pos6_5 = memberRepository.findByEmail("pos.busan.seomyeon.05@hypelink.com");
        posList.add(POS.builder().posCode("STR006_01").healthCheck(true).store(stores.get(5)).member(pos6_1).build());
        posList.add(POS.builder().posCode("STR006_02").healthCheck(false).store(stores.get(5)).member(pos6_2).build());
        posList.add(POS.builder().posCode("STR006_03").healthCheck(false).store(stores.get(5)).member(pos6_3).build());
        posList.add(POS.builder().posCode("STR006_04").healthCheck(true).store(stores.get(5)).member(pos6_4).build());
        posList.add(POS.builder().posCode("STR006_05").healthCheck(false).store(stores.get(5)).member(pos6_5).build());

        // 대구 동성로점 POS (5개)
        Member pos7_1 = memberRepository.findByEmail("pos.daegu.dongseongro.01@hypelink.com");
        Member pos7_2 = memberRepository.findByEmail("pos.daegu.dongseongro.02@hypelink.com");
        Member pos7_3 = memberRepository.findByEmail("pos.daegu.dongseongro.03@hypelink.com");
        Member pos7_4 = memberRepository.findByEmail("pos.daegu.dongseongro.04@hypelink.com");
        Member pos7_5 = memberRepository.findByEmail("pos.daegu.dongseongro.05@hypelink.com");
        posList.add(POS.builder().posCode("STR007_01").healthCheck(false).store(stores.get(6)).member(pos7_1).build());
        posList.add(POS.builder().posCode("STR007_02").healthCheck(true).store(stores.get(6)).member(pos7_2).build());
        posList.add(POS.builder().posCode("STR007_03").healthCheck(false).store(stores.get(6)).member(pos7_3).build());
        posList.add(POS.builder().posCode("STR007_04").healthCheck(true).store(stores.get(6)).member(pos7_4).build());
        posList.add(POS.builder().posCode("STR007_05").healthCheck(false).store(stores.get(6)).member(pos7_5).build());

        // 광주 충장로점 POS (3개)
        Member pos8_1 = memberRepository.findByEmail("pos.gwangju.chungjangro.01@hypelink.com");
        Member pos8_2 = memberRepository.findByEmail("pos.gwangju.chungjangro.02@hypelink.com");
        Member pos8_3 = memberRepository.findByEmail("pos.gwangju.chungjangro.03@hypelink.com");
        posList.add(POS.builder().posCode("STR008_01").healthCheck(true).store(stores.get(7)).member(pos8_1).build());
        posList.add(POS.builder().posCode("STR008_02").healthCheck(false).store(stores.get(7)).member(pos8_2).build());
        posList.add(POS.builder().posCode("STR008_03").healthCheck(false).store(stores.get(7)).member(pos8_3).build());

        // 대전 둔산점 POS (4개)
        Member pos9_1 = memberRepository.findByEmail("pos.daejeon.dunsan.01@hypelink.com");
        Member pos9_2 = memberRepository.findByEmail("pos.daejeon.dunsan.02@hypelink.com");
        Member pos9_3 = memberRepository.findByEmail("pos.daejeon.dunsan.03@hypelink.com");
        Member pos9_4 = memberRepository.findByEmail("pos.daejeon.dunsan.04@hypelink.com");
        posList.add(POS.builder().posCode("STR009_01").healthCheck(true).store(stores.get(8)).member(pos9_1).build());
        posList.add(POS.builder().posCode("STR009_02").healthCheck(false).store(stores.get(8)).member(pos9_2).build());
        posList.add(POS.builder().posCode("STR009_03").healthCheck(true).store(stores.get(8)).member(pos9_3).build());
        posList.add(POS.builder().posCode("STR009_04").healthCheck(true).store(stores.get(8)).member(pos9_4).build());

        // 제주점 POS (4개)
        Member pos10_1 = memberRepository.findByEmail("pos.jeju.01@hypelink.com");
        Member pos10_2 = memberRepository.findByEmail("pos.jeju.02@hypelink.com");
        Member pos10_3 = memberRepository.findByEmail("pos.jeju.03@hypelink.com");
        Member pos10_4 = memberRepository.findByEmail("pos.jeju.04@hypelink.com");
        posList.add(POS.builder().posCode("STR010_01").healthCheck(true).store(stores.get(9)).member(pos10_1).build());
        posList.add(POS.builder().posCode("STR010_02").healthCheck(false).store(stores.get(9)).member(pos10_2).build());
        posList.add(POS.builder().posCode("STR010_03").healthCheck(true).store(stores.get(9)).member(pos10_3).build());
        posList.add(POS.builder().posCode("STR010_04").healthCheck(false).store(stores.get(9)).member(pos10_4).build());

        for (POS pos : posList) {
            posRepository.save(pos);
        }
        log.info("✅ POS 데이터 생성 완료 (총 {}개)", posList.size());
    }

    private void initDrivers() {
        // 기사 Member 조회
        Member driver1 = memberRepository.findByEmail("driver.gangnam@hypelink.com");
        Member driver2 = memberRepository.findByEmail("driver.hongdae@hypelink.com");
        Member driver3 = memberRepository.findByEmail("driver.jamsil@hypelink.com");
        Member driver4 = memberRepository.findByEmail("driver.bundang@hypelink.com");
        Member driver5 = memberRepository.findByEmail("driver.incheon@hypelink.com");
        Member driver6 = memberRepository.findByEmail("driver.busan.seomyeon@hypelink.com");
        Member driver7 = memberRepository.findByEmail("driver.daegu.dongseongro@hypelink.com");
        Member driver8 = memberRepository.findByEmail("driver.gwangju.chungjangro@hypelink.com");
        Member driver9 = memberRepository.findByEmail("driver.daejeon.dunsan@hypelink.com");
        Member driver10 = memberRepository.findByEmail("driver.jeju@hypelink.com");

        List<Driver> drivers = new ArrayList<>();
        drivers.add(Driver.builder().macAddress("50:50:2C:46:38:1F").carNumber("21가2483").member(driver1).build());
        drivers.add(Driver.builder().macAddress("2C:59:1A:4A:4E:42").carNumber("71가5565").member(driver2).build());
        drivers.add(Driver.builder().macAddress("46:20:1A:3E:3D:51").carNumber("48가5024").member(driver3).build());
        drivers.add(Driver.builder().macAddress("24:14:55:0F:25:40").carNumber("78가4767").member(driver4).build());
        drivers.add(Driver.builder().macAddress("0D:1D:5C:50:63:44").carNumber("58가2698").member(driver5).build());
        drivers.add(Driver.builder().macAddress("51:23:2B:17:21:5F").carNumber("58가1377").member(driver6).build());
        drivers.add(Driver.builder().macAddress("61:5C:15:1B:45:24").carNumber("38가5033").member(driver7).build());
        drivers.add(Driver.builder().macAddress("45:10:0D:55:58:40").carNumber("65가8649").member(driver8).build());
        drivers.add(Driver.builder().macAddress("1D:28:28:34:0E:13").carNumber("57가6114").member(driver9).build());
        drivers.add(Driver.builder().macAddress("48:5D:44:55:15:1E").carNumber("78가7569").member(driver10).build());

        for (Driver driver : drivers) {
            driverRepository.save(driver);
        }
        log.info("✅ Driver 데이터 생성 완료 (총 {}명)", drivers.size());
    }
}
