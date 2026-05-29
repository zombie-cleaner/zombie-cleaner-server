package com.zombie_cleaner.zombie_cleaner_server.dtos.aws.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;

@Data
@AllArgsConstructor
public class ExternalResourceSummary {
//    private Long id;
//    private String resourceName;
//    private String resourceType;
//    private String resourceArn;
//    private String resourceIdentifierAws;
//    private String resourceNameAws;
    private String roleArn;
    private String resourceName;
    private String resourceType;

    private JacksonProperties.Json propertiesJson;

}
