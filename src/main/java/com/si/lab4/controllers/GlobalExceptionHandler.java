package com.si.lab4.controllers;

import com.si.lab4.exceptions.SomethingIsWrongException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SomethingIsWrongException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView somethingIsWrong(HttpServletRequest request, SomethingIsWrongException e){
        ModelAndView model = new ModelAndView("convertor");

        model.addObject("key", Strings.EMPTY);
        model.addObject("outputText", Strings.EMPTY);
        model.addObject("hide", true);
        model.addObject("hideKey", true);
        model.addObject("error", "We can't process your request.");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addObject("isLogged", true);
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addObject("isLogged", false);
        }
        return model;
    }
}
