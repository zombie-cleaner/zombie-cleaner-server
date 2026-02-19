package com.zombie_cleaner.zombie_cleaner_server.dtos.auth.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private Long id;
    private String name;
    public LoginResponse(String token, Long id, String name)
    {
        this.token = token;
        this.id = id;
        this.name = name;
    }
}
