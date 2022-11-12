package com.si.lab4.service;


import com.si.lab4.model.requests.RegisterRequest;

public interface Validator {

    boolean validate(RegisterRequest request);

}
