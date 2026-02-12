package com.zombie_cleaner.zombie_cleaner_server.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
    public UserDetails loadUserByEmail(String email);
}
