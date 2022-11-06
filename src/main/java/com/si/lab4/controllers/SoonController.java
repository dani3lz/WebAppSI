package com.si.lab4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SoonController {

    @RequestMapping("/soon")
    public String comingSoon(){
        return "soon";
    }
}
