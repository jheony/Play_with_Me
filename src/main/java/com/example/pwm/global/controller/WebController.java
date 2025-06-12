package com.example.pwm.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @GetMapping(value = { "/", "/{x:[\\w\\-]+}" })
    public String forward() {
        return "forward:/index.html";
    }
}
