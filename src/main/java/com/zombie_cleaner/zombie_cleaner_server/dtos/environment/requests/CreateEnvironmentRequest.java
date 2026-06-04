package com.zombie_cleaner.zombie_cleaner_server.dtos.environment.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateEnvironmentRequest {
    @NotBlank(message = "Environment name is required")
    private String environmentName;
    @NotBlank(message = "Description is required")
    private String description;
//    @Pattern(regexp = "^arn:(aws[a-zA-Z-]*)?:[a-z0-9-]+:[a-z0-9-]*:[0-9]*:.+$", message = "Invalid AWS arn format")
    @NotBlank(message = "Environment Id is required")
    @NotBlank
    private String externalId;
    @NotBlank(message = "User ID is required")
    private String userId;
}
