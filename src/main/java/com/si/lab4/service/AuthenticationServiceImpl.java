package com.si.lab4.service;


import com.si.lab4.exceptions.UserAlreadyRegisteredException;
import com.si.lab4.model.entity.Credential;
import com.si.lab4.model.entity.User;
import com.si.lab4.model.requests.LoginResponse;
import com.si.lab4.model.requests.UserRequest;
import com.si.lab4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final ValidationService sessionService;

    @Override
    public LoginResponse registerNewUser(UserRequest request) {
        String email = request.getEmail();
        if (emailExists(email)) {
            throw new UserAlreadyRegisteredException("Email: " + email + " is already registered");
        }
        saveUser(request);
        return sessionService.validateCredentials(request);
    }

    @Override
    public LoginResponse loginUser(UserRequest request) {
        return sessionService.validateCredentials(request);
    }

    @Override
    public void logoutUser(String token) {
        sessionService.invalidateToken(token);
    }

    private User saveUser(UserRequest request) {
        Credential credential = new Credential(encoder.encode(request.getPassword()));
        User newUser = new User(request.getEmail());
        newUser.setCredential(credential);
        credential.setUser(newUser);
        return userRepository.save(newUser);
    }

    private boolean emailExists(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }
}
