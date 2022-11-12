package com.si.lab4.model.requests;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;
}
