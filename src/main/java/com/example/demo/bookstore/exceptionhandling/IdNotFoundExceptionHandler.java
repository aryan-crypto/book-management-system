package com.example.demo.bookstore.exceptionhandling;

import com.example.demo.bookstore.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class IdNotFoundExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus
    public ResponseEntity<ErrorMessage> handleDeletionException(IdNotFoundException ex, WebRequest request) {
//        return new ResponseEntity<ErrorMessage>(ex.getMessage(), HttpStatus.NOT_FOUND);
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }
}
