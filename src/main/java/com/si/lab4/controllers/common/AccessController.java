package com.si.lab4.controllers.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessController {

    @RequestMapping("/access-denied")
    public ModelAndView denied(){
        return new ModelAndView("denied");
    }
}
