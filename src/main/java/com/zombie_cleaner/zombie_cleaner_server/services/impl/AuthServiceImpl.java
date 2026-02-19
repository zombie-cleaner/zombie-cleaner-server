package com.zombie_cleaner.zombie_cleaner_server.services.impl;

import com.zombie_cleaner.zombie_cleaner_server.config.auth.JwtUtil;
import com.zombie_cleaner.zombie_cleaner_server.dtos.auth.requests.LoginRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.auth.requests.RegisterRequest;
import com.zombie_cleaner.zombie_cleaner_server.dtos.auth.responses.LoginResponse;
import com.zombie_cleaner.zombie_cleaner_server.entities.User;
import com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions.ResourceNotFoundException;
import com.zombie_cleaner.zombie_cleaner_server.repositories.UserRepository;
import com.zombie_cleaner.zombie_cleaner_server.services.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User", "email", req.getEmail()));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Credentials");
        }

        return new LoginResponse(jwtUtil.generateToken(user.getEmail()), user.getId(), user.getName());
    }

    public Boolean register(RegisterRequest req) {
        User user = new User();
        user.setEmail(req.getEmail());
        user.setName(req.getName());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Data integrity");
        }
    }
}
