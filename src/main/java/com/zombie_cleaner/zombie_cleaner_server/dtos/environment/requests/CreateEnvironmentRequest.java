package com.zombie_cleaner.zombie_cleaner_server.dtos.environment.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateEnvironmentRequest {
    @NotBlank(message = "Environment name is required")
    private String environmentName;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Environment ARN is required")
    private String environmentArn;
    @NotBlank(message = "User ID is required")
    private String userId;
}
