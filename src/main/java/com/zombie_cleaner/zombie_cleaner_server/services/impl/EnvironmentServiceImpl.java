package com.zombie_cleaner.zombie_cleaner_server.services.impl;

import com.zombie_cleaner.zombie_cleaner_server.dtos.environment.requests.CreateEnvironmentRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses.EnvironmentDetails;
import com.zombie_cleaner.zombie_cleaner_server.dtos.resource.responses.ResourceSummary;
import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import com.zombie_cleaner.zombie_cleaner_server.entities.User;
import com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions.DatabaseException;
import com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions.ResourceAlreadyExistsException;
import com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions.ResourceNotFoundException;
import com.zombie_cleaner.zombie_cleaner_server.repositories.EnvironmentRepository;
import com.zombie_cleaner.zombie_cleaner_server.repositories.UserRepository;
import com.zombie_cleaner.zombie_cleaner_server.services.EnvironmentService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentServiceImpl implements EnvironmentService {
    EnvironmentRepository environmentRepository;
    UserRepository userRepository;

    public EnvironmentServiceImpl(EnvironmentRepository environmentRepository, UserRepository userRepository) {
        this.environmentRepository = environmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Environment> getUsersAllEnvironments(String id) {
        List<Environment> environments = environmentRepository.getUserEnvironmentsByUserId(Long.parseLong(id)).orElseThrow(()-> new ResourceNotFoundException("Environments", "User Id", id));
        return environments;
    }

    @Override
    public Environment getEnvironmentById(String environmentId, Long userId) throws AuthenticationException {

        Environment environment = environmentRepository.findById(Long.parseLong(environmentId)).orElseThrow(() -> new ResourceNotFoundException("Environment", "Environment Id", environmentId));
        if (!environment.getUser().getId().equals(userId)) {
            throw new AuthenticationException("Unauthorized access to environment with id: " + environmentId);
        }
        return environment;
    }
    @Override
    public EnvironmentDetails getEnvironmentDetails(String environmentId, Long userId) throws AuthenticationException{
        Environment environment = getEnvironmentById(environmentId, userId);
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
        environmentDetails.setExternalId(environment.getExternalId());
        environmentDetails.setDescription(environmentDetails.getDescription());
        environmentDetails.setUserId(environment.getUser().getId());
        environmentDetails.setResources(resources);
        return environmentDetails;
    }

    @Override
    public Environment createEnvironment(CreateEnvironmentRequest environmentRequest) {
        try{
            User user = userRepository.findById(Long.parseLong(environmentRequest.getUserId())).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", environmentRequest.getUserId()));

            Environment environment = new Environment();
            environment.setEnvironmentName(environmentRequest.getEnvironmentName());
            environment.setDescription(environmentRequest.getDescription());
            environment.setExternalId(environmentRequest.getExternalId());
            environment.setUser(user);
            return environmentRepository.save(environment);
        }catch (DataIntegrityViolationException e) {
            if(e.getCause() instanceof ConstraintViolationException){
                throw new ResourceAlreadyExistsException("Evironment with name '" + environmentRequest.getEnvironmentName() + "' already exists for this user.");
            }
            throw new DatabaseException("Failed to save environment due to data integrity violation.");
        } catch (Exception e) {
            throw new DatabaseException("An unexpected error occurred while saving the environment.");
        }
    }
}
