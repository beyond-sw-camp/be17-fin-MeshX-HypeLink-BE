package com.example.apidirect.auth.adapter.in.kafka;

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
    private MemberRole role;
    private StoreInfo storeInfo;
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
    public static class PosInfo {
        private Integer posId;
        private String posCode;
        private Integer storeId;
        private Boolean healthCheck;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
