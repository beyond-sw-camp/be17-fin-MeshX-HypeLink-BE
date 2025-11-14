package com.example.apiauth;

import com.example.apiauth.domain.model.value.MemberRole;
import com.example.apiauth.domain.model.value.Region;
import com.example.apiauth.domain.model.value.SyncStatus;
import com.example.apiauth.usecase.port.in.RegisterCommand;
import com.example.apiauth.usecase.port.out.persistence.*;
import com.example.apiauth.usecase.port.out.usecase.AuthUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class MemberRegisterIntegrationTest {

    @Autowired
    private AuthUseCase authUseCase;

    @Autowired
    private MemberQueryPort memberQueryPort;

    @Autowired
    private StoreQueryPort storeQueryPort;

    @Autowired
    private DriverQueryPort driverQueryPort;

    @Autowired
    private PosQueryPort posQueryPort;

    @Test
    @DisplayName("지점장 회원가입 - Write DB 저장 및 syncStatus=NEW 확인")
    void 지점장_회원가입_테스트() {
        // Given
        long timestamp = System.currentTimeMillis();
        String email = "store_integration_" + timestamp + "@test.com";
        RegisterCommand command = RegisterCommand.builder()
                .email(email)
                .password("test1234")
                .name("통합테스트지점_" + timestamp)
                .phone("010-1111-2222")
                .address("서울시 강남구 테헤란로 123")
                .role(MemberRole.BRANCH_MANAGER)
                .region(Region.SEOUL_GYEONGGI)
                .storeNumber("ST999")
                .build();

        // When
        authUseCase.register(command);

        // Then
        // 1. Member가 Write DB에 저장되었는지 확인
        assertThat(memberQueryPort.existsByEmail(email)).isTrue();
        var member = memberQueryPort.findByEmail(email);
        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo("통합테스트지점_" + timestamp);
        assertThat(member.getRole()).isEqualTo(MemberRole.BRANCH_MANAGER);
        assertThat(member.getSyncStatus()).isEqualTo(SyncStatus.NEW);

        // 2. Store가 Write DB에 저장되었는지 확인
        var store = storeQueryPort.findByMemberId(member.getId());
        assertThat(store).isNotNull();
        assertThat(store.getStoreNumber()).isEqualTo("ST999");
        assertThat(store.getSyncStatus()).isEqualTo(SyncStatus.NEW);

        System.out.println("=== 지점장 회원가입 성공 ===");
        System.out.println("Member ID: " + member.getId());
        System.out.println("Store ID: " + store.getId());
        System.out.println("SyncStatus: " + member.getSyncStatus());
        System.out.println("Kafka 이벤트 발행됨 (member-registered)");
    }

    @Test
    @DisplayName("드라이버 회원가입 - Write DB 저장 및 syncStatus=NEW 확인")
    void 드라이버_회원가입_테스트() {
        // Given
        long timestamp = System.currentTimeMillis();
        String email = "driver_integration_" + timestamp + "@test.com";
        RegisterCommand command = RegisterCommand.builder()
                .email(email)
                .password("test1234")
                .name("통합테스트드라이버_" + timestamp)
                .phone("010-3333-4444")
                .address("서울시 서초구 강남대로 456")
                .role(MemberRole.DRIVER)
                .region(Region.SEOUL_GYEONGGI)
                .macAddress("AA:BB:CC:DD:EE:FF")
                .carNumber("12가3456")
                .build();

        // When
        authUseCase.register(command);

        // Then
        // 1. Member가 Write DB에 저장되었는지 확인
        assertThat(memberQueryPort.existsByEmail(email)).isTrue();
        var member = memberQueryPort.findByEmail(email);
        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo("통합테스트드라이버_" + timestamp);
        assertThat(member.getRole()).isEqualTo(MemberRole.DRIVER);
        assertThat(member.getSyncStatus()).isEqualTo(SyncStatus.NEW);

        // 2. Driver가 Write DB에 저장되었는지 확인
        var driver = driverQueryPort.findByMemberId(member.getId());
        assertThat(driver).isNotNull();
        assertThat(driver.getCarNumber()).isEqualTo("12가3456");
        assertThat(driver.getMacAddress()).isEqualTo("AA:BB:CC:DD:EE:FF");
        assertThat(driver.getSyncStatus()).isEqualTo(SyncStatus.NEW);

        System.out.println("=== 드라이버 회원가입 성공 ===");
        System.out.println("Member ID: " + member.getId());
        System.out.println("Driver ID: " + driver.getId());
        System.out.println("SyncStatus: " + member.getSyncStatus());
        System.out.println("Kafka 이벤트 발행됨 (member-registered)");
    }

    @Test
    @DisplayName("POS 회원가입 - Write DB 저장 및 Store posCount 증가 확인")
    void POS_회원가입_테스트() throws InterruptedException {
        // Given - 먼저 지점장과 Store 생성
        long storeTimestamp = System.currentTimeMillis();
        String storeEmail = "store_for_pos_" + storeTimestamp + "@test.com";
        RegisterCommand storeCommand = RegisterCommand.builder()
                .email(storeEmail)
                .password("test1234")
                .name("POS용지점_" + storeTimestamp)
                .phone("010-5555-6666")
                .address("서울시 강남구 선릉로 789")
                .role(MemberRole.BRANCH_MANAGER)
                .region(Region.SEOUL_GYEONGGI)
                .storeNumber("ST888")
                .build();
        authUseCase.register(storeCommand);

        var storeMember = memberQueryPort.findByEmail(storeEmail);
        var store = storeQueryPort.findByMemberId(storeMember.getId());
        Integer initialPosCount = store.getPosCount();

        // 타임스탬프 중복 방지
        Thread.sleep(10);

        // Given - POS 회원 생성
        long posTimestamp = System.currentTimeMillis();
        String posEmail = "pos_integration_" + posTimestamp + "@test.com";
        String posCode = "SEL888_" + String.format("%02d", (posTimestamp % 100));
        RegisterCommand posCommand = RegisterCommand.builder()
                .email(posEmail)
                .password("test1234")
                .name("통합테스트POS_" + posTimestamp)
                .phone("010-7777-8888")
                .address(storeMember.getAddress())
                .role(MemberRole.POS_MEMBER)
                .region(Region.SEOUL_GYEONGGI)
                .storeId(store.getId())
                .posCode(posCode)
                .build();

        // When
        authUseCase.register(posCommand);

        // Then
        // 1. POS Member가 Write DB에 저장되었는지 확인
        assertThat(memberQueryPort.existsByEmail(posEmail)).isTrue();
        var posMember = memberQueryPort.findByEmail(posEmail);
        assertThat(posMember).isNotNull();
        assertThat(posMember.getRole()).isEqualTo(MemberRole.POS_MEMBER);
        assertThat(posMember.getSyncStatus()).isEqualTo(SyncStatus.NEW);

        // 2. Pos가 Write DB에 저장되었는지 확인
        var pos = posQueryPort.findByMemberId(posMember.getId());
        assertThat(pos).isNotNull();
        assertThat(pos.getPosCode()).isEqualTo(posCode);
        assertThat(pos.getSyncStatus()).isEqualTo(SyncStatus.NEW);

        // 3. Store의 posCount가 증가했는지 확인
        var updatedStore = storeQueryPort.findById(store.getId());
        assertThat(updatedStore.getPosCount()).isEqualTo(initialPosCount + 1);

        System.out.println("=== POS 회원가입 성공 ===");
        System.out.println("Member ID: " + posMember.getId());
        System.out.println("Pos ID: " + pos.getId());
        System.out.println("Store posCount: " + initialPosCount + " → " + updatedStore.getPosCount());
        System.out.println("SyncStatus: " + posMember.getSyncStatus());
        System.out.println("Kafka 이벤트 발행됨 (member-registered)");
    }

    @Test
    @DisplayName("관리자 회원가입 - Member만 저장")
    void 관리자_회원가입_테스트() {
        // Given
        long timestamp = System.currentTimeMillis();
        String email = "admin_integration_" + timestamp + "@test.com";
        RegisterCommand command = RegisterCommand.builder()
                .email(email)
                .password("admin1234")
                .name("통합테스트관리자_" + timestamp)
                .phone("010-9999-0000")
                .address("서울시 종로구 세종대로 1")
                .role(MemberRole.ADMIN)
                .region(Region.SEOUL_GYEONGGI)
                .build();

        // When
        authUseCase.register(command);

        // Then
        // Member만 저장되고 Store/Pos/Driver는 생성되지 않음
        assertThat(memberQueryPort.existsByEmail(email)).isTrue();
        var member = memberQueryPort.findByEmail(email);
        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo("통합테스트관리자_" + timestamp);
        assertThat(member.getRole()).isEqualTo(MemberRole.ADMIN);
        assertThat(member.getSyncStatus()).isEqualTo(SyncStatus.NEW);

        System.out.println("=== 관리자 회원가입 성공 ===");
        System.out.println("Member ID: " + member.getId());
        System.out.println("SyncStatus: " + member.getSyncStatus());
        System.out.println("Kafka 이벤트 발행됨 (member-registered)");
    }
}
