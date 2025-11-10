package com.example.apiauth.usecase.port.out.external;

import com.example.apiauth.adapter.out.external.geocoding.dto.GeocodeDto;

public interface GeocodingPort {
    GeocodeDto getCoordinates(String address);
}
