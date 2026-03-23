package com.zombie_cleaner.zombie_cleaner_server.services.impl;

import com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses.EnvironmentDetails;
import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions.ResourceNotFoundException;
import com.zombie_cleaner.zombie_cleaner_server.repositories.EnvironmentRepository;
import com.zombie_cleaner.zombie_cleaner_server.services.EnvironmentService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentServiceImpl implements EnvironmentService {
    EnvironmentRepository environmentRepository;

    public EnvironmentServiceImpl(EnvironmentRepository environmentRepository) {
        this.environmentRepository = environmentRepository;
    }

    @Override
    public Environment[] getUsersAllEnvironments(String id) {
        Environment[] environments = environmentRepository.getUserEnvironmentsByUserId(Long.parseLong(id)).orElseThrow(()-> new ResourceNotFoundException("Environments", "User Id", id));
        return environments;
    }

    @Override
    public EnvironmentDetails getEnvironmentById(String id, Long userId) throws AuthenticationException {
        System.out.println("hello");
        EnvironmentDetails environment = environmentRepository.getEnvironmentDetailsById(Long.parseLong(id)).orElseThrow(()-> new ResourceNotFoundException("Environment", "Environment Id", id));
        if(environment.getUserId().equals(userId)){
            return environment;
        }
        throw new AuthenticationException("Unauthorized access to environment with id: " + id);
    }
}
