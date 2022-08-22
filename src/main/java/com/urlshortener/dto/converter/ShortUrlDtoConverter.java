package com.urlshortener.dto.converter;


import com.urlshortener.dto.ShortUrlDto;
import com.urlshortener.model.ShortUrl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShortUrlDtoConverter {

    public ShortUrlDto covertToDto(ShortUrl shortUrl) {
        return ShortUrlDto.builder()
                .id(shortUrl.getId())
                .url(shortUrl.getUrl())
                .code(shortUrl.getCode())
                .build();
    }

    public List<ShortUrlDto> convertListDto(List<ShortUrl> shortUrlList) {
        return shortUrlList.stream()
                .map(this::covertToDto)
                .collect(Collectors.toList());
    }
}
