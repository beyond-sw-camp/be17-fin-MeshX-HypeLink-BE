package org.example.apidirect.auth.adapter.out.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosSyncDto {
    private Integer id;
    private String posCode;
    private Integer storeId;
    private Boolean healthCheck;
    private Integer memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
