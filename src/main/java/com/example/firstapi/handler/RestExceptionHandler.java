package com.example.firstapi.handler;

import com.example.firstapi.exceptions.NotFoundRequestException;
import com.example.firstapi.exceptions.NotFoundRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    // Se lançar uma exceção e ela for do tipo NotFoundException, tem que utilizar o método
    @ExceptionHandler(NotFoundRequestException.class)
    public ResponseEntity<NotFoundRequestExceptionDetails> handlerBadRequestException(NotFoundRequestException bre){
        return new ResponseEntity<>(
                NotFoundRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("Not Found Exception, check the Documentation")
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .build(), HttpStatus.NOT_FOUND);
    }
}
