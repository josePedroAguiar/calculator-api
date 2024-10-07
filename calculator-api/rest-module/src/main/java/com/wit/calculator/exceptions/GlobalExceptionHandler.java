package com.wit.calculator.exceptions;

import org.springframework.core.convert.ConversionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleArithmeticException(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        if (ex.getCause() instanceof ArithmeticException)
            body.put("error",   ex.getCause().getMessage());
        else
            body.put("error", "An error occurred: " + ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleGenericException(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Argument Type Mismatch: Both 'a' and 'b' must be integers.");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Missing parameter: " + ex.getParameterName());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    /*@ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Resource not found: " );
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error",ex.getClass() +" An error occurred: " + ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}