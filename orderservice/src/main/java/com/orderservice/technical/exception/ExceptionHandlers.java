package com.orderservice.technical.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Simplified exception management
 * Next step would be to define clear functional exceptions
 *
 * @author Cyril Gambis
 * @date 22 juin 2021
 */
@RestControllerAdvice
public class ExceptionHandlers {

    /**
     * Simplified version
     *
     * @author Cyril Gambis
     * @date 22 juin 2021
     */
    @ExceptionHandler(value = RuntimeException.class)
    public final ResponseEntity<String> handleCustomRuntimeException(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
