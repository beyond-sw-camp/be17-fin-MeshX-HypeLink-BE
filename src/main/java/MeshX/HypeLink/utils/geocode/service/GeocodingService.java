package MeshX.HypeLink.utils.geocode.service;

import MeshX.HypeLink.utils.AddressCleaner;
import MeshX.HypeLink.utils.geocode.exception.GeocodingException;
import MeshX.HypeLink.utils.geocode.exception.GeocodingExceptionMessage;
import MeshX.HypeLink.utils.geocode.model.dto.GeocodeDto;
import MeshX.HypeLink.utils.geocode.model.dto.NaverGeocodeResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class GeocodingService {

    @Value("${naver.api.client-id}")
    private String clientId;

    @Value("${naver.api.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public GeocodeDto getCoordinates(String address) {
        // 사용자 정의 AddressCleaner를 사용하여 주소 정제
        String cleanedAddress = AddressCleaner.clean(address);

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

        try {
            ResponseEntity<NaverGeocodeResponseDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, NaverGeocodeResponseDto.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().getAddresses() != null && !response.getBody().getAddresses().isEmpty()) {
                return response.getBody().getAddresses().get(0);
            } else {
                log.warn("Geocoding returned no results for cleaned address: {}. Status: {}", cleanedAddress, response.getStatusCode());
                throw new GeocodingException(GeocodingExceptionMessage.NO_RESULTS_FOUND);
            }
        } catch (RestClientException e) {
            log.error("Error during geocoding REST call for cleaned address: {}", cleanedAddress, e);
            throw new GeocodingException(GeocodingExceptionMessage.GEOCODING_FAILED);
        }
    }
}
