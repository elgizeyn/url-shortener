package com.urlshortener.request.converter;

import com.urlshortener.model.ShortUrl;
import com.urlshortener.request.ShortUrlRequest;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlRequestConverter {

    public ShortUrl convertToEntity(ShortUrlRequest request) {
        return ShortUrl.builder()
                .url(request.getUrl())
                .code(request.getCode())
                .build();
    }
}
