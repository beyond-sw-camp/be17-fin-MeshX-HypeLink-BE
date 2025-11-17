package com.example.apidirect.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import com.example.apidirect.util.FeignCustomDecoder;
import org.springframework.context.annotation.Bean;

public class FeignGlobalConfig {

    @Bean
    public Decoder customFeignDecoder(ObjectMapper objectMapper) {
        return new FeignCustomDecoder(objectMapper);
    }
}
