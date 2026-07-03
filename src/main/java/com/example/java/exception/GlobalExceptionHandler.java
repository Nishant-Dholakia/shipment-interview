package com.example.java.exception;

import com.example.java.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        404,
                        ex.getMessage()
                ));
    }
    @ExceptionHandler(UsersAlreadyConnectedException.class)
    public ResponseEntity<ErrorResponse> validation(
            UsersAlreadyConnectedException ex){

        Map<String,String> errors = new HashMap<>();

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        409,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex) {

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        400,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedRequest(
            BadRequestException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        403,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validation(
            MethodArgumentNotValidException ex){

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(),
                                error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex){

        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        500,
                        ex.getMessage()
                ));
    }
}
