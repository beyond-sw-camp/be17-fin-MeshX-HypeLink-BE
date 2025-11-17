package com.example.apidirect.auth.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class POS {
    private Integer id;
    private String posCode;
    private Integer storeId;
    private Boolean healthCheck;
    private Integer memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updateHealthCheck(Boolean status) {
        this.healthCheck = status;
    }

    public boolean isHealthy() {
        return Boolean.TRUE.equals(this.healthCheck);
    }

    public String generateMerchantUidPrefix() {
        return this.posCode;
    }
}
