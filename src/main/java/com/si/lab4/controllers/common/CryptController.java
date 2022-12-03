package com.si.lab4.controllers.common;

import com.si.lab4.model.requests.TextRequest;
import com.si.lab4.model.response.ConvertorResponse;
import com.si.lab4.model.response.TextResponse;
import com.si.lab4.service.CryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequestMapping("/convertor")
@RequiredArgsConstructor
public class CryptController {

    private final CryptService cryptService;

    @PostMapping
    public ModelAndView post(@ModelAttribute(name = "TextRequest") TextRequest request) throws Exception {
        TextResponse response = cryptService.doOperation(request);
        ModelAndView model = new ModelAndView("convertor");

        ConvertorResponse convertorResponse = new ConvertorResponse(
                response.getKey(),
                response.getOutputText(),
                false,
                false);

        model.addObject("response", convertorResponse);

        if (Objects.isNull(response.getKey())) {
            convertorResponse.setHideKey(true);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLogged = !Objects.isNull(authentication) && !(authentication instanceof AnonymousAuthenticationToken);
        model.addObject("isLogged", isLogged);

        return model;
    }

    @GetMapping
    public ModelAndView get() {
        ModelAndView model = new ModelAndView("convertor");
        model.addObject("response", new ConvertorResponse());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLogged = !Objects.isNull(authentication) && !(authentication instanceof AnonymousAuthenticationToken);
        model.addObject("isLogged", isLogged);

        return model;
    }

}
