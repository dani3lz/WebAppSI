package com.si.lab4.exceptions;

public class PasswordNotMatch extends RuntimeException{
    public PasswordNotMatch(){
        super("Password not match");
    }
}
