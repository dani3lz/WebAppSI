package com.si.lab4.model.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
