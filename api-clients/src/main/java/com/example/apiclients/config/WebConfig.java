package com.example.apiclients.config;

import com.example.apiclients.client.AuthApiClient;
import com.example.apiclients.resolver.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    private GetStoreIdResolver getStoreIdResolver;

    @Autowired(required = false)
    private GetPosIdResolver getPosIdResolver;

    @Autowired(required = false)
    private GetDriverIdResolver getDriverIdResolver;

    @Bean
    public GetMemberIdResolver getMemberIdResolver() {
        return new GetMemberIdResolver();
    }

    @Bean
    public GetMemberEmailResolver getMemberEmailResolver() {
        return new GetMemberEmailResolver();
    }

    @Bean
    @ConditionalOnBean(AuthApiClient.class)
    public GetStoreIdResolver getStoreIdResolver(AuthApiClient authApiClient) {
        return new GetStoreIdResolver(authApiClient);
    }

    @Bean
    @ConditionalOnBean(AuthApiClient.class)
    public GetPosIdResolver getPosIdResolver(AuthApiClient authApiClient) {
        return new GetPosIdResolver(authApiClient);
    }

    @Bean
    @ConditionalOnBean(AuthApiClient.class)
    public GetDriverIdResolver getDriverIdResolver(AuthApiClient authApiClient) {
        return new GetDriverIdResolver(authApiClient);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(getMemberIdResolver());
        resolvers.add(getMemberEmailResolver());
        if (getStoreIdResolver != null) {
            resolvers.add(getStoreIdResolver);
        }
        if (getPosIdResolver != null) {
            resolvers.add(getPosIdResolver);
        }
        if (getDriverIdResolver != null) {
            resolvers.add(getDriverIdResolver);
        }
    }
}
