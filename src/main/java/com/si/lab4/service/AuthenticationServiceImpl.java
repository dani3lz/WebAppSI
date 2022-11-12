package com.si.lab4.service;


import com.si.lab4.exceptions.InvalidCredentialsException;
import com.si.lab4.exceptions.PasswordNotMatch;
import com.si.lab4.exceptions.UserAlreadyRegisteredException;
import com.si.lab4.model.entity.Credential;
import com.si.lab4.model.entity.CustomUser;
import com.si.lab4.model.requests.LoginResponse;
import com.si.lab4.model.requests.RegisterRequest;
import com.si.lab4.model.requests.UserRequest;
import com.si.lab4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final ValidationService sessionService;
    private final InMemoryUserDetailsManager userDetailsManager;

    @Override
    public LoginResponse registerNewUser(RegisterRequest request) {
        if(validate(request)){
            UserRequest userRequest = new UserRequest(request.getUsername(), request.getPassword());
            saveUser(userRequest);

            CustomUser user = userRepository.findCustomUserByUsername(userRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("Something goes wrong.."));

            userDetailsManager.createUser(User.builder()
                    .username(user.getUsername())
                    .password(user.getCredential()
                            .getPassword())
                    .roles("USER")
                    .build());

            return sessionService.validateCredentials(userRequest);
        }
        throw new InvalidCredentialsException();
    }

    @Override
    public LoginResponse loginUser(UserRequest request) {
        return null;
        //return sessionService.validateCredentials(request);
    }

    private void saveUser(UserRequest request) {
        Credential credential = new Credential(encoder.encode(request.getPassword()));
        CustomUser newCustomUser = new CustomUser(request.getUsername());
        newCustomUser.setCredential(credential);
        credential.setCustomUser(newCustomUser);
        userRepository.save(newCustomUser);
    }

    private boolean validate(RegisterRequest request){
        String username = request.getUsername();
        if (usernameExists(username)) {
            throw new UserAlreadyRegisteredException(username);
        } else if (!request.getPassword().equals(request.getConfirmPassword())){
            throw new PasswordNotMatch();
        }
        return true;
    }

    private boolean usernameExists(String username) {
        return userRepository.findCustomUserByUsername(username).isPresent();
    }
}
