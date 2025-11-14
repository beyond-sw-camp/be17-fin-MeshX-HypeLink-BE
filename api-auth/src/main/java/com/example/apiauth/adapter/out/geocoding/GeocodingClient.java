package com.example.apiauth.adapter.out.geocoding;

import com.example.apiauth.adapter.out.geocoding.dto.NaverGeocodeResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
public class GeocodingClient {

    @Value("${naver.api.client-id}")
    private String clientId;

    @Value("${naver.api.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public NaverGeocodeResponseDto callNaverGeocodeApi(String cleanedAddress) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://maps.apigw.ntruss.com")
                .path("/map-geocode/v2/geocode")
                .queryParam("query", cleanedAddress)
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", clientSecret);
        headers.setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<NaverGeocodeResponseDto> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                NaverGeocodeResponseDto.class
        );

        return response.getBody();
    }
}
