package com.example.apiclients.config;

import com.example.apiclients.resolver.GetMemberEmailResolver;
import com.example.apiclients.resolver.GetMemberIdResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public GetMemberIdResolver getMemberIdResolver() {
        return new GetMemberIdResolver();
    }

    @Bean
    public GetMemberEmailResolver getMemberEmailResolver() {
        return new GetMemberEmailResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(getMemberIdResolver());
        resolvers.add(getMemberEmailResolver());
    }
}
