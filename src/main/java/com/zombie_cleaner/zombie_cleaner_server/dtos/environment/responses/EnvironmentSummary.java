package com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentSummary {
    private Long id;
    private String environmentName;
    private String description;
    private String externalId;
    private Number resourceCount;
}
