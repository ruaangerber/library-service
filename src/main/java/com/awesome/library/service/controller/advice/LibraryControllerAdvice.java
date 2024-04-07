package com.awesome.library.service.controller.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.awesome.library.service.exception.AlreadyExistsException;
import com.awesome.library.service.exception.NotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class LibraryControllerAdvice {
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleValidationExceptions(HttpMessageNotReadableException ex) {

        return "Invalid input";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleValidationExceptions(ConstraintViolationException ex) {
        var errors = new HashMap<String, String>();
        
        ex.getConstraintViolations().forEach((error) -> {
            var fieldName = error.getPropertyPath().toString();
            var errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleValidationExceptions(MissingServletRequestParameterException ex) {

        return "Invalid input";
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(AlreadyExistsException.class)
    public String handleValidationExceptions(AlreadyExistsException ex) {

        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NotFoundException.class)
    public String handleValidationExceptions(NotFoundException ex) {

        return ex.getMessage();
    }

    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // @ExceptionHandler(Exception.class)
    // public String handleValidationExceptions(Exception ex) {

    //     return "Internal server error";
    // }

}
