package com.example.apiauth.adapter.in.web.dto;

import com.example.apiauth.domain.model.value.Region;
import com.example.apiauth.domain.model.value.StoreState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class StoreWithPosResDto {
    private Integer id;
    private String name;
    private String address;
    private Region region;
    private StoreState storeState;
    private List<PosInfoDto> posDevices;

}
