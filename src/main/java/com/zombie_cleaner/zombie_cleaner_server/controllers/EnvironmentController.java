package com.zombie_cleaner.zombie_cleaner_server.controllers;

import com.zombie_cleaner.zombie_cleaner_server.dtos.ApiResponse;
import com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses.EnvironmentDetails;
import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import com.zombie_cleaner.zombie_cleaner_server.services.EnvironmentService;
import com.zombie_cleaner.zombie_cleaner_server.utils.AuthenticationUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

        // Get the current user's ID from the authenticated principal
        Long currentUserId = authenticationUtil.getCurrentUserId();

        // Fetch environment and verify ownership
        EnvironmentDetails environment = null;
        try {
            environment = environmentService.getEnvironmentById(id, currentUserId);
        } catch (org.apache.tomcat.websocket.AuthenticationException e) {
            throw new RuntimeException(e);
        }
        ApiResponse<EnvironmentDetails> response = ApiResponse.success(environment, "Environment retrieved successfully");
        return ResponseEntity.ok(response);
    }

}


