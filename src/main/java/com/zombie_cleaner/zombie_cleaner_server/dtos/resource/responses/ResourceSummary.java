package com.zombie_cleaner.zombie_cleaner_server.dtos.resource.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class ResourceSummary {
    private Long id;
    private String resourceName;
    private String resourceType;
    private String resourceArn;
    private String resourceIdentifierAws;
    private String resourceNameAws;
}
