package com.example.apiauth.domain.kafka;

import com.example.apiauth.domain.model.Driver;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.domain.model.Pos;
import com.example.apiauth.domain.model.Store;
import com.example.apiauth.domain.model.value.MemberRole;
import com.example.apiauth.domain.model.value.Region;
import com.example.apiauth.domain.model.value.StoreState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterEvent {

    private Integer memberId;
    private String email;
    private String name;
    private String phone;
    private String address;
    private MemberRole role;
    private Region region;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private StoreInfo storeInfo;
    private DriverInfo driverInfo;
    private PosInfo posInfo;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreInfo {
        private Integer storeId;
        private Double lat;
        private Double lon;
        private String storeNumber;
        private StoreState storeState;
        private Integer posCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DriverInfo {
        private Integer driverId;
        private String macAddress;
        private String carNumber;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PosInfo {
        private Integer posId;
        private String posCode;
        private Integer storeId;
        private Boolean healthCheck;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    public static MemberRegisterEvent of(Member member, Store store, Driver driver, Pos pos) {
        return MemberRegisterEvent.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .address(member.getAddress())
                .role(member.getRole())
                .region(member.getRegion())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .storeInfo(store != null ? store.toEventInfo() : null)
                .driverInfo(driver != null ? driver.toEventInfo() : null)
                .posInfo(pos != null ? pos.toEventInfo() : null)
                .build();
    }
}
