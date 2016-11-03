package com.yankovets.sqlcmd.model;

public class DatabaseManagerException extends RuntimeException{
    public DatabaseManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
