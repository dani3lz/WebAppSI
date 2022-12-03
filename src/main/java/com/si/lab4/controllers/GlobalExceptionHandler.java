package com.si.lab4.controllers;

import com.si.lab4.exceptions.InvalidCredentialsException;
import com.si.lab4.exceptions.PasswordNotMatchException;
import com.si.lab4.exceptions.SomethingIsWrongException;
import com.si.lab4.exceptions.UserAlreadyRegisteredException;
import com.si.lab4.model.response.ConvertorResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {SomethingIsWrongException.class, IllegalStateException.class, IllegalArgumentException.class})
    public ModelAndView somethingIsWrong() {
        ModelAndView model = new ModelAndView("convertor");
        model.addObject("response", new ConvertorResponse("We can't process your request."));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLogged = !Objects.isNull(authentication) && !(authentication instanceof AnonymousAuthenticationToken);
        model.addObject("isLogged", isLogged);

        return model;
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ModelAndView redirectPasswordNotMatch() {
        return new ModelAndView("redirect:/register?password");
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ModelAndView redirectUsernameAlreadyExist() {
        return new ModelAndView("redirect:/register?exist");
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ModelAndView redirectToLogin() {
        return new ModelAndView("redirect:/login");
    }
}
