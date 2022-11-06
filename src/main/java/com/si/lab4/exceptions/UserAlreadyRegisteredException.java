package com.si.lab4.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(String email){
        super("Email: " + email + " is already registered");
    }
}
