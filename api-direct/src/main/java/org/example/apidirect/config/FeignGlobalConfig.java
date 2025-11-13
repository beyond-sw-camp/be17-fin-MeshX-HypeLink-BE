package org.example.apidirect.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import org.example.apidirect.util.FeignCustomDecoder;
import org.springframework.context.annotation.Bean;

public class FeignGlobalConfig {

    @Bean
    public Decoder feignDecoder(ObjectMapper objectMapper) {
        return new FeignCustomDecoder(objectMapper);
    }
}
