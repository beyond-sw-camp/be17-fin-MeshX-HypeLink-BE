package com.example.apidirect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.apidirect", "com.example.apiclients"})
public class ApiDirectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDirectApplication.class, args);
    }

}
