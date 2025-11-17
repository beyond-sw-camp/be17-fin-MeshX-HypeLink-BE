package com.example.apiauth.adapter.out.geocoding.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class GeocodeDto {
    @JsonProperty("x")
    private String lon;

    @JsonProperty("y")
    private String lat;

    public Double getLonAsDouble() {
        return lon != null ? Double.parseDouble(lon) : null;
    }

    public Double getLatAsDouble() {
        return lat != null ? Double.parseDouble(lat) : null;
    }
}

