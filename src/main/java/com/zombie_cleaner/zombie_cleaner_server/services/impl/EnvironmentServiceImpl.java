package com.zombie_cleaner.zombie_cleaner_server.services.impl;

import com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses.EnvironmentDetails;
import com.zombie_cleaner.zombie_cleaner_server.dtos.resource.responses.ResourceSummary;
import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions.ResourceNotFoundException;
import com.zombie_cleaner.zombie_cleaner_server.repositories.EnvironmentRepository;
import com.zombie_cleaner.zombie_cleaner_server.services.EnvironmentService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Environment environment = environmentRepository.getEnvironmentById(Long.parseLong(id)).orElseThrow(()-> new ResourceNotFoundException("Environment", "Environment Id", id));
        if(!environment.getUser().getId().equals(userId)){
            throw new AuthenticationException("Unauthorized access to environment with id: " + id);
        }
        List<ResourceSummary> resources = environment.getResources()
                .stream()
                .map(resource -> new ResourceSummary(
                        resource.getId(),
                        resource.getResourceName(),
                        resource.getResourceType(),
                        resource.getResourceArn(),
                        resource.getResourceIdentifierAws(),
                        resource.getResourceNameAws()))
                .toList();
        EnvironmentDetails environmentDetails = new EnvironmentDetails();
        environmentDetails.setId(environment.getId());
        environmentDetails.setEnvironmentName(environment.getEnvironmentName());
        environmentDetails.setResources(resources);
        return environmentDetails;
    }
}
