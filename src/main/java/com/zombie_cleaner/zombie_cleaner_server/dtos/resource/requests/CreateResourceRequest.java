package com.zombie_cleaner.zombie_cleaner_server.dtos.resource.requests;

import lombok.Data;

@Data
public class CreateResourceRequest {
    private String resourceName;
    private String description;
    private String resourceArn;
    private String environmentId;
}
