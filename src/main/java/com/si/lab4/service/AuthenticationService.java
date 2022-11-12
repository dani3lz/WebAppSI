package com.si.lab4.service;


import com.si.lab4.exceptions.InvalidCredentialsException;
import com.si.lab4.exceptions.PasswordNotMatchException;
import com.si.lab4.exceptions.UserAlreadyRegisteredException;
import com.si.lab4.model.entity.Credential;
import com.si.lab4.model.entity.CustomUser;
import com.si.lab4.model.requests.RegisterRequest;
import com.si.lab4.model.requests.UserRequest;
import com.si.lab4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements Validator {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final InMemoryUserDetailsManager userDetailsManager;


    public void registerNewUser(RegisterRequest request) {
        if (validate(request)) {
            UserRequest userRequest = new UserRequest(request.getUsername(), request.getPassword());
            saveUser(userRequest);

            Optional<CustomUser> userOptional = userRepository.findCustomUserByUsername(userRequest.getUsername());
            if (userOptional.isPresent()) {
                CustomUser user = userOptional.get();
                userDetailsManager.createUser(User.builder()
                        .username(user.getUsername())
                        .password(user.getCredential()
                                .getPassword())
                        .roles(user.getRole())
                        .build());
            }
        }
        throw new InvalidCredentialsException();
    }

    private void saveUser(UserRequest request) {
        Credential credential = new Credential(encoder.encode(request.getPassword()));
        CustomUser newCustomUser = new CustomUser(request.getUsername());
        newCustomUser.setCredential(credential);
        credential.setCustomUser(newCustomUser);
        userRepository.save(newCustomUser);
    }

    @Override
    public boolean validate(RegisterRequest request) {
        String username = request.getUsername();
        if (userRepository.findCustomUserByUsername(username).isPresent()) {
            throw new UserAlreadyRegisteredException(username);
        } else if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new PasswordNotMatchException();
        }
        return true;
    }

}
