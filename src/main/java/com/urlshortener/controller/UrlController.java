package com.urlshortener.controller;


import com.urlshortener.dto.ShortUrlDto;
import com.urlshortener.dto.converter.ShortUrlDtoConverter;
import com.urlshortener.model.ShortUrl;
import com.urlshortener.request.ShortUrlRequest;
import com.urlshortener.request.converter.ShortUrlRequestConverter;
import com.urlshortener.service.ShortUrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UrlController {


    private final ShortUrlDtoConverter shortUrlDtoConverter;
    private final ShortUrlRequestConverter shortUrlRequestConverter;
    private final ShortUrlService service;

    public UrlController(ShortUrlDtoConverter shortUrlDtoConverter, ShortUrlRequestConverter shortUrlRequestConverter, ShortUrlService service) {
        this.shortUrlDtoConverter = shortUrlDtoConverter;
        this.shortUrlRequestConverter = shortUrlRequestConverter;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ShortUrlDto>> getAllUrls() {
        return new ResponseEntity<>(
                shortUrlDtoConverter.convertListDto(service.getAllShortUrls()), HttpStatus.OK);

    }

    @GetMapping("/show/{code}")
    public ResponseEntity<ShortUrlDto> getUrlByCode(@Valid @NotEmpty @PathVariable String code) {
        return new ResponseEntity<>(
                shortUrlDtoConverter.covertToDto(service.getUrlByCode(code)), HttpStatus.FOUND);

    }

    @GetMapping("/{code}")
    public ResponseEntity<ShortUrlDto> redirect(@Valid @NotEmpty @PathVariable String code) throws URISyntaxException {
        ShortUrl shortUrl = service.getUrlByCode(code);
        URI uri = new URI(shortUrl.getUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(
                httpHeaders, HttpStatus.SEE_OTHER);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ShortUrlRequest shortUrlRequest) {
        ShortUrl shortUrl = shortUrlRequestConverter.convertToEntity(shortUrlRequest);
        return new ResponseEntity<ShortUrlDto>(shortUrlDtoConverter.covertToDto(service.create(shortUrl)), HttpStatus.CREATED);
    }


}
