package com.example.apiauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
public class ApiAuthyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiAuthyApplication.class, args);
    }


}
