package com.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ShortUrlNotFoundException.class)
    public ResponseEntity<?> shortUrlNotFound(ShortUrlNotFoundException e) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CodeAlreadyExist.class)
    public ResponseEntity<?> codeAlreadyExists(CodeAlreadyExist e) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> codeAlreadyExists(ConstraintViolationException e) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
}
