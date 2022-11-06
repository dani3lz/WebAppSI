package com.si.lab4.service;

import com.si.lab4.exceptions.InvalidCredentialsException;
import com.si.lab4.model.entity.Credential;
import com.si.lab4.model.requests.LoginResponse;
import com.si.lab4.model.requests.UserRequest;
import com.si.lab4.repository.CredentialRepository;
import com.si.lab4.security.JWTUtil;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final CredentialRepository credentialRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtToken;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public LoginResponse validateCredentials(UserRequest request) {
        Credential credential = credentialRepository.findCredentialByUserEmailContaining(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (passwordEncoder.matches(request.getPassword(), credential.getPassword())) {
            return createLoginResponse(request);
        }
        throw new InvalidCredentialsException();
    }

    @Override
    public void invalidateToken(String token) {
        LoginResponse.isExpired = true;
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().setExpiration(new Date());
    }

    private LoginResponse createLoginResponse(UserRequest request) {
        String token = jwtToken.generateToken(request.getEmail());
        LoginResponse.isExpired = false;
        return new LoginResponse(token);
    }

}
