package com.example.firstapi.handler;

import com.example.firstapi.exceptions.NotFoundRequestException;
import com.example.firstapi.exceptions.NotFoundRequestExceptionDetails;
import com.example.firstapi.exceptions.ValidationExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//permite que você use exatamente as mesmas técnicas de tratamento de exceção,
// mas aplique-as em toda a aplicação, não apenas a um controlador individual
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException exception){
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(
                Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("Not Found Exception, check the Documentation")
                        .details(exception.getMessage())
                        .developerMessage(exception.getClass().getName())
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(), HttpStatus.NOT_FOUND);
    }

}
