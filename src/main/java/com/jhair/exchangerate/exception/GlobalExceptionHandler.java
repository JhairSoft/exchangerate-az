package com.jhair.exchangerate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jhair.exchangerate.dto.response.ErrorResponseDTO;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Mono<ErrorResponseDTO> handlerResourceNotFoundException(ResourceNotFoundException ex){
        ErrorResponseDTO error = new ErrorResponseDTO("NOT_FOUND", ex.getMessage());
        return Mono.just(error);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponseDTO> handlerGlobalException(Exception ex){
        ErrorResponseDTO error = new ErrorResponseDTO("INTERNAL_SERVER_ERROR", ex.getMessage());
        return Mono.just(error);
    }

}
