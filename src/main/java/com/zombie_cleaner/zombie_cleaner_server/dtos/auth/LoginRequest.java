package com.zombie_cleaner.zombie_cleaner_server.dtos.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
