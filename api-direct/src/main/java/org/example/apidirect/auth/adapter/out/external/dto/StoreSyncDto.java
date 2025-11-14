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
public class StoreSyncDto {
    private Integer id;
    private Double lat;
    private Double lon;
    private Integer posCount;
    private String storeNumber;
    private String storeState;
    private Integer memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
