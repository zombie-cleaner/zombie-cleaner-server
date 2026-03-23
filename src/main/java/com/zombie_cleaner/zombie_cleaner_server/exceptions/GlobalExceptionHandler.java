package com.zombie_cleaner.zombie_cleaner_server.exceptions;

import com.zombie_cleaner.zombie_cleaner_server.dtos.ApiResponse;
import com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions.AuthenticationException;
import com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions.ResourceNotFoundException;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<@NonNull ApiResponse<String>>  handleResourceNotFoundException(ResourceNotFoundException ex){
        ApiResponse<String> apiResponse = ApiResponse.failure(ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<@NonNull ApiResponse<String>> handleAuthenticationException(AuthenticationException ex){
        ApiResponse<String> apiResponse = ApiResponse.failure("Authentication Failure : "+ ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<@NonNull ApiResponse<String>> handleNoHandlerFoundException(NoHandlerFoundException ex){
        ApiResponse<String> apiResponse = ApiResponse.failure("Endpoint not found: " + ex.getRequestURL());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<@NonNull ApiResponse<String>> handleInternalServerError(Exception ex){
        ApiResponse<String> apiResponse = ApiResponse.failure("Internal server error. Please contact support.");
        System.out.println("Unhandled exception: " + ex.getMessage());

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
