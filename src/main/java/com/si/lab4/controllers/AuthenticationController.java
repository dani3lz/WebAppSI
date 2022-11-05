package com.si.lab4.controllers;


import com.si.lab4.model.requests.LoginResponse;
import com.si.lab4.model.requests.UserRequest;
import com.si.lab4.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody @Valid UserRequest request){
        LoginResponse response = authenticationService.registerNewUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid UserRequest request){
        LoginResponse response = authenticationService.loginUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logOut(@RequestHeader String token){
        authenticationService.logoutUser(token);
    }

}
