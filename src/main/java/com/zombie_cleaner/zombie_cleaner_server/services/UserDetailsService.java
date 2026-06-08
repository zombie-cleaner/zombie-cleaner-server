package com.zombie_cleaner.zombie_cleaner_server.services;

import com.zombie_cleaner.zombie_cleaner_server.dtos.user.responses.UserDetailsResponse;
import com.zombie_cleaner.zombie_cleaner_server.entities.User;

public interface UserDetailsService {
    public User loadUserByEmail(String email);
    public UserDetailsResponse getUserWithEnvironments(Long id);
}
