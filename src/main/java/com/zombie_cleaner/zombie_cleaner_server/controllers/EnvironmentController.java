package com.zombie_cleaner.zombie_cleaner_server.controllers;

import com.zombie_cleaner.zombie_cleaner_server.dtos.ApiResponse;
import com.zombie_cleaner.zombie_cleaner_server.dtos.environment.requests.CreateEnvironmentRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses.EnvironmentDetails;
import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import com.zombie_cleaner.zombie_cleaner_server.services.EnvironmentService;
import com.zombie_cleaner.zombie_cleaner_server.utils.AuthenticationUtil;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EnvironmentController {

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    /**
     * Get environment by ID with authorization check.
     * Ensures the requesting user owns the environment.
     */
    @GetMapping("/api/environment/{id}")
    public ResponseEntity<@NonNull ApiResponse<EnvironmentDetails>> getEnvironmentById(@PathVariable String id) {
        // Fetch environment and verify ownership
        EnvironmentDetails environment = null;
        try {
            environment = environmentService.getEnvironmentDetails(id);
        } catch (org.apache.tomcat.websocket.AuthenticationException e) {
            throw new RuntimeException(e);
        }
        ApiResponse<EnvironmentDetails> response = ApiResponse.success(environment, "Environment retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/environment")
    public ResponseEntity<@NonNull ApiResponse<Environment>> createEnvironment(@Valid @RequestBody CreateEnvironmentRequest environmentRequest) {
        Environment environment = environmentService.createEnvironment(environmentRequest);
        ApiResponse<Environment> apiResponse = ApiResponse.success(environment, "Environment created successfully");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

//    @GetMapping("/api/environm")
}


