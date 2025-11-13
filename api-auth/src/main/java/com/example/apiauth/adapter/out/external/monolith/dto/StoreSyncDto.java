package com.example.apiauth.adapter.out.external.monolith.dto;

import com.example.apiauth.domain.model.value.StoreState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private StoreState storeState;
    private Integer memberId;
}
