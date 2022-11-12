package com.si.lab4.exceptions;

public class PasswordNotMatchException extends RuntimeException{
    public PasswordNotMatchException(){
        super("Password not match");
    }
}
