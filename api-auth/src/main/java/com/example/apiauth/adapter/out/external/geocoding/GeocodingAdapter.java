package com.example.apiauth.adapter.out.external.geocoding;

import com.example.apiauth.adapter.out.external.geocoding.dto.GeocodeDto;
import com.example.apiauth.adapter.out.external.geocoding.dto.NaverGeocodeResponseDto;
import com.example.apiauth.common.exception.GeocodingException;
import com.example.apiauth.common.exception.GeocodingExceptionMessage;
import com.example.apiauth.common.utils.AddressCleaner;
import com.example.apiauth.usecase.port.out.external.GeocodingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Component
@RequiredArgsConstructor
public class GeocodingAdapter implements GeocodingPort {

    private final GeocodingClient geocodingClient;

    @Override
    public GeocodeDto getCoordinates(String address) {
        String cleanedAddress = AddressCleaner.clean(address);

        try {
            NaverGeocodeResponseDto response = geocodingClient.callNaverGeocodeApi(cleanedAddress);

            if (response != null
                    && response.getAddresses() != null
                    && !response.getAddresses().isEmpty()) {
                return response.getAddresses().get(0);
            } else {
                throw new GeocodingException(GeocodingExceptionMessage.NO_RESULTS_FOUND);
            }
        } catch (RestClientException e) {
            throw new GeocodingException(GeocodingExceptionMessage.GEOCODING_FAILED);
        }
    }
}
