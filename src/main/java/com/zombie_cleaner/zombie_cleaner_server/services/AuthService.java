package com.zombie_cleaner.zombie_cleaner_server.services;

import com.zombie_cleaner.zombie_cleaner_server.dtos.auth.requests.LoginRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.auth.requests.RegisterRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.auth.responses.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    Boolean register(RegisterRequest response);
}
