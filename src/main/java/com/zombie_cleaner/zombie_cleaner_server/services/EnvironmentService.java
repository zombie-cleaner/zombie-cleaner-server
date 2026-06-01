package com.zombie_cleaner.zombie_cleaner_server.services;

import com.zombie_cleaner.zombie_cleaner_server.dtos.environment.requests.CreateEnvironmentRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses.EnvironmentDetails;
import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import org.apache.tomcat.websocket.AuthenticationException;

import java.util.List;

public interface EnvironmentService {
    public List<Environment> getUsersAllEnvironments(String id);
    public EnvironmentDetails getEnvironmentById(String id, Long userId) throws AuthenticationException;
    public Environment createEnvironment(CreateEnvironmentRequest environment);
    public boolean setDeleteEvent();
    public boolean setUpdateEvent();
}
