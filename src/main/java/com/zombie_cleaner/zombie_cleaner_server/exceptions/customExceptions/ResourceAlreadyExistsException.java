package com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions;

public class ResourceAlreadyExistsException  extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(String.format(message));
    }
}
