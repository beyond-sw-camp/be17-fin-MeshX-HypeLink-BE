package MeshX.HypeLink.common;

import MeshX.HypeLink.auth.model.entity.*;
import MeshX.HypeLink.auth.repository.DriverJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.PosJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.head_office.customer.repository.CustomerJpaRepositoryVerify;
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
    private final CustomerJpaRepositoryVerify customerRepository; // Add CustomerJpaRepositoryVerify
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
        } catch (BaseException e) {
            log.info("데이터 미 존재");
        }

        // Admin and Manager
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

        Member manager = Member.builder()
                .email("manager@company.com")
                .password(encoder.encode("1234"))
                .name("서브관리자")
                .phone("010-1111-1112")
                .address("서울특별시 강남구 테헤란로 1")
                .role(MemberRole.MANAGER)
                .region(Region.SEOUL_GYEONGGI)
                .refreshToken(null)
                .build();

        Member manager2 = Member.builder()
                .email("manager2@company.com")
                .password(encoder.encode("1234"))
                .name("서브관리자2")
                .phone("010-1111-1113")
                .address("서울특별시 강남구 테헤란로 1")
                .role(MemberRole.MANAGER)
                .region(Region.SEOUL_GYEONGGI)
                .refreshToken(null)
                .build();

        Member manager3 = Member.builder()
                .email("manager3@company.com")
                .password(encoder.encode("1234"))
                .name("서브관리자3")
                .phone("010-1111-1114")
                .address("서울특별시 강남구 테헤란로 1")
                .role(MemberRole.MANAGER)
                .region(Region.SEOUL_GYEONGGI)
                .refreshToken(null)
                .build();

        // Branch Managers (5 stores)
        Member owner1 = Member.builder().email("owner1@store.com").password(encoder.encode("1234")).name("가맹점주1").phone("010-2222-2221").address("서울특별시 중구 을지로 1가").role(MemberRole.BRANCH_MANAGER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build();
        Member owner2 = Member.builder().email("owner2@store.com").password(encoder.encode("1234")).name("가맹점주2").phone("010-2222-2222").address("부산광역시 중구 중앙동").role(MemberRole.BRANCH_MANAGER).region(Region.GYEONGSANG).refreshToken(null).build();
        Member owner3 = Member.builder().email("owner3@store.com").password(encoder.encode("1234")).name("가맹점주3").phone("010-2222-2223").address("대전광역시 서구 둔산동").role(MemberRole.BRANCH_MANAGER).region(Region.CHUNGCHEONG).refreshToken(null).build();
        Member owner4 = Member.builder().email("owner4@store.com").password(encoder.encode("1234")).name("가맹점주4").phone("010-2222-2224").address("광주광역시 서구 상무지구").role(MemberRole.BRANCH_MANAGER).region(Region.JEOLLA).refreshToken(null).build();
        Member owner5 = Member.builder().email("owner5@store.com").password(encoder.encode("1234")).name("가맹점주5").phone("010-2222-2225").address("제주특별자치도 제주시").role(MemberRole.BRANCH_MANAGER).region(Region.JEOLLA).refreshToken(null).build();

        // POS Members (2 per store = 10 total)
        Member pos1_1 = Member.builder().email("pos1_1@store.com").password(encoder.encode("1234")).name("가맹점주1_POS1").phone("010-3333-3331").address("서울특별시 중구 을지로 1가").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build();
        Member pos1_2 = Member.builder().email("pos1_2@store.com").password(encoder.encode("1234")).name("가맹점주1_POS2").phone("010-3333-3332").address("서울특별시 중구 을지로 1가").role(MemberRole.POS_MEMBER).region(Region.SEOUL_GYEONGGI).refreshToken(null).build();
        Member pos2_1 = Member.builder().email("pos2_1@store.com").password(encoder.encode("1234")).name("가맹점주2_POS1").phone("010-3333-3333").address("부산광역시 중구 중앙동").role(MemberRole.POS_MEMBER).region(Region.GYEONGSANG).refreshToken(null).build();
        Member pos2_2 = Member.builder().email("pos2_2@store.com").password(encoder.encode("1234")).name("가맹점주2_POS2").phone("010-3333-3334").address("부산광역시 중구 중앙동").role(MemberRole.POS_MEMBER).region(Region.GYEONGSANG).refreshToken(null).build();
        Member pos3_1 = Member.builder().email("pos3_1@store.com").password(encoder.encode("1234")).name("가맹점주3_POS1").phone("010-3333-3335").address("대전광역시 서구 둔산동").role(MemberRole.POS_MEMBER).region(Region.CHUNGCHEONG).refreshToken(null).build();
        Member pos3_2 = Member.builder().email("pos3_2@store.com").password(encoder.encode("1234")).name("가맹점주3_POS2").phone("010-3333-3336").address("대전광역시 서구 둔산동").role(MemberRole.POS_MEMBER).region(Region.CHUNGCHEONG).refreshToken(null).build();
        Member pos4_1 = Member.builder().email("pos4_1@store.com").password(encoder.encode("1234")).name("가맹점주4_POS1").phone("010-3333-3337").address("광주광역시 서구 상무지구").role(MemberRole.POS_MEMBER).region(Region.JEOLLA).refreshToken(null).build();
        Member pos4_2 = Member.builder().email("pos4_2@store.com").password(encoder.encode("1234")).name("가맹점주4_POS2").phone("010-3333-3338").address("광주광역시 서구 상무지구").role(MemberRole.POS_MEMBER).region(Region.JEOLLA).refreshToken(null).build();
        Member pos5_1 = Member.builder().email("pos5_1@store.com").password(encoder.encode("1234")).name("가맹점주5_POS1").phone("010-3333-3339").address("제주특별자치도 제주시").role(MemberRole.POS_MEMBER).region(Region.JEOLLA).refreshToken(null).build();
        Member pos5_2 = Member.builder().email("pos5_2@store.com").password(encoder.encode("1234")).name("가맹점주5_POS2").phone("010-3333-3340").address("제주특별자치도 제주시").role(MemberRole.POS_MEMBER).region(Region.JEOLLA).refreshToken(null).build();

        // Drivers (2) - Keep existing drivers
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
        memberRepository.save(manager);
        memberRepository.save(manager2);
        memberRepository.save(manager3);
        memberRepository.save(owner1);
        memberRepository.save(owner2);
        memberRepository.save(owner3);
        memberRepository.save(owner4);
        memberRepository.save(owner5);
        memberRepository.save(pos1_1);
        memberRepository.save(pos1_2);
        memberRepository.save(pos2_1);
        memberRepository.save(pos2_2);
        memberRepository.save(pos3_1);
        memberRepository.save(pos3_2);
        memberRepository.save(pos4_1);
        memberRepository.save(pos4_2);
        memberRepository.save(pos5_1);
        memberRepository.save(pos5_2);
        memberRepository.save(driver1);
        memberRepository.save(driver2);

        log.info("✅ Member 기본 데이터 생성 완료");
        return false;
    }

    private void initStoresAndPoses() {
        Member owner1 = memberRepository.findByEmail("owner1@store.com");
        Member owner2 = memberRepository.findByEmail("owner2@store.com");
        Member owner3 = memberRepository.findByEmail("owner3@store.com");
        Member owner4 = memberRepository.findByEmail("owner4@store.com");
        Member owner5 = memberRepository.findByEmail("owner5@store.com");

        Member pos1_1 = memberRepository.findByEmail("pos1_1@store.com");
        Member pos1_2 = memberRepository.findByEmail("pos1_2@store.com");
        Member pos2_1 = memberRepository.findByEmail("pos2_1@store.com");
        Member pos2_2 = memberRepository.findByEmail("pos2_2@store.com");
        Member pos3_1 = memberRepository.findByEmail("pos3_1@store.com");
        Member pos3_2 = memberRepository.findByEmail("pos3_2@store.com");
        Member pos4_1 = memberRepository.findByEmail("pos4_1@store.com");
        Member pos4_2 = memberRepository.findByEmail("pos4_2@store.com");
        Member pos5_1 = memberRepository.findByEmail("pos5_1@store.com");
        Member pos5_2 = memberRepository.findByEmail("pos5_2@store.com");

        Store store1 = Store.builder().lat(37.5665).lon(126.9780).posCount(2).storeNumber("STORE001").member(owner1).storeState(StoreState.OPEN).build();
        Store store2 = Store.builder().lat(35.1796).lon(129.0756).posCount(2).storeNumber("STORE002").member(owner2).storeState(StoreState.OPEN).build();
        Store store3 = Store.builder().lat(36.3504).lon(127.3845).posCount(2).storeNumber("STORE003").member(owner3).storeState(StoreState.OPEN).build();
        Store store4 = Store.builder().lat(35.1595).lon(126.8526).posCount(2).storeNumber("STORE004").member(owner4).storeState(StoreState.OPEN).build();
        Store store5 = Store.builder().lat(33.4996).lon(126.5312).posCount(2).storeNumber("STORE005").member(owner5).storeState(StoreState.OPEN).build();

        storeRepository.save(store1);
        storeRepository.save(store2);
        storeRepository.save(store3);
        storeRepository.save(store4);
        storeRepository.save(store5);

        POS pos_s1_1 = POS.builder().posCode("STORE001-01").healthCheck(true).store(store1).member(pos1_1).build();
        POS pos_s1_2 = POS.builder().posCode("STORE001-02").healthCheck(true).store(store1).member(pos1_2).build();
        POS pos_s2_1 = POS.builder().posCode("STORE002-01").healthCheck(true).store(store2).member(pos2_1).build();
        POS pos_s2_2 = POS.builder().posCode("STORE002-02").healthCheck(true).store(store2).member(pos2_2).build();
        POS pos_s3_1 = POS.builder().posCode("STORE003-01").healthCheck(true).store(store3).member(pos3_1).build();
        POS pos_s3_2 = POS.builder().posCode("STORE003-02").healthCheck(true).store(store3).member(pos3_2).build();
        POS pos_s4_1 = POS.builder().posCode("STORE004-01").healthCheck(true).store(store4).member(pos4_1).build();
        POS pos_s4_2 = POS.builder().posCode("STORE004-02").healthCheck(true).store(store4).member(pos4_2).build();
        POS pos_s5_1 = POS.builder().posCode("STORE005-01").healthCheck(true).store(store5).member(pos5_1).build();
        POS pos_s5_2 = POS.builder().posCode("STORE005-02").healthCheck(true).store(store5).member(pos5_2).build();

        posRepository.save(pos_s1_1);
        posRepository.save(pos_s1_2);
        posRepository.save(pos_s2_1);
        posRepository.save(pos_s2_2);
        posRepository.save(pos_s3_1);
        posRepository.save(pos_s3_2);
        posRepository.save(pos_s4_1);
        posRepository.save(pos_s4_2);
        posRepository.save(pos_s5_1);
        posRepository.save(pos_s5_2);

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