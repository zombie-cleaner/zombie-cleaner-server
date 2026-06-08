package com.zombie_cleaner.zombie_cleaner_server.controllers;

import com.zombie_cleaner.zombie_cleaner_server.dtos.ApiResponse;
import com.zombie_cleaner.zombie_cleaner_server.dtos.user.responses.UserDetailsResponse;
import com.zombie_cleaner.zombie_cleaner_server.entities.User;
import com.zombie_cleaner.zombie_cleaner_server.services.UserDetailsService;
import com.zombie_cleaner.zombie_cleaner_server.services.impl.UserDetailsServiceImpl;
import com.zombie_cleaner.zombie_cleaner_server.utils.AuthenticationUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @GetMapping("/api/user/{id}")
    public ResponseEntity<@NonNull ApiResponse<UserDetailsResponse>> getUserDetails() {
        Long userId = authenticationUtil.getCurrentUserId();
        UserDetailsResponse userDetailsResponse = userDetailsService.getUserWithEnvironments(userId);
        ApiResponse<UserDetailsResponse> apiResponse = ApiResponse.success(userDetailsResponse, "User details retrieved successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
