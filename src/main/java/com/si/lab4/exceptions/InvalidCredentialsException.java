package com.si.lab4.exceptions;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(){
        super("Username or password is incorrect");
    }
}
