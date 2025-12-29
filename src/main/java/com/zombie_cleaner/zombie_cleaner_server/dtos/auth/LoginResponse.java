package com.zombie_cleaner.zombie_cleaner_server.dtos.auth;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    public LoginResponse(String token)
    {
        this.token = token;
    }
}
