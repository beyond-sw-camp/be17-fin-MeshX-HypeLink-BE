package MeshX.HypeLink.common;

import MeshX.HypeLink.auth.model.entity.*;
import MeshX.HypeLink.auth.repository.DriverJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.PosJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
        boolean check = initMembers();
        if(!check) {
            initStoresAndPoses();
            initDrivers();
        }
    }

    private boolean initMembers() {
        try {
            memberRepository.findByEmail("hq@company.com");
            log.info("베이스 데이터 이미 존재");
            return true;
        } catch (RuntimeException e) {
            log.info("데이터 미 존재");
        }

        Member hq = Member.builder()
                .email("hq@company.com")
                .password(encoder.encode("1234"))
                .name("본사관리자")
                .phone("010-1111-1111")
                .address("서울특별시 강남구 테헤란로 1")
                .role(MemberRole.ADMIN)
                .region(Region.SEOUL_GYEONGGI)
                .refreshToken(null)
                .build();

        Member sq = Member.builder()
                .email("sq@company.com")
                .password(encoder.encode("1234"))
                .name("서브관리자")
                .phone("010-1111-1112")
                .address("서울특별시 강남구 테헤란로 1")
                .role(MemberRole.MANAGER)
                .region(Region.SEOUL_GYEONGGI)
                .refreshToken(null)
                .build();

        Member owner1 = Member.builder()
                .email("owner1@store.com")
                .password(encoder.encode("1234"))
                .name("가맹점주1")
                .phone("010-2222-2222")
                .address("서울특별시 중구 을지로 1가")
                .role(MemberRole.BRANCH_MANAGER)
                .region(Region.SEOUL_GYEONGGI)
                .refreshToken(null)
                .build();

        Member pos1 = Member.builder()
                .email("pos1@store1.com")
                .password(encoder.encode("1234"))
                .name("가맹점주1_pos1")
                .phone("010-3333-3334")
                .address("부산광역시 중구 중앙동")
                .role(MemberRole.POS_MEMBER)
                .region(Region.SEOUL_GYEONGGI)
                .refreshToken(null)
                .build();

        Member owner2 = Member.builder()
                .email("owner2@store.com")
                .password(encoder.encode("1234"))
                .name("가맹점주2")
                .phone("010-3333-4333")
                .address("부산광역시 중구 중앙동")
                .role(MemberRole.POS_MEMBER)
                .region(Region.JEOLLA)
                .refreshToken(null)
                .build();

        Member pos2 = Member.builder()
                .email("pos1@store2.com")
                .password(encoder.encode("1234"))
                .name("가맹점주2_pos1")
                .phone("010-3333-4334")
                .address("부산광역시 중구 중앙동")
                .role(MemberRole.BRANCH_MANAGER)
                .region(Region.JEOLLA)
                .refreshToken(null)
                .build();

        Member driver1 = Member.builder()
                .email("driver1@delivery.com")
                .password(encoder.encode("1234"))
                .name("기사1")
                .phone("010-4444-4444")
                .address("서울특별시 강북구 수유동")
                .role(MemberRole.DRIVER)
                .region(Region.SEOUL_GYEONGGI)
                .refreshToken(null)
                .build();

        Member driver2 = Member.builder()
                .email("driver2@delivery.com")
                .password(encoder.encode("1234"))
                .name("기사2")
                .phone("010-5555-5555")
                .address("부산광역시 해운대구 우동")
                .role(MemberRole.DRIVER)
                .region(Region.GYEONGSANG)
                .refreshToken(null)
                .build();

        memberRepository.save(hq);
        memberRepository.save(sq);
        memberRepository.save(owner1);
        memberRepository.save(owner2);
        memberRepository.save(pos1);
        memberRepository.save(pos2);
        memberRepository.save(driver1);
        memberRepository.save(driver2);

        log.info("✅ Member 기본 데이터 생성 완료");
        return false;
    }

    private void initStoresAndPoses() {
        Member owner1 = memberRepository.findByEmail("owner1@store.com");
        Member owner2 = memberRepository.findByEmail("owner2@store.com");

        Member pos_1 = memberRepository.findByEmail("pos1@store1.com");
        Member pos_2 = memberRepository.findByEmail("pos1@store2.com");

        Store store1 = Store.builder()
                .lat(37.5665)
                .lon(126.9780)
                .address("서울특별시 중구 을지로 1가")
                .posCount(2)
                .storeNumber("STORE001")
                .member(owner1)
                .build();

        Store store2 = Store.builder()
                .lat(35.1796)
                .lon(129.0756)
                .address("부산광역시 중구 중앙동")
                .posCount(2)
                .storeNumber("STORE002")
                .member(owner2)
                .build();

        storeRepository.save(store1);
        storeRepository.save(store2);

        POS pos1 = POS.builder()
                .posCode("STORE001-01")
                .healthCheck(true)
                .store(store1)
                .member(pos_1)
                .build();

        POS pos2 = POS.builder()
                .posCode("STORE002-01")
                .healthCheck(true)
                .store(store2)
                .member(pos_2)
                .build();

        posRepository.save(pos1);
        posRepository.save(pos2);

        log.info("✅ Store, POS 기본 데이터 생성 완료");
    }

    private void initDrivers() {
        Member driver1 = memberRepository.findByEmail("driver1@delivery.com");
        Member driver2 = memberRepository.findByEmail("driver2@delivery.com");

        Driver d1 = Driver.builder()
                .macAddress("AA:BB:CC:DD:EE:01")
                .carNumber("12가1234")
                .member(driver1)
                .build();

        Driver d2 = Driver.builder()
                .macAddress("AA:BB:CC:DD:EE:02")
                .carNumber("34나5678")
                .member(driver2)
                .build();

        driverRepository.save(d1);
        driverRepository.save(d2);

        log.info("✅ Driver 기본 데이터 생성 완료");
    }
}
