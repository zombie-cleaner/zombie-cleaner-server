package com.zombie_cleaner.zombie_cleaner_server.controllers;

import com.zombie_cleaner.zombie_cleaner_server.dtos.ApiResponse;
import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import com.zombie_cleaner.zombie_cleaner_server.services.EnvironmentService;
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

        Environment environment = environmentService.getEnvironmentById(id);
        if (environment != null) {
            ApiResponse<Environment> response = ApiResponse.success(environment, "Environment retrieved successfully");
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Environment> response = ApiResponse.failure("Environment not found");
            return ResponseEntity.status(404).body(response);
        }

        // Get the current user's ID from the authenticated principal
        Long currentUserId = authenticationUtil.getCurrentUserId();

        // Fetch environment and verify ownership
        Environment environment = environmentService.getEnvironmentByIdForUser(id, currentUserId);
        return ResponseEntity.ok(response);
    }

}


