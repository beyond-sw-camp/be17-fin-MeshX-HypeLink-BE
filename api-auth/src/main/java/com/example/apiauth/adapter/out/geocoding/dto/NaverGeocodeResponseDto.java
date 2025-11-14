package com.example.apiauth.adapter.out.geocoding.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NaverGeocodeResponseDto {
    @JsonProperty("addresses")
    private List<GeocodeDto> addresses;
}
