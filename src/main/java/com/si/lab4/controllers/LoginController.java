package com.si.lab4.controllers;

import com.si.lab4.exceptions.InvalidCredentialsException;
import com.si.lab4.exceptions.PasswordNotMatch;
import com.si.lab4.model.requests.LoginResponse;
import com.si.lab4.model.requests.RegisterRequest;
import com.si.lab4.model.requests.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationController authenticationController;

    @RequestMapping(value = {"/", "/login"})
    public ModelAndView loginPage() {
        if(LoginResponse.isExpired) {
            return new ModelAndView("login");
        }
        return new ModelAndView("redirect:/soon");
    }

    @RequestMapping(value = "/register")
    public ModelAndView registerPage() {
        if(LoginResponse.isExpired) {
            return new ModelAndView("login");
        }
        return new ModelAndView("redirect:/soon");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView processLogin(@ModelAttribute UserRequest userRequest) {
        authenticationController.login(userRequest);
        return new ModelAndView("redirect:/soon");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegistration(@ModelAttribute RegisterRequest registerRequest) {
        authenticationController.signup(registerRequest);
        return new ModelAndView("redirect:/soon");
    }

    @RequestMapping(value = "/logoutQuery", method = RequestMethod.POST)
    public ModelAndView logout(){
        LoginResponse.isExpired = true;
        //authenticationController.logOut();
        return new ModelAndView("redirect:/");
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView redirectToLogin() {
        return new ModelAndView("redirect:/login");
    }
}
