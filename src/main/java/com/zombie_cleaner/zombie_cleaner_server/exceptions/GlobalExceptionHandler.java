package com.zombie_cleaner.zombie_cleaner_server.exceptions;

import com.zombie_cleaner.zombie_cleaner_server.dtos.ApiResponse;
import com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions.AuthenticationException;
import com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions.DatabaseException;
import com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions.ResourceAlreadyExistsException;
import com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions.ResourceNotFoundException;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<@NonNull ApiResponse<String>> handleDatabaseException(DatabaseException ex){
        ApiResponse<String> apiResponse = ApiResponse.failure("Database error: " + ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<@NonNull ApiResponse<String>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
        ApiResponse<String> apiResponse = ApiResponse.failure("Conflict: " + ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<@NonNull ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        ApiResponse<Map<String, String>> apiResponse = ApiResponse.failure(fieldErrors, "Validation failed");

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.apache.tomcat.websocket.AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(org.apache.tomcat.websocket.AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Authentication error: " + ex.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<@NonNull ApiResponse<String>> handleInternalServerError(Exception ex){
        ApiResponse<String> apiResponse = ApiResponse.failure("Internal server error. Please contact support.");
        System.out.println("Unhandled exception: " + ex);

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
