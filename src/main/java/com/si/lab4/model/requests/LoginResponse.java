package com.si.lab4.model.requests;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class LoginResponse {

    private final String token;
    public static boolean isExpired = true;
}
