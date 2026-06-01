package com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses;

import com.zombie_cleaner.zombie_cleaner_server.dtos.resource.responses.ResourceSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentDetails {
    private Long id;
    private String environmentName;
    private String description;
    private String externalId;
    private Long userId;
    private List<ResourceSummary> resources;
}
