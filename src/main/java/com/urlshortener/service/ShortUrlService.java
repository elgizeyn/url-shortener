package com.urlshortener.service;

import com.urlshortener.exception.CodeAlreadyExist;
import com.urlshortener.exception.ShortUrlNotFoundException;
import com.urlshortener.model.ShortUrl;
import com.urlshortener.repository.ShortUrlRepository;
import com.urlshortener.util.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortUrlService {

    private final ShortUrlRepository repository;
    private final RandomStringGenerator stringGenerator;

    public ShortUrlService(ShortUrlRepository repository, RandomStringGenerator stringGenerator) {
        this.repository = repository;
        this.stringGenerator = stringGenerator;
    }


    public List<ShortUrl> getAllShortUrls() {
        return repository.findAll();
    }

    public ShortUrl getUrlByCode(String code) {
        return repository.findAllByCode(code).orElseThrow(() -> new ShortUrlNotFoundException("url not found!"));
    }

    public ShortUrl create(ShortUrl shortUrl) {
        if (shortUrl.getCode() == null || shortUrl.getCode().isEmpty()) {
            shortUrl.setCode(generateCode());
        }
        else if (repository.findAllByCode(shortUrl.getCode()).isPresent()) {
            throw new CodeAlreadyExist("code already exists");
        }
        shortUrl.setCode(shortUrl.getCode().toUpperCase());
        return repository.save(shortUrl);
    }

    private String generateCode() {
        String code;

        do {
            code = stringGenerator.generateRandomString();
        }
        while (repository.findAllByCode(code).isPresent());

        return code;
    }
}
