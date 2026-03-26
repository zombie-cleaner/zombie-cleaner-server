package com.zombie_cleaner.zombie_cleaner_server.exceptions.customExceptions;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String message) {
        super(String.format(message));
    }
}
