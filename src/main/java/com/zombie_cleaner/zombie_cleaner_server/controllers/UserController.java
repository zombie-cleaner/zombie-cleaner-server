package com.zombie_cleaner.zombie_cleaner_server.controllers;

import com.zombie_cleaner.zombie_cleaner_server.dtos.ApiResponse;
import com.zombie_cleaner.zombie_cleaner_server.dtos.user.responses.UserDetailsResponse;
import com.zombie_cleaner.zombie_cleaner_server.services.impl.UserDetailsServiceImpl;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/user/{id}")
    public ResponseEntity<@NonNull ApiResponse<UserDetailsResponse>> getUserDetails(@PathVariable String id) {
        UserDetailsResponse userDetailsResponse = userDetailsService.getUserWithEnvironments(Long.parseLong(id));
        ApiResponse<UserDetailsResponse> apiResponse = ApiResponse.success(userDetailsResponse, "User details retrieved successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
