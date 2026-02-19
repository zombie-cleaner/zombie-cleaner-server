package com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataIntegrityViolation extends RuntimeException{
    private String message;
    private String resourceName;

    public DataIntegrityViolation(String message, String resourceName){
        super(String.format("Resource %s: %s", resourceName, message));
        this.message = message;
        this.resourceName = resourceName;
    }
}
