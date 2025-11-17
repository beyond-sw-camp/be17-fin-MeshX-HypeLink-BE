package com.example.apiauth.adapter.out.geocoding;

import com.example.apiauth.adapter.out.geocoding.dto.GeocodeDto;
import com.example.apiauth.adapter.out.geocoding.dto.NaverGeocodeResponseDto;
import com.example.apiauth.common.exception.GeocodingException;
import com.example.apiauth.common.exception.GeocodingExceptionMessage;
import com.example.apiauth.common.utils.AddressCleaner;
import com.example.apiauth.usecase.port.out.external.GeocodingPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeocodingAdapter implements GeocodingPort {

    private final GeocodingClient geocodingClient;

    @Override
    @CircuitBreaker(name = "geocoding", fallbackMethod = "getCoordinatesFallback")
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

    private GeocodeDto getCoordinatesFallback(String address, Exception e) {
        log.error("지오코딩 서킷브레이커 작동! address: {}, error: {}", address, e.getMessage());
        //TODO : 이상하게 저장된 주소를 따로 DB에 저장해서 배치테이블 돌려서 그거 정상화 시키기
        return GeocodeDto.builder()
                .lon("126.9780")  // 경도
                .lat("37.5665")   // 위도
                .build();
    }
}
