package com.sk.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.sk.recipe.util.ErrorMessage;

import javax.persistence.EntityNotFoundException;

/**
 * Controller advice for handling exceptions
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class , NoItemFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> entityNotFoundException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage();
        message.setMessage(ex.getMessage());
        message.setCode(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }



    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> anyException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage();
        message.setMessage(ex.getMessage());
        message.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}
