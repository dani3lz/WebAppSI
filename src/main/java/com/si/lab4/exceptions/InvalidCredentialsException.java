package com.si.lab4.exceptions;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(){
        super("Email or password is incorrect");
    }
}
