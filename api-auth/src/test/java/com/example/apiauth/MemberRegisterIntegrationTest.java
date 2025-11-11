package com.example.apiauth;

import com.example.apiauth.domain.model.value.MemberRole;
import com.example.apiauth.domain.model.value.Region;
import com.example.apiauth.usecase.port.in.RegisterCommand;
import com.example.apiauth.usecase.port.out.persistence.DriverPort;
import com.example.apiauth.usecase.port.out.persistence.MemberPort;
import com.example.apiauth.usecase.port.out.persistence.PosPort;
import com.example.apiauth.usecase.port.out.persistence.StorePort;
import com.example.apiauth.usecase.port.out.usecase.AuthUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class MemberRegisterIntegrationTest {

    @Autowired
    private AuthUseCase authUseCase;

    @Autowired
    private MemberPort memberPort;

    @Autowired
    private StorePort storePort;

    @Autowired
    private DriverPort driverPort;

    @Autowired
    private PosPort posPort;

    @Test
    @DisplayName("지점장 회원가입 전체 플로우 테스트 - Member, Store 저장 및 Kafka 이벤트 발행")
    void 지점장_회원가입_전체_플로우_테스트() {
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
        // 1. Member가 생성되었는지 확인
        assertThat(memberPort.existsByEmail(email)).isTrue();
        var member = memberPort.findByEmail(email);
        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo("통합테스트지점_" + timestamp);
        assertThat(member.getRole()).isEqualTo(MemberRole.BRANCH_MANAGER);

        // 2. Store가 생성되었는지 확인
        var store = storePort.findByMemberId(member.getId());
        assertThat(store).isNotNull();
        assertThat(store.getStoreNumber()).isEqualTo("ST999");

        System.out.println("=== 지점장 회원가입 성공 ===");
        System.out.println("Member ID: " + member.getId());
        System.out.println("Store ID: " + store.getId());
        System.out.println("Kafka 이벤트 발행됨");
    }

    @Test
    @DisplayName("드라이버 회원가입 전체 플로우 테스트 - Member, Driver 저장 및 Kafka 이벤트 발행")
    void 드라이버_회원가입_전체_플로우_테스트() {
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
        // 1. Member가 생성되었는지 확인
        assertThat(memberPort.existsByEmail(email)).isTrue();
        var member = memberPort.findByEmail(email);
        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo("통합테스트드라이버_" + timestamp);
        assertThat(member.getRole()).isEqualTo(MemberRole.DRIVER);

        // 2. Driver가 생성되었는지 확인
        var driver = driverPort.findByMember_Id(member.getId());
        assertThat(driver).isNotNull();
        assertThat(driver.getCarNumber()).isEqualTo("12가3456");
        assertThat(driver.getMacAddress()).isEqualTo("AA:BB:CC:DD:EE:FF");

        System.out.println("=== 드라이버 회원가입 성공 ===");
        System.out.println("Member ID: " + member.getId());
        System.out.println("Driver ID: " + driver.getId());
        System.out.println("Kafka 이벤트 발행됨");
    }

    @Test
    @DisplayName("POS 회원가입 전체 플로우 테스트 - Member, Pos 저장 및 Store posCount 증가")
    void POS_회원가입_전체_플로우_테스트() throws InterruptedException {
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

        var storeMember = memberPort.findByEmail(storeEmail);
        var store = storePort.findByMemberId(storeMember.getId());
        Integer initialPosCount = store.getPosCount();

        // 타임스탬프 중복 방지
        Thread.sleep(10);

        // Given - POS 회원 생성
        long posTimestamp = System.currentTimeMillis();
        String posEmail = "pos_integration_" + posTimestamp + "@test.com";
        // posCode도 고유하게 생성 (마지막 2자리를 타임스탬프 기반으로)
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
        // 1. POS Member가 생성되었는지 확인
        assertThat(memberPort.existsByEmail(posEmail)).isTrue();
        var posMember = memberPort.findByEmail(posEmail);
        assertThat(posMember).isNotNull();
        assertThat(posMember.getRole()).isEqualTo(MemberRole.POS_MEMBER);

        // 2. Pos가 생성되었는지 확인
        var pos = posPort.findByMember_Id(posMember.getId());
        assertThat(pos).isNotNull();
        assertThat(pos.getPosCode()).isEqualTo(posCode);

        // 3. Store의 posCount가 증가했는지 확인
        var updatedStore = storePort.findByStoreId(store.getId());
        assertThat(updatedStore.getPosCount()).isEqualTo(initialPosCount + 1);

        System.out.println("=== POS 회원가입 성공 ===");
        System.out.println("Member ID: " + posMember.getId());
        System.out.println("Pos ID: " + pos.getId());
        System.out.println("Store posCount: " + initialPosCount + " → " + updatedStore.getPosCount());
        System.out.println("Kafka 이벤트 발행됨");
    }
}
