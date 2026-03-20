package com.zombie_cleaner.zombie_cleaner_server.services;

import com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses.EnvironmentDetails;
import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;

public interface EnvironmentService {
    public Environment[] getUsersAllEnvironments(String id);
    public EnvironmentDetails getEnvironmentById(String id);
}
