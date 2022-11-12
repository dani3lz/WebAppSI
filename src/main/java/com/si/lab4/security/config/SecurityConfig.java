package com.si.lab4.security.config;

import com.si.lab4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final UserRepository userRepository;

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        List<UserDetails> userDetails = new ArrayList<>();
        userRepository.findAll()
                .forEach(user -> userDetails.add(User.builder()
                        .username(user.getUsername())
                        .password(user.getCredential()
                                .getPassword())
                        .roles("USER")
                        .build()));
        userDetails.forEach(user -> log.info(user.toString()));

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeRequests(configurer ->
                        configurer
                                .antMatchers("/soon").hasRole("USER")
                                .antMatchers("/sys/**").hasRole("ADMIN"))

                .formLogin(configurer ->
                        configurer
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/soon", true)
                                .permitAll())


                .logout(LogoutConfigurer::permitAll)

                .exceptionHandling(configurer ->
                        configurer
                                .accessDeniedPage("/access-denied"))

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
