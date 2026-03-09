package com.zombie_cleaner.zombie_cleaner_server.services.impl;

import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import com.zombie_cleaner.zombie_cleaner_server.repositories.EnvironmentRepository;
import com.zombie_cleaner.zombie_cleaner_server.services.EnviornmentService;

public class EnvironmentServiceImpl implements EnviornmentService {
    EnvironmentRepository environmentRepository;

    EnvironmentServiceImpl(EnvironmentRepository environmentRepository) {
        this.environmentRepository = environmentRepository;
    }

    @Override
    public Environment[] getUsersAllEnvironments(String id) {
        Environment[] environments = environmentRepository.getUserEnvironmentsByUserId(Long.parseLong(id)).orElse(null);
        return environments;
    }
}
