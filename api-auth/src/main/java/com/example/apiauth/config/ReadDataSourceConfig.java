package com.example.apiauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.apiauth.adapter.out.persistence.read",
        entityManagerFactoryRef = "readEntityManagerFactory"
)
public class ReadDataSourceConfig {
    // Read DB용 Repository 자동 스캔
    // - basePackages: "com.example.apiauth.adapter.out.persistence.read"
    // - readEntityManagerFactory 사용 (DataSourceConfig에서 정의됨)
}
