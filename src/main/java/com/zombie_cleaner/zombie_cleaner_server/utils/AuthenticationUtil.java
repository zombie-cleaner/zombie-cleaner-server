package com.zombie_cleaner.zombie_cleaner_server.utils;

import com.zombie_cleaner.zombie_cleaner_server.entities.User;
import com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Utility class for handling authentication-related operations.
 * This class provides common methods to retrieve authentication and user details
 * from the SecurityContext, eliminating code redundancy across controllers.
 */
@Component
public class AuthenticationUtil {

    /**
     * Get the current authentication object from SecurityContextHolder
     * @return the current Authentication object
     */
    public Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Get the current authenticated user details
     * @return UserDetails of the current user
     */
    public Long getCurrentUserId() throws AuthenticationException {
        Authentication auth = getCurrentAuthentication();
        if (auth != null && auth.getPrincipal() instanceof User) {
            User user = (User) auth.getPrincipal();
            return user.getId();
        }

        throw new AuthenticationException("User not authenticated");
    }

    /**
     * Get the current username
     * @return the username of the current authenticated user
     */
    public String getCurrentUsername() {
        Authentication auth = getCurrentAuthentication();
        if (auth != null) {
            return auth.getName();
        }
        return null;
    }

    /**
     * Check if the current user is authenticated
     * @return true if user is authenticated, false otherwise
     */
    public boolean isAuthenticated() {
        Authentication auth = getCurrentAuthentication();
        return auth != null && auth.isAuthenticated();
    }
}

