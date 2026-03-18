package com.zombie_cleaner.zombie_cleaner_server.services;

import com.zombie_cleaner.zombie_cleaner_server.entities.User;import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
    public User loadUserByEmail(String email);
}
