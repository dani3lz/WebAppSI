package com.si.lab4.service;


import com.si.lab4.model.requests.LoginResponse;
import com.si.lab4.model.requests.UserRequest;

public interface ValidationService {
    LoginResponse validateCredentials(UserRequest request);

    void invalidateToken(String token);
}
