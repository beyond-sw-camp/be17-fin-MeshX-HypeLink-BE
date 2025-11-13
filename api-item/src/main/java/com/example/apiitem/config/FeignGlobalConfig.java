package com.example.apiitem.config;

import com.example.apiitem.util.FeignCustomDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignGlobalConfig {
    @Bean
    public Decoder feignDecoder(ObjectMapper objectMapper) {
        return new FeignCustomDecoder(objectMapper);
    }
}