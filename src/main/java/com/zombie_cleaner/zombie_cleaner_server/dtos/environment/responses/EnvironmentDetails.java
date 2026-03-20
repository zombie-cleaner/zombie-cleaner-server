package com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses;

import com.zombie_cleaner.zombie_cleaner_server.dtos.resource.responses.ResourceSummary;
import lombok.Data;

@Data
public class EnvironmentDetails {
    private Long id;
    private String environmentName;
    private String description;
    private String environmentArn;
    private Long userId;
    private ResourceSummary[] resources;
}
