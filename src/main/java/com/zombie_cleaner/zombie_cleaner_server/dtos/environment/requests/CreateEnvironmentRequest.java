package com.zombie_cleaner.zombie_cleaner_server.dtos.environment.requests;

import lombok.Data;

@Data
public class CreateEnvironmentRequest {
    private String environmentName;
    private String description;
    private String environmentArn;
    private Long userId;
}
