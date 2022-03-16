package com.example.firstapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundRequestException extends RuntimeException{

    public NotFoundRequestException(String message){
        super(message);
    }
}
