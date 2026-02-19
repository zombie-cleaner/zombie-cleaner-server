package com.zombie_cleaner.zombie_cleaner_server.exceptions;

import com.zombie_cleaner.zombie_cleaner_server.dtos.ApiResponse;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<@NonNull ApiResponse<String>> handleNotFound()
    {
        ApiResponse<String> apiResponse = ApiResponse.failure("No such resource found");
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
