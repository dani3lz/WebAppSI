package com.si.lab4.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    private String username;

    @NotBlank
    private String password;
}
