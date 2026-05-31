package com.zombie_cleaner.zombie_cleaner_server.dtos.aws.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExternalResourceSummary {
    private String resourceArn;
    private String resourceName;
    private String resourceType;
    private Map<String, Object> properties;
}
