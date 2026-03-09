package com.zombie_cleaner.zombie_cleaner_server.services.impl;

import com.zombie_cleaner.zombie_cleaner_server.dtos.user.responses.UserDetailsResponse;
import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import com.zombie_cleaner.zombie_cleaner_server.entities.User;
import com.zombie_cleaner.zombie_cleaner_server.repositories.UserRepository;
import com.zombie_cleaner.zombie_cleaner_server.services.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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
    public UserDetails loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

    public UserDetailsResponse getUserWithEnvironments(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Environment[] environments = environmentService.getUsersAllEnvironments(String.valueOf(id));

        UserDetailsResponse response = new UserDetailsResponse();
        response.setEmail(user.getEmail());
        response.setEnvironments(List.of(environments));

        return response;
    }
}
