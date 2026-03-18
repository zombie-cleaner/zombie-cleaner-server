package com.zombie_cleaner.zombie_cleaner_server.dtos.environment.requests;

import lombok.Data;

@Data
public class createEnvironmentRequest {
    private String environmentName;
    private String description;
    private String environmentArn;
}
