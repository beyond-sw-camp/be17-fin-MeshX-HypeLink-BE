package MeshX.HypeLink.utils.geocode.service;

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
        URI uri = UriComponentsBuilder
                .fromUriString("https://naveropenapi.apigw.ntruss.com")
                .path("/map-geocode/v2/geocode")
                .queryParam("query", address)
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", clientSecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<NaverGeocodeResponseDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, NaverGeocodeResponseDto.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().getAddresses() != null && !response.getBody().getAddresses().isEmpty()) {
                return response.getBody().getAddresses().get(0);
            } else {
                log.warn("Geocoding returned no results for address: {}. Status: {}", address, response.getStatusCode());
                throw new GeocodingException(GeocodingExceptionMessage.NO_RESULTS_FOUND);
            }
        } catch (RestClientException e) {
            log.error("Error during geocoding REST call for address: {}", address, e);
            throw new GeocodingException(GeocodingExceptionMessage.GEOCODING_FAILED);
        }
    }
}
