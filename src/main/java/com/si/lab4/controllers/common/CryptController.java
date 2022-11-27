package com.si.lab4.controllers.common;

import com.si.lab4.model.requests.TextRequest;
import com.si.lab4.model.response.TextResponse;
import com.si.lab4.service.CryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/convertor")
@RequiredArgsConstructor
public class CryptController {

    private final CryptService cryptService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView doSomething(@ModelAttribute(name = "TextRequest") TextRequest request) throws Exception {
        ModelAndView model = new ModelAndView("convertor");
        TextResponse response = cryptService.doOperation(request);

        model.addObject("outputText", response.getOutputText());
        model.addObject("key", response.getKey());
        model.addObject("hide", false);
        if(response.getKey() == null) {
            model.addObject("hideKey", true);
        } else {
            model.addObject("hideKey", false);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addObject("isLogged", true);
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addObject("isLogged", false);
        }

        return model;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView get() {
        ModelAndView model = new ModelAndView("convertor");

        model.addObject("key", "");
        model.addObject("outputText", "");
        model.addObject("hide", true);
        model.addObject("hideKey", true);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addObject("isLogged", true);
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addObject("isLogged", false);
        }
        return model;
    }

}
