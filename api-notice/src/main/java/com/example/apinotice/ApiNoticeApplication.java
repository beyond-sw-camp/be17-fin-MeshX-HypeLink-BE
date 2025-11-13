package com.example.apinotice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ApiNoticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiNoticeApplication.class, args);
    }

}
