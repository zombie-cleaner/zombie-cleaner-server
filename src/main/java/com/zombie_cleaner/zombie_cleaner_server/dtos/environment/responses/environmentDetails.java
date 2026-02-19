package com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses;

import lombok.Data;

import java.util.List;
import com.zombie_cleaner.zombie_cleaner_server.entities.Resource;

@Data
public class environmentDetails {
    private String environmentName;
    private List<Resource> resources;
}
