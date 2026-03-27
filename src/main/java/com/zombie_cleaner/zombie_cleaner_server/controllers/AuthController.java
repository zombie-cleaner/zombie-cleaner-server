package com.zombie_cleaner.zombie_cleaner_server.controllers;

import com.zombie_cleaner.zombie_cleaner_server.dtos.ApiResponse;
import com.zombie_cleaner.zombie_cleaner_server.dtos.auth.requests.LoginRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.auth.requests.RegisterRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.auth.responses.LoginResponse;
import com.zombie_cleaner.zombie_cleaner_server.services.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<@NonNull ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse loginResponse = authService.login(request);
        ApiResponse<LoginResponse> apiResponse = ApiResponse.success(loginResponse, "Logged in successfully ");
        return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
    }

    @PostMapping("/register")
    public ResponseEntity<@NonNull ApiResponse<Boolean>> register(@Valid @RequestBody RegisterRequest request){
        Boolean registerResponse = authService.register(request);
        ApiResponse<Boolean> apiResponse = ApiResponse.success(registerResponse, "Registered Successfully");
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
}
