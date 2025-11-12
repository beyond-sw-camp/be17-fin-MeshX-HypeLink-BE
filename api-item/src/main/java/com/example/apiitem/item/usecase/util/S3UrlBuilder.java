package com.example.apiitem.item.usecase.util;

import org.springframework.stereotype.Component;

@Component
public class S3UrlBuilder {
    public String buildPublicUrl(String s3Key) {
        return "/" + s3Key;
    }
}

