package com.zombie_cleaner.zombie_cleaner_server.controllers;

import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import com.zombie_cleaner.zombie_cleaner_server.services.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvironmentController {

    @Autowired
    private EnvironmentService environmentService;

    @GetMapping("/api/environment/{id}")
    public Environment getEnvironment(@PathVariable String id) {
        return environmentService.getEnvironmentById(id);
    }
}
