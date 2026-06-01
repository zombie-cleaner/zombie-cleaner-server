package com.zombie_cleaner.zombie_cleaner_server.services.impl;

import com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses.EnvironmentSummary;
import com.zombie_cleaner.zombie_cleaner_server.dtos.user.responses.UserDetailsResponse;
import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import com.zombie_cleaner.zombie_cleaner_server.entities.User;
import com.zombie_cleaner.zombie_cleaner_server.repositories.UserRepository;
import com.zombie_cleaner.zombie_cleaner_server.services.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final EnvironmentServiceImpl environmentService;

    public UserDetailsServiceImpl(UserRepository userRepository, EnvironmentServiceImpl environmentService) {
        this.userRepository = userRepository;
        this.environmentService = environmentService;
    }

    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDetailsResponse getUserWithEnvironments(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Environment> environments = environmentService.getUsersAllEnvironments(String.valueOf(id));

        UserDetailsResponse response = new UserDetailsResponse();


        response.setEmail(user.getEmail());
        response.setEnvironments( environments
                .stream()
                .map(env -> new EnvironmentSummary(
                        env.getId(),
                        env.getEnvironmentName(),
                        env.getDescription(),
                        env.getExternalId(),
                        env.getResources() != null ? env.getResources().size() : 0
                        )
                )
                .toList());

        return response;
    }
}
