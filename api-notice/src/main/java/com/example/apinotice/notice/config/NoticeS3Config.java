package com.example.apinotice.notice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoticeS3Config {
    public static String BASE_URL;

    @Value("${notice.s3.base-url}")
    public void setBaseUrl(String baseUrl) {
        NoticeS3Config.BASE_URL = baseUrl;
    }
}

