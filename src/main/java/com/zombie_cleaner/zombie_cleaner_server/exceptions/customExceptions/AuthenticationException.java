package com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationException extends RuntimeException{

    public AuthenticationException(String message) {
        super(message);
    }

}
